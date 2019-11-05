package uet.controller;

import uet.DTO.UetManDTO;
import uet.model.Role;
import uet.model.UetMan;
import uet.service.UetManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.stereotype.RequiredRoles;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by nhkha on 26/03/2017.
 */
@RestController
public class UetManController {
    private final
    UetManService uetManService;

    @Autowired
    public UetManController(UetManService uetManService) {
        this.uetManService = uetManService;
    }

    /**
     * Create UET Man
     *
     * @param uetManDTO
     * @param request
     * @return 
     */
    @RequiredRoles({Role.ADMIN_VNU, Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "uetMan/create", method = RequestMethod.POST)
    public UetMan createUnit(@RequestBody UetManDTO uetManDTO, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return uetManService.createUetMan(uetManDTO, token);
    }

    /**
     * Edit Unit, UET Man
     * @param uetManDTO
     * @param request 
     */
    @RequiredRoles({Role.ADMIN_VNU, Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "uetMan/edit", method = RequestMethod.PUT)
    public void editTypeContract(@RequestBody UetManDTO uetManDTO, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        uetManService.editUetMan(uetManDTO, token);
    }

    /**
     * Delete unit
     *
     * @param uetManId
     * @param request
     * @throws Exception 
     */
    @RequiredRoles({Role.ADMIN_VNU, Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "uetMan/{uetManId}/delete", method = RequestMethod.DELETE)
    public void deleteUnit(@PathVariable("uetManId") int uetManId, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        uetManService.deleteUetMan(uetManId, token);
    }

    /**
     * Show All UET Man
     *
     * @return 
     */
    @RequiredRoles({Role.ADMIN_VNU, Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "uetMan", method = RequestMethod.GET)
    public List<UetMan> getAll(){
        return uetManService.getAll();
    }

    /**
     * Get All UET Man Roles And Signing Level
     *
     * @param request
     * @return 
     */
    @RequiredRoles({Role.ADMIN_VNU, Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "uetMan/rolesAndSigningLevel", method = RequestMethod.GET)
    public List<UetMan> getAllUetManRolesAndSigningLevel(HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return uetManService.getAllUetManRolesAndSigningLevel(token);
    }
}
