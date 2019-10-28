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

    //show all Internships
    @RequiredRoles({Role.ADMIN, Role.VIP_PARTNER})
    @RequestMapping(value = "/intern", method = RequestMethod.GET)
    public List<Internship> getAllInterns(HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        return internshipService.getAllIntern(token);
    }

    //show all Internship of a partner
    @RequiredRoles({Role.ADMIN, Role.VIP_PARTNER, Role.NORMAL_PARTNER})
    @RequestMapping(value = "/partner/{partnerId}/internship", method = RequestMethod.GET)
    public List<Internship> getAllInPartner(@PathVariable int partnerId, HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        return internshipService.getAllInPartner(partnerId, token);
    }

    //shơ all internship by internship term
//    @RequiredRoles({Role.ADMIN,Role.VIP_PARTNER,Role.LECTURERS})
//    @RequestMapping(value = "/intern/internshipTerm/{internshipTermId}")
//    public List<Internship> getAllInternshipByInternshipTerm(@PathVariable("internshipTermId") int internshipTermId){
//        return internshipService.getAllInternshipByInternshipTerm(internshipTermId);
//    }
//
//    //show all inernship of lecturers
//    @RequiredRoles({Role.ADMIN, Role.LECTURERS})
//    @RequestMapping(value = "internship/lecturers/{lecturersId}", method = RequestMethod.GET)
//    public List<Internship> getInternByLecturers(@PathVariable("lecturersId") int lecturersId,
//                                                 HttpServletRequest request){
//        String token= request.getHeader("auth-token");
//        return internshipService.getInternByLecturers(lecturersId, token);
//    }

    //Create a Internship
//    @RequiredRoles({Role.ADMIN})
//    @RequestMapping(value = "/intern/create", method = RequestMethod.POST)
//    public Internship createIntern(@RequestBody InternshipDTO internshipDTO, HttpServletRequest request) {
//        String token = request.getHeader("auth-token");
//        return internshipService.createIntern(internshipDTO, token);
//    }

    //create internship from excel
    @RequiredRoles({Role.ADMIN, Role.VIP_PARTNER})
    @RequestMapping(value = "/partner/createInternship", method = RequestMethod.PUT)
    public void createInternship(@RequestBody List<InternshipDTO> List, HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        internshipService.createMultiInternship(List, token);
    }

    //lecturers adds final score
    @RequiredRoles({Role.ADMIN, Role.LECTURERS})
    @RequestMapping(value = "/intern/{internId}/addscore", method = RequestMethod.PUT)
    public void addScore(@PathVariable("internId") int internId, @RequestBody float score, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        internshipService.addScore(internId, score, token);
    }


    //lecturers adds final score
    @RequiredRoles({Role.ADMIN, Role.LECTURERS})
    @RequestMapping(value = "/intern/addscore", method = RequestMethod.POST)
    public List<Integer> addMultiScore(@RequestBody List<InternshipDTO> internshipDTOS, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        return internshipService.addMultiScore(internshipDTOS, token);
    }

    //students adds final report
    @RequiredRoles({Role.STUDENT})
    @RequestMapping(value = "/intern/addFinalReport", method = RequestMethod.POST)
    public void addFinalReport(@RequestBody InternshipDTO internshipDTO, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        internshipService.addFinalReport(internshipDTO, token);
    }

    //find student by internId
//    @RequiredRoles({Role.ADMIN,Role.VIP_PARTNER,Role.STUDENT})
//    @RequestMapping(value = "/intern/{internId}", method = RequestMethod.GET)
//    public Internship findInternById(@PathVariable("internId") int id,HttpServletRequest request) {
//        String token = request.getHeader("auth-token");
//        return internshipService.findInternById(id,token);
//    }
    //Edit a internship
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "/intern/{internId}", method = RequestMethod.PUT)
    public Internship changeInternById(@PathVariable("internId") int id, @RequestBody InternshipDTO internshipDTO,
                                       HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        return internshipService.changeById(id, internshipDTO, token);
    }

    //Delete a internship
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "/intern/{internId}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable("internId") int id) {
//        String token = request.getHeader("auth-token");
        internshipService.deleteById(id);
    }

    //student pass interview, create link for join internship
//    @RequiredRoles(Role.VIP_PARTNER)
//    @RequestMapping(value = "student/{studentId}/passInterview", method = RequestMethod.POST)
//    public PassInterview passInterview(@PathVariable("studentId") int studentId, HttpServletRequest request){
//        String token = request.getHeader("auth-token");
//        return internshipService.passInterview(studentId, token);
//    }
//
//    //select internship confirmmationlink
//    @RequiredRoles(Role.STUDENT)
//    @RequestMapping(value = "comfirmationLink/{comfirmationLink}", method = RequestMethod.POST)
//    public Internship selectIntern(@PathVariable("comfirmationLink") String comfirmationLink){
//        return internshipService.selectIntern(comfirmationLink);
//    }

    //select internship partnerId
    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "select/intern/{followId}", method = RequestMethod.POST)
    public Internship selectInternPartnerId(@PathVariable("followId") int followId, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        return internshipService.selectInternPartnerId(followId, token);
    }

    //get comfirmation link of student
//    @RequiredRoles({Role.ADMIN, Role.STUDENT})
//    @RequestMapping(value = "student/{studentId}/comfirmationLink", method = RequestMethod.GET)
//    public List<PassInterview> getComfirmationLinkOfStudent(@PathVariable("studentId") int studentId,
//                                                            HttpServletRequest request){
//        String token = request.getHeader("auth-token");
//        return internshipService.getComfirmationLinkOfStudent(studentId, token);
//    }

    @RequiredRoles({Role.STUDENT})
    @RequestMapping(value = "/student/internship", method = RequestMethod.GET)
    public List<Internship> getAllInternshipOfStudent(HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        return internshipService.getAllInternshipOfStudent(token);
    }

    //    AddScoreByExcel
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "/score/excel/add", method = RequestMethod.POST)
    public List<StudentDTO> addScoreByExcel(HttpServletRequest request, @RequestBody List<StudentDTO> studentDTO) throws Exception {
        String token = request.getHeader("auth-token");
        return internshipService.AddScoreByExcel(studentDTO, token);
    }

    //    checkExcelAddScore
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "/score/excel/check", method = RequestMethod.POST)
    public List<StudentDTO> checkExcelAddScore(HttpServletRequest request, @RequestBody List<StudentDTO> studentDTO) throws Exception {
        String token = request.getHeader("auth-token");
        return internshipService.checkExcelAddScore(studentDTO);
    }
    //add lecturers for student
//    @RequiredRoles({Role.ADMIN})
//    @RequestMapping(value = "internship/add/lecturers/{lecturersId}/intern/{internId}", method = RequestMethod.POST)
//    public void addLecturersForInternship(@PathVariable("internId") int internId, @PathVariable("lecturersId") int lecturersId){
//        internshipService.addLecturersForInternship(internId, lecturersId);
//    }

    //submit score
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "noti/score/on", method = RequestMethod.POST)
    public void submitScore(HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        internshipService.submitScore(token);
    }

//    @RequiredRoles(Role.ADMIN)
//    @RequestMapping(value = "data/update", method = RequestMethod.POST)
//    public void updateData() throws Exception {
//        internshipService.updateData();
//    }

    //get curent internship of internship term
    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "/internship", method = RequestMethod.GET)
    public Internship getCurrentInternshipOfInternshipTerm(HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        return internshipService.getCurrentInternshipOfInternshipTerm(token);

    }

    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "/internship", method = RequestMethod.POST)
    public Internship createInternship(HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        return internshipService.createInternship(token);
    }

    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "/internship", method = RequestMethod.DELETE)
    public void deleteInternship(HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        internshipService.deleteInternship(token);
    }
}
