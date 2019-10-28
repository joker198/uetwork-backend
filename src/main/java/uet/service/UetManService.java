package uet.service;

import uet.DTO.UetManDTO;
import uet.model.*;
import uet.repository.ActivityLogRepository;
import uet.repository.ContractRepository;
import uet.repository.UetManRepository;
import uet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.http.HTTPException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhkha on 27/03/2017.
 */
@Service
public class UetManService {
    private final
    UetManRepository uetManRepository;

    private final
    ContractRepository contractRepository;

    private final
    UserRepository userRepository;

    private final
    ActivityLogRepository activityLogRepository;

    @Autowired
    public UetManService(UetManRepository uetManRepository, ContractRepository contractRepository, UserRepository userRepository, ActivityLogRepository activityLogRepository) {
        this.uetManRepository = uetManRepository;
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;
        this.activityLogRepository = activityLogRepository;
    }

    public UetMan createUetMan(UetManDTO uetManDTO, String token) {
        User user = userRepository.findByToken(token);
        int rolesSigningLevelId = 0;
        RolesAndSigningLevel rolesAndSigningLevel = user.getRolesAndSigningLevel();
        if(rolesAndSigningLevel != null){
            if(rolesAndSigningLevel.getName().equals("VNU")){
                rolesSigningLevelId = rolesAndSigningLevel.getId();
            } else if(user.getRole().equals(String.valueOf(Role.ADMIN_UNIT))){
                rolesSigningLevelId = rolesAndSigningLevel.getId();
            }
        } else if(user.getUnitName().getRolesAndSigningLevel() != null){
//            uetMan1.setRolesAndSigningLevel(user.getUnitName().getRolesAndSigningLevel().getParent_id());
            rolesSigningLevelId = user.getUnitName().getRolesAndSigningLevel().getParent_id().getId();
        }
        UetMan uetMan = uetManRepository.findByUetManNameAndRolesSigningLevelId(uetManDTO.getUetManName(), rolesSigningLevelId);
        if (uetMan == null){
            if (uetManDTO.getUetManName() != null){
                UetMan uetMan1 = new UetMan();
                uetMan1.setUetManName(uetManDTO.getUetManName());
                if(uetManDTO.getAbout() == null){
                    uetManDTO.setAbout(" ");
                }
                uetMan1.setAbout(uetManDTO.getAbout());
//                RolesAndSigningLevel rolesAndSigningLevel = user.getRolesAndSigningLevel();
                if(rolesAndSigningLevel != null){
                    if(rolesAndSigningLevel.getName().equals("VNU")){
                        uetMan1.setRolesAndSigningLevel(rolesAndSigningLevel);
                    } else if(user.getRole().equals(String.valueOf(Role.ADMIN_UNIT))){
                        uetMan1.setRolesAndSigningLevel(rolesAndSigningLevel);
                    }
                } else if(user.getUnitName().getRolesAndSigningLevel() != null){
                    uetMan1.setRolesAndSigningLevel(user.getUnitName().getRolesAndSigningLevel());
                }
                if(user.getRole().equals(String.valueOf(Role.UNIT))){
                    ActivityLog activityLog = new ActivityLog(user);
                    userRepository.save(user);
                    activityLog.setActivityType("createUetMan");
                    activityLog.setAcvtivity(user.getUnitName().getUnitName() + " tạo thêm Người kí (VNU-UET) " +
                            uetManDTO.getUetManName() + " vào lúc " + activityLog.getTimestamp());
                    activityLog.setStatus("NEW");
                    activityLogRepository.save(activityLog);
                }
                return uetManRepository.save(uetMan1);
            } else {
                throw new NullPointerException("Tên Người ký (VNU-UET) trống");
            }

        } else {
            throw new NullPointerException("Người ký (VNU-UET) đã tồn tại!");
        }
    }

    public void editUetMan(UetManDTO uetManDTO, String token) {
        UetMan uetMan = uetManRepository.findOne(uetManDTO.getId());
        if (uetMan != null){
            if (uetManDTO.getUetManName() != null){
                uetMan.setUetManName(uetManDTO.getUetManName());
                if(uetManDTO.getAbout() == null){
                    uetManDTO.setAbout(" ");
                }
                uetMan.setAbout(uetManDTO.getAbout());
                uetManRepository.save(uetMan);
                User user = userRepository.findByToken(token);
                if(user.getRole().equals(String.valueOf(Role.UNIT))){
                    ActivityLog activityLog = new ActivityLog(user);
                    userRepository.save(user);
                    activityLog.setActivityType("editUetMan");
                    activityLog.setAcvtivity(user.getUnitName().getUnitName() + " sửa tên Người kí (VNU-UET) " +
                            uetMan.getUetManName() + " thành: " + uetManDTO.getUetManName() + " vào lúc " + activityLog.getTimestamp());
                    activityLog.setStatus("NEW");
                    activityLogRepository.save(activityLog);
                }
            } else {
                throw new NullPointerException("Có lỗi khi sửa Người ký (VNU-UET)");
            }
        } else {
            throw new NullPointerException("Người ký (VNU-UET) không tồn tại!");
        }
    }

    public void deleteUetMan(int uetManId, String token) throws Exception {
        UetMan uetMan = uetManRepository.findById(uetManId);
        if (uetMan != null){
            if (!uetMan.getContract().isEmpty()){
                throw new Exception("Không thể xóa Người ký này vì đang nằm trong 1 số hợp đồng!");
            } else {
                uetManRepository.delete(uetManId);
//                User user = userRepository.findByToken(token);
//                if(user.getRole().equals(Role.UNIT)){
//                    ActivityLog activityLog = new ActivityLog(user);
//                    userRepository.save(user);
//                    activityLog.setActivityType("deleteUetMan");
//                    activityLog.setAcvtivity(user.getUnitName().getUnitName() + " xóa Người kí (VNU-UET) " +
//                            uetManRepository.findOne(uetManId).getUetManName() + " vào lúc " + activityLog.getTimestamp());
//                    activityLog.setStatus("NEW");
//                    activityLogRepository.save(activityLog);
//                }
            }
        }
        else {
            throw new NullPointerException("Không tồn tại Người ký (VNU-UET) này!");
        }
    }

    public List<UetMan> getAll() {
        return (List<UetMan>) uetManRepository.findAll();
    }

    public List<UetMan> getAllUetManRolesAndSigningLevel(String token){
        User user = userRepository.findByToken(token);
        if(user != null){
            if(user.getRole().equals(String.valueOf(Role.ADMIN_UNIT)) || user.getRole().equals(String.valueOf(Role.ADMIN_VNU))){
                //neu nhu vào trong if này tức là tk admin_vnu hoặc là admin_unit
                RolesAndSigningLevel rolesAndSigningLevel = user.getRolesAndSigningLevel();
                List<UetMan> uetManList = uetManRepository.findByRolesSigningLevelId(rolesAndSigningLevel.getId());
                if(!rolesAndSigningLevel.getChild().isEmpty()){
                    for(RolesAndSigningLevel rolesAndSigningLevel1 : rolesAndSigningLevel.getChild()){
                        uetManList.addAll(rolesAndSigningLevel1.getUetManList());
                    }
                }
                return uetManList;
//                if(rolesAndSigningLevel.getParent_id() == null){
//                    return uetManRepository.findByRolesSigningLevelId(rolesAndSigningLevel.getId());
//                } else {
//                    return null;
//                }
            } else if(user.getRole().equals(String.valueOf(Role.UNIT))){
                // nếu vào if này là tk unit
//                System.out.print("Khanh\n\n\n");
                    return uetManRepository.findByRolesSigningLevelId(user.getUnitName().getRolesAndSigningLevel().getId());
            } else {
                throw new HTTPException(404);
            }
        } else {
            throw new HTTPException(401);
        }
    }
}
