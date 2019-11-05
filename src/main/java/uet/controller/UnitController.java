package uet.controller;

import uet.DTO.UnitNameDTO;
import uet.model.Contract;
import uet.model.Role;
import uet.model.UnitName;
import uet.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.stereotype.RequiredRoles;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by nhkha on 26/03/2017.
 */
@RestController
public class UnitController {
    private final
    UnitService unitService;

    @Autowired
    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    /**
     * Create Unit
     *
     * @param unitNameDTO
     * @param request
     * @return 
     */
    @RequiredRoles({Role.ADMIN_UNIT, Role.ADMIN_VNU})
    @RequestMapping(value = "unit/create", method = RequestMethod.POST)
    public UnitName createUnit(@RequestBody UnitNameDTO unitNameDTO, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return unitService.createUnit(unitNameDTO, token);
    }

    /**
     * Edit Unit
     *
     * @param unitNameDTO 
     */
    @RequiredRoles({Role.ADMIN_UNIT, Role.ADMIN_VNU})
    @RequestMapping(value = "unit/edit", method = RequestMethod.PUT)
    public void editTypeContract(@RequestBody UnitNameDTO unitNameDTO){
        unitService.editUnit(unitNameDTO);
    }

    /**
     * Delete Unit
     *
     * @param unitId
     * @throws Exception 
     */
    @RequiredRoles({Role.ADMIN_UNIT, Role.ADMIN_VNU})
    @RequestMapping(value = "unit/{unitId}/delete", method = RequestMethod.DELETE)
    public void deleteUnit(@PathVariable("unitId") int unitId) throws Exception {
        unitService.deleteUnit(unitId);
    }

    /**
     * Show All Unit
     *
     * @return 
     */
    @RequestMapping(value = "unit", method = RequestMethod.GET)
    public List<UnitName> getAll(){
        return unitService.getAll();
    }

    /**
     * Get Unit Name By Roles And Signing Level
     *
     * @param request
     * @return 
     */
    @RequestMapping(value = "unit/rolesAndSigningLevel", method = RequestMethod.GET)
    public List<UnitName> getUnitNameByRolesAndSigningLevel(HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return unitService.getUnitNameByRolesAndSigningLevel(token);
    }
}
