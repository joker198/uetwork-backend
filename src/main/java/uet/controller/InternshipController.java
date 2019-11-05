package uet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.DTO.InternshipDTO;
import uet.DTO.StudentDTO;
import uet.model.Internship;
import uet.model.Role;
import uet.service.InternshipService;
import uet.stereotype.RequiredRoles;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by fgv on 7/12/2016.
 */
@RestController
public class InternshipController {
    private final InternshipService internshipService;

    @Autowired
    public InternshipController(InternshipService internshipService) {
        this.internshipService = internshipService;
    }

    /**
     * Show All Internship
     *
     * @param request
     * @return 
     */
    @RequiredRoles({Role.ADMIN, Role.VIP_PARTNER})
    @RequestMapping(value = "/intern", method = RequestMethod.GET)
    public List<Internship> getAllInterns(HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        return internshipService.getAllIntern(token);
    }

    /**
     * Show All Internship Of A Partner
     *
     * @param partnerId
     * @param request
     * @return 
     */
    @RequiredRoles({Role.ADMIN, Role.VIP_PARTNER, Role.NORMAL_PARTNER})
    @RequestMapping(value = "/partner/{partnerId}/internship", method = RequestMethod.GET)
    public List<Internship> getAllInPartner(@PathVariable int partnerId, HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        return internshipService.getAllInPartner(partnerId, token);
    }

    /**
     * Create Internship From Excel
     *
     * @param List
     * @param request 
     */
    @RequiredRoles({Role.ADMIN, Role.VIP_PARTNER})
    @RequestMapping(value = "/partner/createInternship", method = RequestMethod.PUT)
    public void createInternship(@RequestBody List<InternshipDTO> List, HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        internshipService.createMultiInternship(List, token);
    }

    /**
     * Lecturers Adds Final Score
     *
     * @param internId
     * @param score
     * @param request
     * @throws Exception 
     */
    @RequiredRoles({Role.ADMIN, Role.LECTURERS})
    @RequestMapping(value = "/intern/{internId}/addscore", method = RequestMethod.PUT)
    public void addScore(@PathVariable("internId") int internId, @RequestBody float score, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        internshipService.addScore(internId, score, token);
    }


    /**
     * Lecturers Adds Final Score
     *
     * @param internshipDTOS
     * @param request
     * @return
     * @throws Exception 
     */
    @RequiredRoles({Role.ADMIN, Role.LECTURERS})
    @RequestMapping(value = "/intern/addscore", method = RequestMethod.POST)
    public List<Integer> addMultiScore(@RequestBody List<InternshipDTO> internshipDTOS, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        return internshipService.addMultiScore(internshipDTOS, token);
    }

    /**
     * Students Adds Final Report
     *
     * @param internshipDTO
     * @param request
     * @throws Exception 
     */
    @RequiredRoles({Role.STUDENT})
    @RequestMapping(value = "/intern/addFinalReport", method = RequestMethod.POST)
    public void addFinalReport(@RequestBody InternshipDTO internshipDTO, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        internshipService.addFinalReport(internshipDTO, token);
    }

    /**
     * Edit A Internship
     *
     * @param id
     * @param internshipDTO
     * @param request
     * @return 
     */
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "/intern/{internId}", method = RequestMethod.PUT)
    public Internship changeInternById(@PathVariable("internId") int id, @RequestBody InternshipDTO internshipDTO,
                                       HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        return internshipService.changeById(id, internshipDTO, token);
    }

    /**
     * Delete A Internship
     *
     * @param id 
     */
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "/intern/{internId}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable("internId") int id) {
//        String token = request.getHeader("auth-token");
        internshipService.deleteById(id);
    }

    /**
     * Select Internship Partner Id
     *
     * @param followId
     * @param request
     * @return
     * @throws Exception 
     */
    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "select/intern/{followId}", method = RequestMethod.POST)
    public Internship selectInternPartnerId(@PathVariable("followId") int followId, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        return internshipService.selectInternPartnerId(followId, token);
    }

    /**
     * Get All Internship Of Student
     *
     * @param request
     * @return
     * @throws Exception 
     */
    @RequiredRoles({Role.STUDENT})
    @RequestMapping(value = "/student/internship", method = RequestMethod.GET)
    public List<Internship> getAllInternshipOfStudent(HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        return internshipService.getAllInternshipOfStudent(token);
    }

    /**
     * Add Score By Excel
     *
     * @param request
     * @param studentDTO
     * @return
     * @throws Exception 
     */
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "/score/excel/add", method = RequestMethod.POST)
    public List<StudentDTO> addScoreByExcel(HttpServletRequest request, @RequestBody List<StudentDTO> studentDTO) throws Exception {
        String token = request.getHeader("auth-token");
        return internshipService.AddScoreByExcel(studentDTO, token);
    }

    /**
     * Check Excel Add Score
     *
     * @param request
     * @param studentDTO
     * @return
     * @throws Exception 
     */
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "/score/excel/check", method = RequestMethod.POST)
    public List<StudentDTO> checkExcelAddScore(HttpServletRequest request, @RequestBody List<StudentDTO> studentDTO) throws Exception {
        String token = request.getHeader("auth-token");
        return internshipService.checkExcelAddScore(studentDTO);
    }

    /**
     * Submit Score
     *
     * @param request
     * @throws Exception 
     */
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "noti/score/on", method = RequestMethod.POST)
    public void submitScore(HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        internshipService.submitScore(token);
    }

    /**
     * Get Current Internship Of Internship Term
     *
     * @param request
     * @return
     * @throws Exception 
     */
    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "/internship", method = RequestMethod.GET)
    public Internship getCurrentInternshipOfInternshipTerm(HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        return internshipService.getCurrentInternshipOfInternshipTerm(token);

    }

    /**
     * Create Internship
     *
     * @param request
     * @return
     * @throws Exception 
     */
    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "/internship", method = RequestMethod.POST)
    public Internship createInternship(HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        return internshipService.createInternship(token);
    }

    /**
     * Delete Internship
     *
     * @param request
     * @throws Exception 
     */
    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "/internship", method = RequestMethod.DELETE)
    public void deleteInternship(HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        internshipService.deleteInternship(token);
    }
}
