package uet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.DTO.InfoBySchoolDTO;
import uet.model.InfoBySchool;
import uet.model.Role;
import uet.service.InfoBySchoolService;
import uet.stereotype.RequiredRoles;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Tu on 07-Jul-16.
 */
@RestController
public class InfoBySchoolController {
    private final InfoBySchoolService infoBySchoolService;

    @Autowired
    public InfoBySchoolController(InfoBySchoolService infoBySchoolService) {
        this.infoBySchoolService = infoBySchoolService;
    }

    /**
     * Create Info
     *
     * @param studentId
     * @param infoBySchoolDTO
     * @return 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value="/student/{studentId}/infoBySchool", method = RequestMethod.POST)
    public InfoBySchool createInfo(@PathVariable("studentId") int studentId, @RequestBody InfoBySchoolDTO infoBySchoolDTO){
        return infoBySchoolService.createInfo(studentId, infoBySchoolDTO);
    }

    /**
     * Show Info Of A Student
     *
     * @param infoId
     * @param request
     * @return 
     */
    @RequiredRoles({Role.STUDENT,Role.VIP_PARTNER,Role.ADMIN})
    @RequestMapping(value="/infoBySchool/{infoId}", method = RequestMethod.GET)
    public InfoBySchool getInfo(@PathVariable("infoId") int infoId, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return infoBySchoolService.getInfo(infoId, token);
    }

    /**
     * Show Info Of A Student By Student Id
     *
     * @param studentId
     * @return 
     */
    @RequiredRoles({Role.STUDENT,Role.VIP_PARTNER,Role.ADMIN})
    @RequestMapping(value="student/infoBySchool/{studentId}", method = RequestMethod.GET)
    public InfoBySchool getInfoByStudentId(@PathVariable("studentId") int studentId){
        return infoBySchoolService.getInfoByStudentId(studentId);
    }

    /**
     * Edit Class Info By School
     *
     * @param infoBySchoolDTO
     * @param request
     * @throws Exception 
     */
    @RequestMapping(value = "infoBySchool/class", method = RequestMethod.PUT)
    public void editClass(@RequestBody InfoBySchoolDTO infoBySchoolDTO, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        infoBySchoolService.editClass(infoBySchoolDTO, token);
    }

    /**
     * Edit Info Of A Student
     *
     * @param infoId
     * @param infoBySchoolDTO
     * @return 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value="/infoBySchool/{infoId}", method = RequestMethod.PUT)
    public InfoBySchool editInfo(@PathVariable("infoId") int infoId,@RequestBody InfoBySchoolDTO infoBySchoolDTO){
        return infoBySchoolService.editInfo(infoId, infoBySchoolDTO);
    }

    /**
     * Delete Info Of A Student
     *
     * @param infoId 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value="/infoBySchool/{infoId}", method = RequestMethod.DELETE)
    public void deleteInfo(@PathVariable("infoId") int infoId){
        infoBySchoolService.deleteInfo(infoId);
    }
}
