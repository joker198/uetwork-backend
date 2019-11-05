package uet.service;

import uet.DTO.*;
import uet.config.GlobalConfig;
import uet.model.*;
import uet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import javax.xml.ws.http.HTTPException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by nhkha on 27/03/2017.
 */
@Service
public class ContractService {
    private final ContractRepository contractRepository;
    private final UetManRepository uetManRepository;
    private final UnitNameRepository unitNameRepository;
    private final PartnerContactRepository partnerContactRepository;
    private final PartnerRepository partnerRepository;
    private final CooperateActivityRepository cooperateActivityRepository;
    private final UserRepository userRepository;
    private final ActivityLogRepository activityLogRepository;
    private final ContractShareRepository contractShareRepository;
    private final AnnualActivityRepository annualActivityRepository;
    private final NoticeRepository noticeRepository;
    private final CooperateActivityDetailRepository cooperateActivityDetailRepository;
    private final RolesAndSigningLevelRepository rolesAndSigningLevelRepository;
    private final ContractShareVnuRepository contractShareVnuRepository;

    @Autowired
    public ContractService(
        ContractRepository contractRepository,
        UetManRepository uetManRepository,
        UnitNameRepository unitNameRepository,
        PartnerContactRepository partnerContactRepository,
        PartnerRepository partnerRepository,
        CooperateActivityRepository cooperateActivityRepository,
        UserRepository userRepository,
        ActivityLogRepository activityLogRepository,
        ContractShareRepository contractShareRepository,
        AnnualActivityRepository annualActivityRepository,
        NoticeRepository noticeRepository,
        CooperateActivityDetailRepository cooperateActivityDetailRepository,
        RolesAndSigningLevelRepository rolesAndSigningLevelRepository,
        ContractShareVnuRepository contractShareVnuRepository
    ) {
        this.contractRepository = contractRepository;
        this.uetManRepository = uetManRepository;
        this.unitNameRepository = unitNameRepository;
        this.partnerContactRepository = partnerContactRepository;
        this.partnerRepository = partnerRepository;
        this.cooperateActivityRepository = cooperateActivityRepository;
        this.userRepository = userRepository;
        this.activityLogRepository = activityLogRepository;
        this.contractShareRepository = contractShareRepository;
        this.annualActivityRepository = annualActivityRepository;
        this.noticeRepository = noticeRepository;
        this.cooperateActivityDetailRepository = cooperateActivityDetailRepository;
        this.rolesAndSigningLevelRepository = rolesAndSigningLevelRepository;
        this.contractShareVnuRepository = contractShareVnuRepository;
    }

    private String attachFile(ContractDTO contractDTO, ExcelContractDTO excelContractDTO, User user) throws IOException
    {
        String fileFolder = UUID.randomUUID().toString();
        if (user != null) {
            String fileType = null, fileName = null, attachFile = null;
            if (contractDTO != null) {
                fileType = contractDTO.getFileType();
                fileName = contractDTO.getFileName();
                attachFile = contractDTO.getAttachFile();
            } else if (excelContractDTO != null) {
                fileType = excelContractDTO.getFileType();
                fileName = excelContractDTO.getFileName();
                attachFile = excelContractDTO.getAttachFile();
            }
            if (fileType != null && fileName != null && attachFile != null) {
                String pathname = GlobalConfig.sourceAddress + "/app/users_data/" + user.getUserName() + "/" +
                        fileFolder + "/";
                File directory = new File(pathname);
                if (!directory.exists()) {
                    directory.mkdir();
                }
                String directoryName = "/users_data/" + user.getUserName() + "/" + fileFolder + "/";
                if (fileType.equals("doc") || fileType.equals("docx") || fileType.equals("pdf")) {
                    byte[] btDataFile = DatatypeConverter.parseBase64Binary(attachFile);
                    File of = new File(pathname + fileName);
                    FileOutputStream osf = new FileOutputStream(of);
                    osf.write(btDataFile);
                    osf.flush();
                }
                return directoryName + fileName;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    public void createContract(ContractDTO contractDTO, String token) throws Exception {
        List<UetMan> uetManList = contractDTO.getUetManList();
        User user = userRepository.findByToken(token);
        RolesAndSigningLevel rolesAndSigningLevel = null;
        if (user.getRole().equals(String.valueOf(Role.ADMIN_UNIT)) || user.getRole().equals(String.valueOf(Role.ADMIN_VNU))) {
            if (contractDTO.getRolesAndSigningLevelId() != 0) {
                rolesAndSigningLevel = rolesAndSigningLevelRepository.findById(contractDTO.getRolesAndSigningLevelId());
            } else {
                rolesAndSigningLevel = user.getRolesAndSigningLevel();
            }
        } else if (user.getRole().equals(String.valueOf(Role.UNIT))) {
            rolesAndSigningLevel = user.getUnitName().getRolesAndSigningLevel();
        }
        UnitName unit = user.getUnitName();
        Partner partner = partnerRepository.findById(contractDTO.getPartnerId());
        PartnerContact partnerContact = null;
        if (contractDTO.getPartnerContactId() == -1) {
            if (contractDTO.getPartnerContact() != null) {
                partnerContact = contractDTO.getPartnerContact();
                partnerContact.setPartner(partner);
                partnerContactRepository.save(partnerContact);
            }
        } else {
            partnerContact = partnerContactRepository.findById(contractDTO.getPartnerContactId());
        }
        PartnerContact contactPoint = null;
        if (contractDTO.getContactPointId() == -1) {
            if (contractDTO.getContactPoint() != null) {
                contactPoint = contractDTO.getContactPoint();
                contactPoint.setPartner(partner);
                partnerContactRepository.save(contactPoint);
            }
        } else if (contractDTO.getContactPointId() == 0) {
            contactPoint = null;
        } else {
            contactPoint = partnerContactRepository.findById(contractDTO.getContactPointId());
        }
        if (partner != null && partnerContact != null) {
            Contract contract = new Contract(partner, partnerContact, uetManList, rolesAndSigningLevel);
            String[] contentContract = contractDTO.getContentContract().split("<br />");
            List<CooperateActivity> setCooperateActivity = new ArrayList<CooperateActivity>();
            contract.setContactPoint(contactPoint);
            contract.setFunding(contractDTO.getFunding());
            contract.setEndDate(contractDTO.getEndDate());
            contract.setNotice(contractDTO.getNotice());
            contract.setNumber(contractDTO.getNumber());
            contract.setOrdinaryNumber(contractDTO.getOrdinaryNumber());
            contract.setStartDate(contractDTO.getStartDate());
            contract.setRenew(contractDTO.getRenew());
            contract.setResult(contractDTO.getResult());
            contract.setCreatedAt(new java.util.Date());
            if (contractDTO.getFileName() != null && contractDTO.getFileType() != null && contractDTO.getAttachFile() != null) {
                contract.setAttachFileAdd(this.attachFile(contractDTO, null, user));
            }
            List<Contract> setContract = new ArrayList<Contract>();
            setContract.add(contract);

            partner.setContracts(setContract);
            partnerContact.setContract(setContract);
            contractRepository.save(contract);
            partnerContactRepository.save(partnerContact);
            int contractId = contract.getId();
            if(!user.getRole().equals(String.valueOf(Role.ADMIN_VNU))){
                if (!contractDTO.getUnitNames().isEmpty()) {
                    List<ContractDTO> contractDTOList = new ArrayList<ContractDTO>();
                    for (UnitName unitName : contractDTO.getUnitNames()) {
                        ContractDTO contractDTO1 = new ContractDTO();
                        contractDTO1.setId(contractId);
                        if (unitName.getId() != 0) {
                            contractDTO1.setUnitNameId(unitName.getId());
                            contractDTO1.setId(contractId);
                            contractDTO1.setResult("true");
                            contractDTOList.add(contractDTO1);
                        }
                    }
                    if (unit != null) {
                        ContractDTO contractDTO1 = new ContractDTO();
                        contractDTO1.setUnitNameId(unit.getId());
                        contractDTO1.setId(contractId);
                        contractDTO1.setResult("true");
                        contractDTOList.add(contractDTO1);
                    }
                    if (!contractDTOList.isEmpty()) {
                        this.shareContract(contractDTOList);
                    }
                }
            } else {
                if (!contractDTO.getRolesAndSigningLevelList().isEmpty()) {
                    List<ContractDTO> contractDTOList = new ArrayList<ContractDTO>();
                    for (RolesAndSigningLevel rolesAndSigningLevel1 : contractDTO.getRolesAndSigningLevelList()) {
                        ContractDTO contractDTO1 = new ContractDTO();
                        contractDTO1.setId(contractId);
                        if (rolesAndSigningLevel1.getId() != 0) {
                            contractDTO1.setRolesAndSigningLevelId(rolesAndSigningLevel1.getId());
                            contractDTO1.setId(contractId);
                            contractDTO1.setResult("true");
                            contractDTOList.add(contractDTO1);
                        }
                    }
                    if (!contractDTOList.isEmpty()) {
                        this.shareContractVnu(contractDTOList);
                    }
                }
            }

            if(user.getRole().equals(String.valueOf(Role.ADMIN_VNU))){
                if (!contractDTO.getRolesAndSigningLevelList().isEmpty()) {
                    List<ContractDTO> contractDTOList = new ArrayList<ContractDTO>();
                    for (RolesAndSigningLevel rolesAndSigningLevel1 : contractDTO.getRolesAndSigningLevelList()) {
                        ContractDTO contractDTO1 = new ContractDTO();
                        contractDTO1.setId(contractId);
                        if (rolesAndSigningLevel1.getId() != 0) {
                            contractDTO1.setRolesAndSigningLevelId(rolesAndSigningLevel1.getId());
                            contractDTO1.setId(contractId);
                            contractDTO1.setResult("true");
                            contractDTOList.add(contractDTO1);
                        }
                    }
                    if (user.getRole().equals(String.valueOf(Role.ADMIN_UNIT))) {

                        ContractDTO contractDTO1 = new ContractDTO();
                        contractDTO1.setRolesAndSigningLevelId(user.getRolesAndSigningLevel().getId());
                        contractDTO1.setId(contractId);
                        contractDTO1.setResult("true");
                        contractDTOList.add(contractDTO1);
                    }
                    if (!contractDTOList.isEmpty()) {
                        this.shareContractVnu(contractDTOList);
                    }
                }
            }
            for (String content : contentContract) {
                CooperateActivityDetail cooperateActivityDetail = new CooperateActivityDetail();
                cooperateActivityDetailRepository.save(cooperateActivityDetail);
                CooperateActivity cooperateActivity = new CooperateActivity(content,
                        contract, cooperateActivityDetail);
                cooperateActivityRepository.save(cooperateActivity);
                cooperateActivityDetail.setCooperateActivity(cooperateActivity);
                cooperateActivityDetailRepository.save(cooperateActivityDetail);
                setCooperateActivity.add(cooperateActivity);
            }
            contract.setCooperateActivity(setCooperateActivity);
            partnerRepository.save(partner);
            if (user.getRole().equals(String.valueOf(Role.UNIT))) {
                ActivityLog activityLog = new ActivityLog(user);
                userRepository.save(user);
                activityLog.setActivityType("createContract");
                activityLog.setAcvtivity(user.getUnitName().getUnitName() + " tạo Hoạt động hợp tác cho đối tác: " +
                        partner.getPartnerName() + " vào lúc " + activityLog.getTimestamp());
                activityLog.setStatus("NEW");
                activityLog.setContractId(contract.getId());
                activityLogRepository.save(activityLog);
            }
        } else {
            throw new Exception("Có lỗi xảy ra, kiểm tra lại các trường trong Hoạt động hợp tác!");
        }
    }

    public List<ExcelContractDTO> importExcel(Set<ExcelContractDTO> List, String token) throws IOException {
        List<ExcelContractDTO> listContract = new ArrayList<ExcelContractDTO>();
        User user = userRepository.findByToken(token);
        for (ExcelContractDTO contractDTO : List) {
            List<UetMan> uetManList = contractDTO.getUetManList();
            Partner partner = partnerRepository.findById(contractDTO.getPartnerId());
            PartnerContact partnerContact = partnerContactRepository.findById(contractDTO.getPartnerContactId());
            RolesAndSigningLevel rolesAndSigningLevel = rolesAndSigningLevelRepository.findById(contractDTO.getRoleAndSigningLevelId());
            if(rolesAndSigningLevel == null){
                rolesAndSigningLevel = user.getRolesAndSigningLevel();
            }
            if (partner != null) {
                Contract contract = new Contract(partner, partnerContact, uetManList, rolesAndSigningLevel);
                String[] contentContract = contractDTO.getContentContract().split("<br />");
                List<CooperateActivity> setCooperateActivity = new ArrayList<CooperateActivity>();

                if(contractDTO.getContactPointId() != 0){
                    PartnerContact contactPoint = partnerContactRepository.findById(contractDTO.getContactPointId());
                    if(contactPoint != null){
                        contract.setContactPoint(contactPoint);
                    }
                }

                contract.setFunding(contractDTO.getFunding());
                contract.setEndDate(contractDTO.getEndDate());
                contract.setNotice(contractDTO.getNotice());
                contract.setNumber(contractDTO.getNumber());
                contract.setOrdinaryNumber(contractDTO.getOrdinaryNumber());
                contract.setStartDate(contractDTO.getStartDate());
                if(contractDTO.getRenew() != null){
                    if(contractDTO.getRenew().equals("x")){
                        contract.setRenew(true);
                    }
                }
                contract.setResult(contractDTO.getResult());
                contract.setCreatedAt(new java.util.Date());
                if (contractDTO.getFileName() != null && contractDTO.getFileType() != null && contractDTO.getAttachFile() != null) {
                    contract.setAttachFileAdd(this.attachFile(null, contractDTO, user));
                }
                List<Contract> setContract = new ArrayList<Contract>();
                setContract.add(contract);


                partner.setContracts(setContract);
                if(partnerContact != null){
                    partnerContact.setContract(setContract);
                    partnerContactRepository.save(partnerContact);
                }

                contractRepository.save(contract);

                int contractId = contract.getId();
                if(contractDTO.getUnitNames() != null){
                    if (!contractDTO.getUnitNames().isEmpty()) {
                        List<ContractDTO> contractDTOList = new ArrayList<ContractDTO>();

                        for (UnitName unitName : contractDTO.getUnitNames()) {
                            ContractDTO contractDTO1 = new ContractDTO();
                            contractDTO1.setId(contractId);
                            if (unitName.getId() != 0) {
                                contractDTO1.setUnitNameId(unitName.getId());
                                contractDTO1.setId(contractId);
                                contractDTO1.setResult("true");
                                contractDTOList.add(contractDTO1);
                            }
                        }
                        if (!contractDTOList.isEmpty()) {
                            this.shareContract(contractDTOList);
                        }
                    }
                }
                for (String content : contentContract) {
                    CooperateActivityDetail cooperateActivityDetail = new CooperateActivityDetail();
                    cooperateActivityDetailRepository.save(cooperateActivityDetail);
                    CooperateActivity cooperateActivity = new CooperateActivity(content,
                            contract, cooperateActivityDetail);
                    cooperateActivityRepository.save(cooperateActivity);
                    cooperateActivityDetail.setCooperateActivity(cooperateActivity);
                    cooperateActivityDetailRepository.save(cooperateActivityDetail);
                    setCooperateActivity.add(cooperateActivity);
                }
                contract.setCooperateActivity(setCooperateActivity);
                partnerRepository.save(partner);
                if (user.getRole().equals(String.valueOf(Role.UNIT))) {
                    ActivityLog activityLog = new ActivityLog(user);
                    userRepository.save(user);
                    activityLog.setActivityType("createContract");
                    activityLog.setAcvtivity(user.getUnitName().getUnitName() + " tạo Hoạt động hợp tác cho đối tác: " +
                            partner.getPartnerName() + " vào lúc " + activityLog.getTimestamp());
                    activityLog.setStatus("NEW");
                    activityLog.setContractId(contract.getId());
                    activityLogRepository.save(activityLog);
                }
            } else {
                listContract.add(contractDTO);
            }
        }
        return listContract;
    }

    public void editContract(ContractDTO contractDTO, int contractId, String token) throws IOException {
        Contract contract = contractRepository.findById(contractId);
        if (contract != null) {
            User user = userRepository.findByToken(token);
            contract.setFunding(contractDTO.getFunding());
            contract.setEndDate(contractDTO.getEndDate());
            if (contractDTO.getEditNotice() != null) {
                if (contractDTO.getEditNotice().equals("true")) {
                    boolean emptyNotice = false;
                    if (contract.getNotices().isEmpty()) {
                        emptyNotice = true;
                    }
                    Notice notice = new Notice(contract.getNotice(), contract);
                    contract.setNotice(contractDTO.getNotice());
                    if (emptyNotice) {
                        notice.setCreated(contract.getCreatedAt());
                    } else {
                        Notice notice1 = noticeRepository.findTopByContractIdOrderByCreatedDesc(contractId);
                        notice.setCreated(notice1.getLastUpdated());
                    }
                    if(user.getRole().equals(String.valueOf(Role.ADMIN_VNU)) || user.getRole().equals(String.valueOf(Role.ADMIN_UNIT))){
                        notice.setEditorName(user.getRolesAndSigningLevel().getName());
                    } else {
                        notice.setEditorName(user.getUnitName().getRolesAndSigningLevel().getName());
                    }
                    noticeRepository.save(notice);
                }
            }
            contract.setNumber(contractDTO.getNumber());
            contract.setOrdinaryNumber(contractDTO.getOrdinaryNumber());
            contract.setStartDate(contractDTO.getStartDate());
            contract.setRenew(contractDTO.getRenew());
            contract.setResult(contractDTO.getResult());
            if (contractDTO.getCooperateActivityValue() != null) {
                Partner partner = contract.getPartner();
                String[] cooperateActivity = contractDTO.getCooperateActivityValue().split("<br />");
                List<CooperateActivity> setCooperateActivity = new ArrayList<CooperateActivity>();
                for (String content : cooperateActivity) {
                    CooperateActivityDetail cooperateActivityDetail = new CooperateActivityDetail();
                    cooperateActivityDetailRepository.save(cooperateActivityDetail);
                    CooperateActivity cooperateActivity1 = new CooperateActivity(content,
                            contract, cooperateActivityDetail);
                    cooperateActivityRepository.save(cooperateActivity1);
                    cooperateActivityDetail.setCooperateActivity(cooperateActivity1);
                    cooperateActivityDetailRepository.save(cooperateActivityDetail);
                    setCooperateActivity.add(cooperateActivity1);
                }
                contract.setCooperateActivity(setCooperateActivity);
                partnerRepository.save(partner);
            }
            if (contractDTO.getAttachFileAdd() != null) {
                if (contractDTO.getAttachFileAdd().equals("edited")) {
                    contract.setAttachFileAdd(this.attachFile(contractDTO, null, user));
                }
            }
            PartnerContact contactPoint = null;
            if (contractDTO.getContactPointId() == -1) {
                if (contractDTO.getContactPoint() != null) {
                    contactPoint = contractDTO.getContactPoint();
                    contactPoint.setPartner(contract.getPartner());
                    partnerContactRepository.save(contactPoint);
                }
            } else if (contractDTO.getContactPointId() == 0) {
                contactPoint = null;
            } else {
                contactPoint = partnerContactRepository.findById(contractDTO.getContactPointId());
            }
            contract.setContactPoint(contactPoint);
            contractRepository.save(contract);
            if (user.getRole().equals(String.valueOf(Role.UNIT))) {
                ActivityLog activityLog = new ActivityLog(user);
                userRepository.save(user);
                activityLog.setActivityType("editContract");
                activityLog.setAcvtivity(user.getUnitName().getUnitName() + " sửa Hoạt động hợp tác của đối tác: " +
                        contract.getPartner().getPartnerName() + " vào lúc " + activityLog.getTimestamp());
                activityLog.setStatus("NEW");
                activityLog.setContractId(contract.getId());
                activityLogRepository.save(activityLog);
            }
        } else {
            throw new NullPointerException("Không tìm thấy Hoạt động hợp tác này hãy thử lại!");
        }

    }

    public void deleteContract(int contractId, String token) {
        Contract contract = contractRepository.findById(contractId);
        if (contract != null) {
            contractRepository.delete(contract);
            User user = userRepository.findByToken(token);
            if (user.getRole().equals(String.valueOf(Role.UNIT))) {
                ActivityLog activityLog = new ActivityLog(user);
                userRepository.save(user);
                activityLog.setActivityType("createContract");
                activityLog.setAcvtivity(user.getUnitName().getUnitName() + " xóa Hoạt động hợp tác của đối tác: " +
                        contract.getPartner().getPartnerName() + " vào lúc " + activityLog.getTimestamp());
                activityLog.setStatus("NEW");
                activityLogRepository.save(activityLog);
            }
        } else {
            throw new NullPointerException("Không tìm thấy Hoạt động hợp tác cần xoám hãy thử lại!");
        }
    }

    public List<Contract> getAllContract() {
        return (List<Contract>) contractRepository.findAll();
    }

    public List<Contract> getContractByRoleAndSigningLevel(String token) {
        User user = userRepository.findByToken(token);
        if (user != null) {
            if (user.getRolesAndSigningLevel() != null) {
                List<Contract> response = new ArrayList<>();
                if (user.getRole().equals(String.valueOf(Role.ADMIN_UNIT))) {
                    List<Contract> adminUnitContractList = user.getRolesAndSigningLevel().getContractList();
                    for (RolesAndSigningLevel rolesAndSigningLevel : user.getRolesAndSigningLevel().getChild()) {
                        adminUnitContractList.addAll(rolesAndSigningLevel.getContractList());
                    }
                    if(!user.getRolesAndSigningLevel().getContractShareVnus().isEmpty()){
                        List<Contract> contracts = new ArrayList<Contract>();
                        for (ContractShareVnu contractShareVnu :user.getRolesAndSigningLevel().getContractShareVnus()) {
                            contracts.add(contractShareVnu.getContract());
                        }
                        adminUnitContractList.addAll(contracts);
                    }
                    response.addAll(adminUnitContractList);
                } else if (user.getRole().equals(String.valueOf(Role.UNIT))) {
                    response.addAll(user.getUnitName().getRolesAndSigningLevel().getContractList());
                } else if(user.getRole().equals(String.valueOf(Role.ADMIN_VNU))){
                    response.addAll(user.getRolesAndSigningLevel().getContractList());
                }
                return response;
            } else {
                throw new HTTPException(401);
            }
        } else {
            throw new HTTPException(401);
        }
    }

    public CheckContractDTO checkContract(CheckContractDTO checkContractDTO) {
        int countPartner = 0;
        String contactId = "";
        if (checkContractDTO.getPartnerName() != null) {
            List<Partner> partnerList = partnerRepository.findByPartnerNameContaining(checkContractDTO.getPartnerName());
            if (!partnerList.isEmpty()) {
                for (Partner partner : partnerList) {
                    checkContractDTO.setPartnerName(String.valueOf(partner.getId()));
                    List<PartnerContact> partners = partner.getPartnerContacts();
                    for (PartnerContact partnerContact : partners) {
                        if (checkContractDTO.getContactName().contains(partnerContact.getContactName())) {
                            countPartner++;
                            contactId = String.valueOf(partnerContact.getId());
                        }
                    }
                    if (countPartner == 1) {
                        checkContractDTO.setContactName(contactId);
                    } else if (countPartner == 0) {
                        checkContractDTO.setContactName("nf");
                    } else {
                        for (PartnerContact partnerContact : partners) {
                            if (checkContractDTO.getContactName().contains(partnerContact.getAbout())) {
                                checkContractDTO.setContactName(String.valueOf(partnerContact.getId()));
                            }
                        }
                    }
                }
            } else {
                checkContractDTO.setPartnerName("nf");
                checkContractDTO.setContactName("nf");
            }
        } else {
            checkContractDTO.setPartnerName("nf");
            checkContractDTO.setContactName("nf");
        }
        if (checkContractDTO.getUetMan() != null) {
            UetMan uetMan = uetManRepository.findByUetManNameContaining(checkContractDTO.getUetMan());
            if (uetMan != null) {
                checkContractDTO.setUetMan(String.valueOf(uetMan.getId()));
            } else {
                checkContractDTO.setUetMan("nf");
            }
        } else {
            checkContractDTO.setUetMan("nf");
        }
        if (checkContractDTO.getUnitName() != null) {
            UnitName unitName = unitNameRepository.findByUnitNameContaining(checkContractDTO.getUnitName());
            if (unitName != null) {
                checkContractDTO.setUnitName(String.valueOf(unitName.getId()));
            } else {
                checkContractDTO.setUnitName("nf");
            }
        } else {
            checkContractDTO.setUnitName("nf");
        }
        return checkContractDTO;
    }

    public void editCooperateActivity(CooperateActivityDTO cooperateActivityDTO, String token) {
        CooperateActivity cooperateActivity = cooperateActivityRepository.findById(cooperateActivityDTO.getId());
        if (cooperateActivity != null) {
            if (cooperateActivityDTO.getCooperateActivity() != null) {
                Contract contract = cooperateActivity.getContract();
                cooperateActivity.setCooperateActivity(cooperateActivityDTO.getCooperateActivity());
                cooperateActivityRepository.save(cooperateActivity);
                User user = userRepository.findByToken(token);
                if (user.getRole().equals(String.valueOf(Role.UNIT))) {
                    ActivityLog activityLog = new ActivityLog(user);
                    userRepository.save(user);
                    activityLog.setActivityType("editCooperateActivity");
                    activityLog.setAcvtivity(user.getUnitName().getUnitName() + " sửa hoạt động hợp tác Hoạt động hợp tác của đối tác: " +
                            cooperateActivity.getContract().getPartner().getPartnerName() + " vào lúc " + activityLog.getTimestamp());
                    activityLog.setStatus("NEW");
                    activityLog.setContractId(contract.getId());
                    activityLogRepository.save(activityLog);
                }
            } else {
                throw new NullPointerException("Hoạt động hợp tác rỗng!");
            }
        } else {
            throw new NullPointerException("Không tìm thấy hoạt động hợp tác!");
        }
    }

    public void deleteCooperateActivity(int cooperateActivityId, String token) {
        CooperateActivity cooperateActivity = cooperateActivityRepository.findById(cooperateActivityId);
        if (cooperateActivity != null) {
            cooperateActivityRepository.delete(cooperateActivity);
            User user = userRepository.findByToken(token);
            if (user.getRole().equals(String.valueOf(Role.UNIT))) {
                ActivityLog activityLog = new ActivityLog(user);
                userRepository.save(user);
                activityLog.setActivityType("deleteCooperateActivity");
                activityLog.setAcvtivity(user.getUnitName().getUnitName() + " xóa hoạt động hợp tác: " + cooperateActivity.getCooperateActivity() + " của Hoạt động hợp tác của đối tác: " +
                        cooperateActivity.getContract().getPartner().getPartnerName() + " vào lúc " + activityLog.getTimestamp());
                activityLog.setStatus("NEW");
                activityLog.setContractId(cooperateActivity.getContract().getId());
                activityLogRepository.save(activityLog);
            }
        } else {
            throw new NullPointerException("Không tìm thấy hoạt động hợp tác!");
        }
    }

    public void addCooperateActivity(CooperateActivityDTO cooperateActivityDTO, int contractId, String token) {
        Contract contract = contractRepository.findById(contractId);
        if (contract != null) {
            Partner partner = contract.getPartner();
            String[] cooperateActivity = cooperateActivityDTO.getCooperateActivity().split("<br />");
            List<CooperateActivity> setCooperateActivity = new ArrayList<CooperateActivity>();
            for (String content : cooperateActivity) {
                CooperateActivityDetail cooperateActivityDetail = new CooperateActivityDetail();
                cooperateActivityDetailRepository.save(cooperateActivityDetail);
                CooperateActivity cooperateActivity1 = new CooperateActivity(content,
                        contract, cooperateActivityDetail);
                cooperateActivityRepository.save(cooperateActivity1);
                cooperateActivityDetail.setCooperateActivity(cooperateActivity1);
                cooperateActivityDetailRepository.save(cooperateActivityDetail);
                setCooperateActivity.add(cooperateActivity1);
            }
            contract.setCooperateActivity(setCooperateActivity);
            partnerRepository.save(partner);
            contractRepository.save(contract);
            User user = userRepository.findByToken(token);
            if (user.getRole().equals(String.valueOf(Role.UNIT))) {
                ActivityLog activityLog = new ActivityLog(user);
                userRepository.save(user);
                activityLog.setActivityType("addCooperateActivity");
                activityLog.setAcvtivity(user.getUnitName().getUnitName() + " thêm hoạt động hợp tác: " + cooperateActivityDTO.getCooperateActivity() + " của Hoạt động hợp tác của đối tác: " +
                        contract.getPartner().getPartnerName() + " vào lúc " + activityLog.getTimestamp());
                activityLog.setStatus("NEW");
                activityLog.setContractId(contract.getId());
                activityLogRepository.save(activityLog);
            }
        } else {
            throw new NullPointerException("Không tìm thấy Hoạt động hợp tác!");
        }
    }


    public List<Contract> getContractOfUnit(String token) {
        User user = userRepository.findByToken(token);
        if (user != null) {
            if (user.getUnitName() != null) {
                List<Contract> contracts = new ArrayList<Contract>();
                for (ContractShare contractShare : user.getUnitName().getContractShares()) {
                    contracts.add(contractShare.getContract());
                }
                return contracts;
            } else {
                throw new HTTPException(404);
            }
        } else {
            throw new HTTPException(401);
        }
    }

    public List<Contract> getAllContractOfPartner(int partnerId) {
        return partnerRepository.findById(partnerId).getContracts();
    }

    public void shareContract(List<ContractDTO> contractDTOList) {
        if (!contractDTOList.isEmpty()) {
            for (ContractDTO contractDTO : contractDTOList) {
                if (contractDTO.getId() != 0 && contractDTO.getUnitNameId() != 0) {
                    ContractShare contractShare1 = contractShareRepository.findByUnitNameIdAndContractId(contractDTO.getUnitNameId(),
                            contractDTO.getId());
                    if (contractShare1 == null) {
                        if (contractDTO.getResult().equals("true")) {
                            Contract contract = contractRepository.findById(contractDTO.getId());
                            UnitName unitName = unitNameRepository.findById(contractDTO.getUnitNameId());
                            if (contract != null && unitName != null) {
                                ContractShare contractShare = new ContractShare(contract, unitName);
                                contractShareRepository.save(contractShare);
                            }
                        }
                    } else {
                        if (contractDTO.getResult().equals("false")) {
                            contractShareRepository.delete(contractShare1);
                        }
                    }
                }
            }
        } else {
            throw new HTTPException(400);
        }
    }

    public void shareContractVnu(List<ContractDTO> contractDTOList) {
        if (!contractDTOList.isEmpty()) {
            for (ContractDTO contractDTO : contractDTOList) {
                if (contractDTO.getId() != 0 && contractDTO.getRolesAndSigningLevelId() != 0) {
                    ContractShareVnu contractShareVnu = contractShareVnuRepository.findByRolesSigningLevelIdAndContractId(contractDTO.getRolesAndSigningLevelId(),
                            contractDTO.getId());
                    if (contractShareVnu == null) {
                        if (contractDTO.getResult().equals("true")) {
                            Contract contract = contractRepository.findById(contractDTO.getId());
                            RolesAndSigningLevel rolesAndSigningLevel = rolesAndSigningLevelRepository.findById(contractDTO.getRolesAndSigningLevelId());
                            if (contract != null && rolesAndSigningLevel != null) {
                                ContractShareVnu contractShareVnu1 = new ContractShareVnu(contract, rolesAndSigningLevel);
                                contractShareVnuRepository.save(contractShareVnu1);
                            }
                        }
                    } else {
                        if (contractDTO.getResult().equals("false")) {
                            contractShareVnuRepository.delete(contractShareVnu);
                        }
                    }
                }
            }
        } else {
            throw new HTTPException(400);
        }
    }

    public void deleteContractShare(List<Integer> listId) {
        if (!listId.isEmpty()) {
            for (int id : listId) {
                if (id != 0) {
                    ContractShare contractShare = contractShareRepository.findById(id);
                    if (contractShare != null) {
                        contractShareRepository.delete(contractShare);
                    }
                }
            }
        } else {
            throw new HTTPException(400);
        }
    }

    public List<ContractShare> getAllContractShareOfUnit(int unitNameId) {
        if (unitNameId != 0) {
            return contractShareRepository.findByUnitNameId(unitNameId);
        } else {
            throw new HTTPException(404);
        }
    }

    public List<ContractShare> getAllContractShareOfContract(int contractId) {
        if (contractId != 0) {
            return contractShareRepository.findByContractId(contractId);
        } else {
            throw new HTTPException(400);
        }
    }

    public void editAnnualActivity(AnnualActivityDTO annualActivityDTO) throws Exception {
        AnnualActivity annualActivity = annualActivityRepository.findById(annualActivityDTO.getId());
        if (annualActivity != null) {
            annualActivity.setActivityName(annualActivityDTO.getActivityName());
            annualActivity.setDate(annualActivityDTO.getDate());
            annualActivity.setContent(annualActivityDTO.getContent());
            annualActivity.setFunding(annualActivityDTO.getFunding());
            annualActivityRepository.save(annualActivity);
        } else {
            throw new Exception("Không tìm thấy hoạt động!");
        }
    }

    public void deleteAnnaulActivity(int annualActivityId) throws Exception {
        AnnualActivity annualActivity = annualActivityRepository.findById(annualActivityId);
        if (annualActivity != null) {
            annualActivityRepository.delete(annualActivityId);
        } else {
            throw new Exception("Không tìm thấy hoạt động");
        }
    }

    public List<AnnualActivity> getAllAnnualActivity() {
        return annualActivityRepository.findAll();
    }


    public void editCooperateActivityDetail(CooperateActivityDetailDTO cooperateActivityDetailDTO) throws Exception {
        CooperateActivityDetail cooperateActivityDetail = cooperateActivityDetailRepository.findById(cooperateActivityDetailDTO.getId());
        if (cooperateActivityDetail != null) {
            cooperateActivityDetail.setContent(cooperateActivityDetailDTO.getContent());
            cooperateActivityDetail.setDate(cooperateActivityDetailDTO.getDate());
            cooperateActivityDetail.setFunding(cooperateActivityDetailDTO.getFunding());
            cooperateActivityDetailRepository.save(cooperateActivityDetail);
        } else {
            throw new Exception("Không tìm thấy hoạt động!");
        }
    }

    public void renewContract(ContractDTO contractDTO, int oldContractId, String token) throws IOException {
        Contract oldContract = contractRepository.findById(oldContractId);
        if (oldContract != null) {
            oldContract.setRenew(false);
            contractRepository.save(oldContract);
            User user = userRepository.findByToken(token);
            PartnerContact partnerContact = null;
            if (contractDTO.getPartnerContactId() == -1) {
                if (contractDTO.getPartnerContact() != null) {
                    partnerContact = contractDTO.getPartnerContact();
                    partnerContact.setPartner(oldContract.getPartner());
                    partnerContactRepository.save(partnerContact);
                } else {
                    partnerContact = oldContract.getPartnerContact();
                }
            } else if(contractDTO.getPartnerContactId() != 0){
                partnerContact = partnerContactRepository.findById(contractDTO.getPartnerContactId());
            } else if(contractDTO.getPartnerId() == 0){
                partnerContact = oldContract.getPartnerContact();
            }
            List<UetMan> uetManList = contractDTO.getUetManList();
            Contract contract = new Contract(oldContract.getPartner(), partnerContact, uetManList, oldContract.getRolesAndSigningLevel());
            contract.setFunding(contractDTO.getFunding());
            contract.setEndDate(contractDTO.getEndDate());
            contract.setRenew(contractDTO.getRenew());
            contract.setNumber(contractDTO.getNumber());
            contract.setOrdinaryNumber(contractDTO.getOrdinaryNumber());
            contract.setStartDate(contractDTO.getStartDate());
            contract.setResult(contractDTO.getResult());
            contractRepository.save(contract);
            List<CooperateActivity> setCooperateActivity = new ArrayList<CooperateActivity>();
            for (CooperateActivity cooperateActivity1 : oldContract.getCooperateActivity()) {
                CooperateActivityDetail cooperateActivityDetail = new CooperateActivityDetail();
                cooperateActivityDetail.setContent(cooperateActivity1.getCooperateActivityDetail().getContent());
                cooperateActivityDetail.setFunding(cooperateActivity1.getCooperateActivityDetail().getFunding());
                cooperateActivityDetailRepository.save(cooperateActivityDetail);
                CooperateActivity cooperateActivity2 = new CooperateActivity(cooperateActivity1.getCooperateActivity(),
                        contract, cooperateActivityDetail);
                cooperateActivityRepository.save(cooperateActivity2);
                cooperateActivityDetail.setCooperateActivity(cooperateActivity2);
                cooperateActivityDetailRepository.save(cooperateActivityDetail);
                setCooperateActivity.add(cooperateActivity2);
            }
            if (contractDTO.getCooperateActivityValue() != null) {
                String[] cooperateActivity = contractDTO.getCooperateActivityValue().split("<br />");
                for (String content : cooperateActivity) {
                    CooperateActivityDetail cooperateActivityDetail = new CooperateActivityDetail();
                    cooperateActivityDetailRepository.save(cooperateActivityDetail);
                    CooperateActivity cooperateActivity1 = new CooperateActivity(content,
                            contract, cooperateActivityDetail);
                    cooperateActivityRepository.save(cooperateActivity1);
                    cooperateActivityDetail.setCooperateActivity(cooperateActivity1);
                    cooperateActivityDetailRepository.save(cooperateActivityDetail);
                    setCooperateActivity.add(cooperateActivity1);
                }
            }
            contract.setCooperateActivity(setCooperateActivity);
            for (Notice notice : oldContract.getNotices()) {
                Notice notice1 = new Notice(notice.getContent(), contract);
                notice1.setCreated(notice.getCreated());
                notice1.setLastUpdated(notice.getLastUpdated());
                noticeRepository.save(notice1);
            }
            contract.setNotice(contractDTO.getNotice());
            if (contractDTO.getAttachFileAdd() != null) {
                if (contractDTO.getAttachFileAdd().equals("edited")) {
                System.out.print("\n" + contractDTO.getFileName() + "\n");
                    contract.setAttachFileAdd(this.attachFile(contractDTO, null, user));
                }
            } else {
                contract.setAttachFileAdd(oldContract.getAttachFileAdd());
            }

            PartnerContact contactPoint = null;
            if (contractDTO.getContactPointId() == -1) {
                if (contractDTO.getContactPoint() != null) {
                    contactPoint = contractDTO.getContactPoint();
                    contactPoint.setPartner(oldContract.getPartner());
                    partnerContactRepository.save(contactPoint);
                }
            } else if (contractDTO.getContactPointId() == 0) {
                contactPoint = null;
            } else {
                contactPoint = partnerContactRepository.findById(contractDTO.getContactPointId());
            }
            contract.setContactPoint(contactPoint);
            int contractId = contract.getId();
            UnitName unit = user.getUnitName();
            if(!user.getRole().equals(String.valueOf(Role.ADMIN_VNU))){
                if (!contractDTO.getUnitNames().isEmpty()) {
                    List<ContractDTO> contractDTOList = new ArrayList<ContractDTO>();
                    for (UnitName unitName : contractDTO.getUnitNames()) {
                        ContractDTO contractDTO1 = new ContractDTO();
                        contractDTO1.setId(contractId);
                        if (unitName.getId() != 0) {
                            contractDTO1.setUnitNameId(unitName.getId());
                            contractDTO1.setId(contractId);
                            contractDTO1.setResult("true");
                            contractDTOList.add(contractDTO1);
                        }
                    }
                    if (unit != null) {
                        ContractDTO contractDTO1 = new ContractDTO();
                        contractDTO1.setUnitNameId(unit.getId());
                        contractDTO1.setId(contractId);
                        contractDTO1.setResult("true");
                        contractDTOList.add(contractDTO1);
                    }
                    if (!contractDTOList.isEmpty()) {
                        this.shareContract(contractDTOList);
                    }
                }
            } else {
                if (!contractDTO.getRolesAndSigningLevelList().isEmpty()) {
                    List<ContractDTO> contractDTOList = new ArrayList<ContractDTO>();
                    for (RolesAndSigningLevel rolesAndSigningLevel1 : contractDTO.getRolesAndSigningLevelList()) {
                        ContractDTO contractDTO1 = new ContractDTO();
                        contractDTO1.setId(contractId);
                        if (rolesAndSigningLevel1.getId() != 0) {
                            contractDTO1.setRolesAndSigningLevelId(rolesAndSigningLevel1.getId());
                            contractDTO1.setId(contractId);
                            contractDTO1.setResult("true");
                            contractDTOList.add(contractDTO1);
                        }
                    }
                    if (!contractDTOList.isEmpty()) {
                        this.shareContractVnu(contractDTOList);
                    }
                }
            }
            contractRepository.save(contract);
            if (user.getRole().equals(String.valueOf(Role.UNIT))) {
                ActivityLog activityLog = new ActivityLog(user);
                userRepository.save(user);
                activityLog.setActivityType("editContract");
                activityLog.setAcvtivity(user.getUnitName().getUnitName() + " sửa Hoạt động hợp tác của đối tác: " +
                        contract.getPartner().getPartnerName() + " vào lúc " + activityLog.getTimestamp());
                activityLog.setStatus("NEW");
                activityLog.setContractId(contract.getId());
                activityLogRepository.save(activityLog);
            }
        } else {
            throw new NullPointerException("Không tìm thấy Hoạt động hợp tác này hãy thử lại!");
        }
    }

    public void removeRenew(String token, int contractId) throws Exception {
        User user = userRepository.findByToken(token);
        if(user != null){
            Contract contract = contractRepository.findById(contractId);
            if(user.getRole().equals(String.valueOf(Role.ADMIN_VNU)) || user.getRole().equals(String.valueOf(Role.ADMIN_UNIT))){
                if(contract.getRolesAndSigningLevel().getId() == user.getRolesAndSigningLevel().getId()){
                    contract.setRenew(false);
                } else {
                    throw new Exception("Không có quyền!");
                }
            } else if(user.getRole().equals(String.valueOf(Role.UNIT))){
                if(contract.getRolesAndSigningLevel().getId() == user.getUnitName().getRolesAndSigningLevel().getId()){
                    contract.setRenew(false);
                }
            }
            contractRepository.save(contract);
        } else {
            throw new Exception("User not found!");
        }
    }
}
