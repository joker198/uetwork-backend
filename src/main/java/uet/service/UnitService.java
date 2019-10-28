package uet.service;

import uet.DTO.UnitNameDTO;
import uet.model.*;
import uet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.http.HTTPException;
import java.util.List;


/**
 * Created by nhkha on 26/03/2017.
 */
@Service
public class UnitService {
    private final
    UnitNameRepository unitNameRepository;

    private final
    ContractRepository contractRepository;

    private final UserRepository userRepository;
    private final RolesAndSigningLevelRepository rolesAndSigningLevelRepository;
    private final ContractShareRepository contractShareRepository;
    private final UetManRepository uetManRepository;
    private final ActivityLogRepository activityLogRepository;

    @Autowired
    public UnitService(UnitNameRepository unitNameRepository, ContractRepository contractRepository, UserRepository userRepository, RolesAndSigningLevelRepository rolesAndSigningLevelRepository, ContractShareRepository contractShareRepository, UetManRepository uetManRepository, ActivityLogRepository activityLogRepository) {
        this.unitNameRepository = unitNameRepository;
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;
        this.rolesAndSigningLevelRepository = rolesAndSigningLevelRepository;
        this.contractShareRepository = contractShareRepository;
        this.uetManRepository = uetManRepository;
        this.activityLogRepository = activityLogRepository;
    }

    public UnitName createUnit(UnitNameDTO unitNameDTO, String token) {
        if(unitNameDTO.getUnitName() != null && unitNameDTO.getAbbreviation() != null){
            User user = userRepository.findByToken(token);
            RolesAndSigningLevel rolesAndSigningLevel = user.getRolesAndSigningLevel();
            if(user.getRole().equals(String.valueOf(Role.ADMIN_UNIT))){
//!rolesAndSigningLevel.getChild().isEmpty()
                Boolean checkUnitName = true;
                for(RolesAndSigningLevel rolesAndSigningLevel1 : rolesAndSigningLevel.getChild()){
                    if(unitNameDTO.getUnitName().equals(rolesAndSigningLevel1.getUnitName().getUnitName())){
                        checkUnitName = false;
                    }
                }
                if(checkUnitName){
                    UnitName unitName = new UnitName();
                    unitName.setUnitName(unitNameDTO.getUnitName());
                    String[] name = user.getRolesAndSigningLevel().getName().split("-");
                    RolesAndSigningLevel rolesAndSigningLevel1 = new RolesAndSigningLevel();
                    for(String n : name){
                        if(!n.trim().equals("VNU")){
                            rolesAndSigningLevel1.setName(n.trim() + "-" + unitNameDTO.getAbbreviation());
                            unitName.setRoles("UNIT-" + n.trim());
                        }
                    }
                    unitName.setAbbreviation(unitNameDTO.getAbbreviation());
                    rolesAndSigningLevel1.setParent_id(rolesAndSigningLevel);
                    rolesAndSigningLevelRepository.save(rolesAndSigningLevel1);
                    unitName.setRolesAndSigningLevel(rolesAndSigningLevel1);
                    return unitNameRepository.save(unitName);
//                    return user.getRolesAndSigningLevel();
                } else {
                    throw new NullPointerException("Đơn vị này đã tồn tại!");
                }

            } else {
                throw new NullPointerException("Có lỗi xảy ra khi tạo đơn vị!");
            }
        } else {
            throw new NullPointerException("Có lỗi xảy ra khi tạo đơn vị!");
        }

    }

    public void editUnit(UnitNameDTO unitNameDTO){
        UnitName unit = unitNameRepository.findOne(unitNameDTO.getId());
        if (unit != null){
            if (unitNameDTO.getUnitName() != null){
                unit.setUnitName(unitNameDTO.getUnitName());
                unit.setAbbreviation(unitNameDTO.getAbbreviation());
                unitNameRepository.save(unit);
            }else {
                throw new NullPointerException("Tên đơn vị trống!");
            }
        } else {
            throw new NullPointerException("Đơn vị không tồn tại!");
        }
    }

    public void deleteUnit(int unitId) throws Exception {
        UnitName unitName = unitNameRepository.findById(unitId);
        if (unitName != null){
            if (!contractShareRepository.findByUnitNameId(unitId).isEmpty()){
//                return contractRepository.findByUnitNameId(unitId);
                throw new Exception("Không thể xóa đơn vị, đơn vị này đang theo dõi 1 số hoạt động hợp tác!");
            } else {
                System.out.print("\n\n\n");
                List<UetMan> uetManList = unitName.getRolesAndSigningLevel().getUetManList();
                if(!uetManList.isEmpty()){
                    uetManRepository.delete(uetManList);
                }
                if(unitName.getUser() != null){
                    if(!unitName.getUser().getActivityLog().isEmpty()){
                        activityLogRepository.delete(unitName.getUser().getActivityLog());
                    }
                }
                if(unitName.getUser() != null){
                    userRepository.delete(unitName.getUser());
                }
                if(unitName.getRolesAndSigningLevel() != null){
                    rolesAndSigningLevelRepository.delete(unitName.getRolesAndSigningLevel());
                }
//                unitNameRepository.delete(unitName);
            }
        }
        else {
            throw new NullPointerException("Không tồn tại đơn vị này!");
        }
    }

    public List<UnitName> getAll() {
        return (List<UnitName>) unitNameRepository.findAll();
    }

    public List<UnitName> getUnitNameByRolesAndSigningLevel(String token){
        User user = userRepository.findByToken(token);
        if(user != null){
            int rolesAndSigningLevel = 0;
            String roles = "";
            if(user.getRole().equals(String.valueOf(Role.ADMIN_UNIT))){
                String[] name = user.getRolesAndSigningLevel().getName().split("-");
                for(String n : name){
                    if(!n.trim().equals("VNU")){
                        roles = "UNIT-" + n;
                    }
                }
            } else if(user.getRole().equals(String.valueOf(Role.UNIT))){
                roles = user.getUnitName().getRoles();
            }
            return unitNameRepository.findByRoles(roles);
        } else
            throw new HTTPException(500);
    }
    //create
}
