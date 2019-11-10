package uet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import uet.DTO.*;
import uet.config.GlobalConfig;
import uet.model.*;
import uet.repository.*;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tu on 28-Jun-16.
 */
@Service
public class PartnerService {
    final StudentRepository studentRepository;
    private final NationRepository nationRepository;
    private PartnerRepository partnerRepository;
    private final PartnerContactRepository partnerContactRepository;
    private UserRepository userRepository;
    private final ActivityLogRepository activityLogRepository;
    private PostRepository postRepository;
    private FollowRepository followRepository;
    private MessageRepository messageRepository;
    private FollowService followService;
    private InternshipTermRepository internshipTermRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private InternshipService internshipService;
    private final InternshipRepository internshipRepository;
    private final PartnerType partnerType;

    @Autowired
    public PartnerService(
        NationRepository nationRepository,
        PartnerContactRepository partnerContactRepository,
        UserRepository userRepository,
        ActivityLogRepository activityLogRepository,
        PartnerRepository partnerRepository,
        StudentRepository studentRepository,
        AnnualActivityRepository annualActivityRepository,
        PostRepository postRepository,
        PartnerType partnerType,
        FollowRepository followRepository,
        MessageRepository messageRepository,
        FollowService followService,
        InternshipTermRepository internshipTermRepository,
        SimpMessagingTemplate simpMessagingTemplate,
        InternshipRepository internshipRepository
    ) {
        this.userRepository = userRepository;
        this.partnerRepository = partnerRepository;
        this.studentRepository = studentRepository;
        this.nationRepository = nationRepository;
        this.partnerRepository = partnerRepository;
        this.partnerContactRepository = partnerContactRepository;
        this.userRepository = userRepository;
        this.activityLogRepository = activityLogRepository;
        this.postRepository = postRepository;
        this.followRepository = followRepository;
        this.messageRepository = messageRepository;
        this.followService = followService;
        this.internshipTermRepository = internshipTermRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.internshipRepository = internshipRepository;
        this.partnerType = partnerType;
    }

    //show list all partner
    public List<Partner> getPartners()
    {
        List<Partner> allPartners = (List<Partner>) partnerRepository.findAll();
        return allPartners;
    }

    //partner search students
    public List<Student> searchStudent(StudentDTO studentDTO)
    {
        List<Student> allStudentMatched = (List<Student>) studentRepository.findByFullNameContaining(studentDTO.getFullName());
        return allStudentMatched;
    }

    //edit  of a partner
    public Partner editInfo(int partnerId, PartnerDTO partnerDTO, String token)
    {
        User user = userRepository.findByToken(token);
        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            Partner partner = partnerRepository.findOne(partnerId);
            if (partnerDTO.getPartnerName() != null) {
                partner.setPartnerName(partnerDTO.getPartnerName());
            }
            if (partnerDTO.getTaxCode() != null) {
                partner.setTaxCode(partnerDTO.getTaxCode());
            }
            if (partnerDTO.getDirector() != null) {
                partner.setDirector(partnerDTO.getDirector());
            }
            if (partnerDTO.getFieldWork() != null) {
                partner.setFieldWork(partnerDTO.getFieldWork());
            }
            if (partnerDTO.getWebsite() != null) {
                partner.setWebsite(partnerDTO.getWebsite());
            }
            if (partnerDTO.getAddress() != null) {
                partner.setAddress(partnerDTO.getAddress());
            }
            if (partnerDTO.getPhone() != null) {
                partner.setPhone(partnerDTO.getPhone());
            }
            if (partnerDTO.getFax() != null) {
                partner.setFax(partnerDTO.getFax());
            }
            if (partnerDTO.getEmail() != null) {
                partner.setEmail(partnerDTO.getEmail());
            }
            return partnerRepository.save(partner);
        } else {
            Partner partner = user.getPartner();
            if (partnerDTO.getPartnerName() != null) {
                partner.setPartnerName(partnerDTO.getPartnerName());
            }
            if (partnerDTO.getTaxCode() != null) {
                partner.setTaxCode(partnerDTO.getTaxCode());
            }
            if (partnerDTO.getDirector() != null) {
                partner.setDirector(partnerDTO.getDirector());
            }
            if (partnerDTO.getFieldWork() != null) {
                partner.setFieldWork(partnerDTO.getFieldWork());
            }
            if (partnerDTO.getWebsite() != null) {
                partner.setWebsite(partnerDTO.getWebsite());
            }
            if (partnerDTO.getAddress() != null) {
                partner.setAddress(partnerDTO.getAddress());
            }
            if (partnerDTO.getPhone() != null) {
                partner.setPhone(partnerDTO.getPhone());
            }
            if (partnerDTO.getFax() != null) {
                partner.setFax(partnerDTO.getFax());
            }
            if (partnerDTO.getEmail() != null) {
                partner.setEmail(partnerDTO.getEmail());
            }
            return partnerRepository.save(partner);
        }
    }

    //change Logo
    public void changeLogo(PartnerDTO partnerDTO, String token) throws IOException, IOException
    {
        User user = userRepository.findByToken(token);
        Partner partner = user.getPartner();
        String username = user.getUserName();
        String pathname = GlobalConfig.sourceAddress + "/app/users_data/" + username;
        File directory = new File(pathname);
        if (!directory.exists()) {
            directory.mkdir();
        }
        pathname = "../ " + GlobalConfig.sourceAddress + "/app/users_data/" + username + "/logo/";
        String directoryName = "/users_data/" + username + "/logo/";
        String fileName = username + "_logo.jpg";
        directory = new File(pathname);
        if (!directory.exists()) {
            directory.mkdir();
        }
        byte[] btDataFile = DatatypeConverter.parseBase64Binary(partnerDTO.getLogo());
        File of = new File(pathname + fileName);
        FileOutputStream osf = new FileOutputStream(of);
        osf.write(btDataFile);
        osf.flush();
        partner.setLogo(directoryName + username + "_logo.jpg");
        partnerRepository.save(partner);
    }

    //get partner vip logo
    public List<HashMap<String, String>> getPartnerViplogo()
    {
        List<HashMap<String, String>> listPartner = new ArrayList<HashMap<String, String>>();
        List<User> Users = (List<User>) userRepository.findByRoleAndStatus(String.valueOf(Role.VIP_PARTNER), "A");
        for (User user : Users) {
            HashMap<String, String> lPartner = new HashMap<String, String>();
            Partner partner = user.getPartner();
            lPartner.put("partnerName", partner.getPartnerName());
            lPartner.put("logo", partner.getLogo());
            lPartner.put("partnerId", String.valueOf(partner.getId()));
            listPartner.add(lPartner);
        }
        return listPartner;
    }

    public List<HashMap<String, String>> getPartnerIdAndPartnerName(String string)
    {
        List<HashMap<String, String>> listNameAndId = new ArrayList<HashMap<String, String>>();
        List<Partner> listPartner = (List<Partner>) partnerRepository.findAll();
        if (string.equals("true")) {
            for (Partner partner : listPartner) {
                if (partner.getUser() != null) {
                    HashMap<String, String> hashMapNameAndId = new HashMap<String, String>();
                    hashMapNameAndId.put("partnerName", partner.getPartnerName());
                    hashMapNameAndId.put("partnerId", String.valueOf(partner.getId()));
                    listNameAndId.add(hashMapNameAndId);
                }
            }
        } else if (string.equals("false")){
            for (Partner partner : listPartner) {
                if (partner.getUser() == null) {
                    HashMap<String, String> hashMapNameAndId = new HashMap<String, String>();
                    hashMapNameAndId.put("partnerName", partner.getPartnerName());
                    hashMapNameAndId.put("partnerId", String.valueOf(partner.getId()));
                    listNameAndId.add(hashMapNameAndId);
                }
            }
        } else if (string.equals("FIT")){
            for (Partner partner : listPartner) {
                if (partner.getPartnerType().equals(string)) {
                    HashMap<String, String> hashMapNameAndId = new HashMap<String, String>();
                    hashMapNameAndId.put("partnerName", partner.getPartnerName());
                    hashMapNameAndId.put("partnerId", String.valueOf(partner.getId()));
                    listNameAndId.add(hashMapNameAndId);
                }
            }
        }

        return listNameAndId;
    }

    public void deletePartner(int partnerId) {
        Partner partner = partnerRepository.findById(partnerId);
        if (partner != null) {
            if (partner.getInternships().isEmpty() && partner.getPost().isEmpty()) {
                if (partner.getUser() != null) {
                    userRepository.delete(partner.getUser());
                }
                partnerRepository.delete(partner);
            } else {
                throw new NullPointerException("Không thẻ xóa partner");
            }
        } else {
            throw new NullPointerException("Partner not found!");
        }
    }


    public List<Partner> getOtherPartner()
    {
        return partnerRepository.findByPartnerType(partnerType.getOtherType());
    }

    public List<Partner> getFitPartner()
    {
        return partnerRepository.findByPartnerType(partnerType.getFitType());
    }

    public Partner createPartner(PartnerDTO partnerDTO, String token) throws Exception
    {
        Nation nation = nationRepository.findOne(partnerDTO.getNationId());
        if (nation != null) {
            Partner partner1 = partnerRepository.findByPartnerName(partnerDTO.getPartnerName());
            if (partner1 == null) {
                Partner partner = new Partner(nation);
                partner.setPartnerName(partnerDTO.getPartnerName());
                partner.setPartnerType(partnerType.getFitType());
                partner.setBirthday(partnerDTO.getBirthday());
                partner.setDirector(partnerDTO.getDirector());
                partner.setEmail(partnerDTO.getEmail());
                partner.setAddress(partnerDTO.getAddress());
                partner.setWebsite(partnerDTO.getWebsite());
                partner.setPhone(partnerDTO.getPhone());
                partnerRepository.save(partner);
                if (partnerDTO.getPartnerContact() != null) {
                    if (!partnerDTO.getPartnerContact().getContactName().equals("")) {
                        PartnerContact partnerContact = partnerDTO.getPartnerContact();
                        partnerContact.setPartner(partner);
                        partnerContactRepository.save(partnerContact);
                    }
                }
                User user = userRepository.findByToken(token);
                if (user.getRole().equals(String.valueOf(Role.UNIT))) {
                    ActivityLog activityLog = new ActivityLog(user);
                    userRepository.save(user);
                    activityLog.setActivityType("createPartner");
                    activityLog.setAcvtivity(user.getUnitName().getUnitName() + " tạo Đối tác " +
                            partnerDTO.getPartnerName() + " vào lúc " + activityLog.getTimestamp());
                    activityLog.setStatus("NEW");
                    activityLogRepository.save(activityLog);
                }
                partner.setPartnerContacts(partnerContactRepository.findByPartnerId(partner.getId()));
                return partner;
            } else {
                throw new Exception("Tên đối tác đã tồn tại");
            }
        } else {
            throw new Exception("Không tìm thấy Quốc gia!");
        }
    }

    public void deletePartner(int partnerId, String token)
    {
        Partner partner = partnerRepository.findById(partnerId);
        if (partner != null) {
            partnerRepository.delete(partner);
            User user = userRepository.findByToken(token);
            if (user.getRole().equals(String.valueOf(Role.UNIT))) {
                ActivityLog activityLog = new ActivityLog(user);
                userRepository.save(user);
                activityLog.setActivityType("deletePartner");
                activityLog.setAcvtivity(user.getUnitName().getUnitName() + " xóa Đối tác " +
                        partner.getPartnerName() + " vào lúc " + activityLog.getTimestamp());
                activityLog.setStatus("NEW");
                activityLogRepository.save(activityLog);
            }
        } else {
            throw new NullPointerException("Không tìm thấy đối tác!");
        }
    }

    public Partner editPartnerInfo(PartnerDTO partnerInfoDTO, String token)
    {
        Partner partner = partnerRepository.findById(partnerInfoDTO.getPartnerId());
        if (partner != null) {
            partner.setPartnerName(partnerInfoDTO.getPartnerName());
            partner.setTaxCode(partnerInfoDTO.getTaxCode());
            partner.setDirector(partnerInfoDTO.getDirector());
            partner.setFieldWork(partnerInfoDTO.getFieldWork());
            partner.setWebsite(partnerInfoDTO.getWebsite());
            partner.setAddress(partnerInfoDTO.getAddress());
            partner.setPhone(partnerInfoDTO.getPhone());
            partner.setFax(partnerInfoDTO.getFax());
            partner.setEmail(partnerInfoDTO.getEmail());
            partner.setDescription(partnerInfoDTO.getDescription());
            partner.setBirthday(partnerInfoDTO.getBirthday());
            if (partnerInfoDTO.getNationId() != 0) {
                Nation nation = nationRepository.findOne(partnerInfoDTO.getNationId());
                if (nation != null) {
                    partner.setNation(nation);
                    partnerRepository.save(partner);
                } else {
                    throw new NullPointerException("Không tìm thấy Quóc gia! " + partnerInfoDTO.getNationId());
                }
            }
            partnerRepository.save(partner);
            User user = userRepository.findByToken(token);
            if (user.getRole().equals(String.valueOf(Role.UNIT))) {
                ActivityLog activityLog = new ActivityLog(user);
                userRepository.save(user);
                activityLog.setActivityType("editPartnerInfo");
                activityLog.setAcvtivity(user.getUnitName().getUnitName() + " sửa thông tin Đối tác " +
                        partner.getPartnerName() + " vào lúc " + activityLog.getTimestamp());
                activityLog.setStatus("NEW");
                activityLogRepository.save(activityLog);
            }
            return partner;
        } else {
            throw new NullPointerException("Không tìm thấy Đối tác!");
        }
    }

    public PartnerContact createPartnerContact(int partnerId, PartnerContactDTO partnerContactDTO, String token)
    {
        Partner partner = partnerRepository.findById(partnerId);
        if (partner != null) {
            PartnerContact partnerContact = new PartnerContact(partnerContactDTO.getContactName(),
                    partnerContactDTO.getPhone(), partnerContactDTO.getEmail(), partnerContactDTO.getSkype(),
                    partnerContactDTO.getAbout(), partner);
            partnerContactRepository.save(partnerContact);
            User user = userRepository.findByToken(token);
            if (user.getRole().equals(String.valueOf(Role.UNIT))) {
                ActivityLog activityLog = new ActivityLog(user);
                userRepository.save(user);
                activityLog.setActivityType("createPartnerContact");
                activityLog.setAcvtivity(user.getUnitName().getUnitName() + " tạo liên hệ cho Đối tác " +
                        partner.getPartnerName() + " vào lúc " + activityLog.getTimestamp());
                activityLog.setStatus("NEW");
                activityLogRepository.save(activityLog);
            }
            return partnerContact;
        } else {
            throw new NullPointerException("Không tìm thấy Đối tác!");
        }
    }

    public void editPartnerContact(PartnerContactDTO partnerContactDTO, String token)
    {
        PartnerContact partnerContact = partnerContactRepository.findById(partnerContactDTO.getId());
        if (partnerContact != null) {
            partnerContact.setContactName(partnerContactDTO.getContactName());
            partnerContact.setEmail(partnerContactDTO.getEmail());
            partnerContact.setSkype(partnerContactDTO.getSkype());
            partnerContact.setAbout(partnerContactDTO.getAbout());
            partnerContact.setPhone(partnerContactDTO.getPhone());
            partnerContactRepository.save(partnerContact);
            User user = userRepository.findByToken(token);
            if (user.getRole().equals(String.valueOf(Role.UNIT))) {
                ActivityLog activityLog = new ActivityLog(user);
                userRepository.save(user);
                activityLog.setActivityType("editPartnerInfo");
                activityLog.setAcvtivity(user.getUnitName().getUnitName() + " sửa liên hệ: " + partnerContact.getContactName() + " của Đối tác " +
                        partnerContact.getPartner().getPartnerName() + " vào lúc " + activityLog.getTimestamp());
                activityLog.setStatus("NEW");
                activityLogRepository.save(activityLog);
            }
        } else {
            throw new NullPointerException("Không tìm thấy Đối Tác");
        }
    }

    public void checkPartner(List<PartnerDTO> listpartnerDTO, String token) throws Exception {
        InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
        User user = userRepository.findByToken(token);
        for (PartnerDTO partnerDTO : listpartnerDTO) {
            Partner partner = partnerRepository.findById(partnerDTO.getId());
            List<Follow> follow = followRepository.findByPartnerIdAndInternshipTerm(partner.getId(), internshipTerm.getId());
            for (Follow follow1 : follow) {
                Internship internship = follow1.getInternship();
                Student student = internship.getStudent();
                if (partnerDTO.getStatus() == null) {
                    follow1.setInternshipTerm(internshipTerm.getId());
                    follow1.setStatus("PASS");
                    followRepository.save(follow1);
                    Message message = new Message();
                    message.setTitle("Thông báo xác nhận thông tin thực tập");
                    message.setContent("Đăng kí thực tập tại " + partner.getPartnerName() + " thành công." + "<br /></a>");
                    message.setStatus("NEW");
                    message.setSendDate(new Date());
                    message.setSenderName("pdt");
                    message.setReceiverName(user.getUserName());
                    message.setMessageType(MessageType.Normal);
                    message.setLastUpdated(new Date());
                    message.setUser(student.getUser());
                    messageRepository.save(message);
                    simpMessagingTemplate.convertAndSend("/user/" + message.getReceiverName() + "/**", message);

                } else {
                    follow1.setInternshipTerm(internshipTerm.getId());
                    follow1.setStatus("FAIL");
                    followRepository.save(follow1);
                    Message message = new Message();
                    message.setTitle("Thông báo về việc đăng ký công ty thực tập");
                    message.setContent("Đăng kí thực tập tại " + partner.getPartnerName() + " không được chấp nhận. Bạn có thể đăng ký công ty khác hoặc đăng ký thực tập tại đối tác của Khoa.");
                    message.setStatus("NEW");
                    message.setReceiverName(student.getUser().getUserName());
                    message.setMessageType(MessageType.Normal);
                    message.setSenderName(user.getUserName());
                    message.setUser(student.getUser());
                    message.setSendDate(new Date());
                    message.setLastUpdated(new Date());
                    messageRepository.save(message);
                    simpMessagingTemplate.convertAndSend("/user/" + message.getReceiverName() + "/**", message);
                }
            }
            if(partnerDTO.getStatus() != null){
                if(partnerDTO.getStatus().equals("false")){
                    partner.setStatus("NOT_ACCEPTED");
                    partnerRepository.save(partner);
                }
            } else if(partnerDTO.getStatus() == null){
                partner.setStatus("ACCEPTED");
                partnerRepository.save(partner);
            }
        }
    }

    public void deletePartnerContact(int contactId, String token)
    {
        PartnerContact partnerContact = partnerContactRepository.findById(contactId);
        if (partnerContact != null) {
            if (partnerContact.getContract().isEmpty()) {
                partnerContactRepository.delete(contactId);
                User user = userRepository.findByToken(token);
                if (user.getRole().equals(String.valueOf(Role.UNIT))) {
                    ActivityLog activityLog = new ActivityLog(user);
                    userRepository.save(user);
                    activityLog.setActivityType("deletePartnerContact");
                    activityLog.setAcvtivity(user.getUnitName().getUnitName() + " xóa liên hệ: " + partnerContact.getContactName() + " của Đối tác " +
                            partnerContact.getPartner().getPartnerName() + " vào lúc " + activityLog.getTimestamp());
                    activityLog.setStatus("NEW");
                    activityLogRepository.save(activityLog);
                }
            } else {
                throw new NullPointerException("Không thể xóa liên hệ này vì có 1 số hợp đồng được kí bởi liên hệ này!");
            }
        } else {
            throw new NullPointerException("Không tìm thấy Liên lạc cần xóa!");
        }
    }

    public List<Partner> showAllPartner()
    {
        return (List<Partner>) partnerRepository.findAll();
    }

    public Partner showPartner(int partnerId)
    {
        return partnerRepository.findById(partnerId);
    }

    public List<PartnerContact> showAllPartnerContact(int partnerId)
    {
        return partnerRepository.findById(partnerId).getPartnerContacts();
    }

    public List<PartnerDTO> getPartnerAndId()
    {
        return partnerRepository.findPartnerNameAndId();
    }

    public List<Partner> getAllWaitPartner()
    {
        return partnerRepository.findByStatus("WAIT");
    }

    public Partner findPartnerByContactId(int partnerContactId) throws Exception
    {
        PartnerContact partnerContact = partnerContactRepository.findById(partnerContactId);
        if(partnerContact != null){
            return partnerContact.getPartner();
        } else {
            throw new Exception("Partner not found!");
        }
    }

    public Partner findPartnerByPostId(int postId) throws Exception
    {
        Post post = postRepository.findById(postId);
        if(post != null){
            return post.getPartner();
        } else {
            throw new Exception("Partner not found!");
        }
    }

    public List<PartnerDTO> getPartnerNameAndIdOfOtherPartner()
    {
        return partnerRepository.findPartnerNameAndIdOfOther();
    }

    public List<PartnerDTO> getPartnerNameAndIdOfFitPartner()
    {
        return partnerRepository.findPartnerNameAndIdOfFit();
    }

    public void resetStatusPartnerType()
    {
        List<Partner> partners = partnerRepository.findByPartnerType(partnerType.getOtherType());
        for(Partner partner : partners){
            partner.setStatus(null);
            partnerRepository.save(partner);
        }
    }
}
