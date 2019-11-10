package uet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.DTO.UserDTO;
import uet.config.GlobalConfig;
import uet.model.Role;
import uet.model.RolesAndSigningLevel;
import uet.model.User;
import uet.repository.RolesAndSigningLevelRepository;
import uet.repository.UserRepository;

import javax.xml.ws.http.HTTPException;
import java.io.File;
import java.util.List;

@Service
public class RolesAndSigningLevelService {

    private final UserRepository userRepository;
    private final RolesAndSigningLevelRepository rolesAndSigningLevelRepository;

    @Autowired
    public RolesAndSigningLevelService(
        UserRepository userRepository,
        RolesAndSigningLevelRepository rolesAndSigningLevelRepository
    ) {
        this.userRepository = userRepository;
        this.rolesAndSigningLevelRepository = rolesAndSigningLevelRepository;
    }

    public void createAccountForUniversity(UserDTO userDTO) throws Exception
    {
        if(userDTO.getUserName() != null && userDTO.getPassword() != null & userDTO.getRolesAndSigningLevel() != null){
            User user = userRepository.findByUserName(userDTO.getUserName());
            if(user == null){
                RolesAndSigningLevel rolesAndSigningLevel = rolesAndSigningLevelRepository.findByName(String.valueOf(Role.VNU) + "-" + userDTO.getRolesAndSigningLevel());
                if (rolesAndSigningLevel == null) {
                    RolesAndSigningLevel rolesAndSigningLevel1 = new RolesAndSigningLevel();
                    rolesAndSigningLevel1.setName(String.valueOf(Role.VNU) + "-" + userDTO.getRolesAndSigningLevel());
                    rolesAndSigningLevel1.setRole(String.valueOf(Role.ADMIN_UNIT));
                    rolesAndSigningLevel1.setUniversityName(userDTO.getUniversityName());
                    rolesAndSigningLevelRepository.save(rolesAndSigningLevel1);
                    User user1 = new User();
                    user1.setUserName(userDTO.getUserName());
                    user1.setPassword(userDTO.getPassword());
                    user1.setRole(rolesAndSigningLevel1.getRole());
                    user1.setStatus("A");
                    userRepository.save(user1);
                    String pathname = GlobalConfig.sourceAddress + "/app/users_data/" + user1.getUserName() + "/";
                    File directory = new File(pathname);
                    if (!directory.exists()) {
                        directory.mkdir();
                    }
                    pathname = GlobalConfig.sourceAddress + "/app/users_data/" + user1.getUserName() + "/report";
                    directory = new File(pathname);
                    if (!directory.exists()) {
                        directory.mkdir();
                    }
                    rolesAndSigningLevel1.setUser(user1);
                    rolesAndSigningLevelRepository.save(rolesAndSigningLevel1);
                } else {
                    throw new Exception("rolesAndSigningLevel exist");
                }
            } else {
                throw new Exception("Username exist");
            }
        } else {
            throw new HTTPException(400);
        }
    }

    public List<RolesAndSigningLevel> getAllRolesAndSigningLevel()
    {
        return (List<RolesAndSigningLevel>) rolesAndSigningLevelRepository.findByRole("ADMIN_UNIT");
    }

    public void deleteRolesAndSigningLevel(String token, int rolesAndSigingLevelId, String forceDelete)
    {
        User user = userRepository.findByToken(token);
        if(user != null){
            //
        }
    }
}
