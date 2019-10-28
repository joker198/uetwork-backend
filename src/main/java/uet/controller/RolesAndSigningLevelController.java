package uet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uet.DTO.UserDTO;
import uet.model.Role;
import uet.model.RolesAndSigningLevel;
import uet.model.User;
import uet.service.RolesAndSigningLevelService;
import uet.stereotype.RequiredRoles;

import java.util.List;

@RestController
public class RolesAndSigningLevelController {
    private final RolesAndSigningLevelService rolesAndSigningLevelService;

    @Autowired
    public RolesAndSigningLevelController(RolesAndSigningLevelService rolesAndSigningLevelService) {
        this.rolesAndSigningLevelService = rolesAndSigningLevelService;
    }

    //tạo tài khoản cho trường trong vnu
    @RequiredRoles(Role.ADMIN_VNU)
    @RequestMapping(value="/account/university/create",method = RequestMethod.POST)
    public void createAccountForUniversity(@RequestBody UserDTO userDTO) throws Exception {
        rolesAndSigningLevelService.createAccountForUniversity(userDTO);
    }

    //get
    @RequiredRoles(Role.ADMIN_VNU)
    @RequestMapping(value="/rolesAndSigningLevel",method = RequestMethod.GET)
    public List<RolesAndSigningLevel> getAllRolesAndSigningLevel() throws Exception {
        return rolesAndSigningLevelService.getAllRolesAndSigningLevel();
    }
}
