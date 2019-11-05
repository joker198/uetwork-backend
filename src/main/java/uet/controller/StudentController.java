package uet.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uet.DTO.InfoBySchoolDTO;
import uet.DTO.PartnerDTO;
import uet.DTO.PostDTO;
import uet.DTO.StudentDTO;
import uet.model.*;
import uet.service.StudentService;
import uet.stereotype.RequiredRoles;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Trung on 7/8/2016.
 */
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    /**
     * Show All Student Info By School
     *
     * @param request
     * @return 
     */
    @RequiredRoles({Role.VIP_PARTNER,Role.ADMIN})
    @RequestMapping(value="/student", method = RequestMethod.GET)
    public List<HashMap<String, String>> getAllInfo(HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return (List<HashMap<String, String>>) studentService.getAllInfo(token);
    }

    /**
     * Show All Student
     *
     * @param pageable
     * @return 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "student/all", method = RequestMethod.GET)
    public Page<Student> getAllStudent(Pageable pageable){
        return studentService.getAllStudent(pageable);
    }

    //get student bu internship term
    @RequestMapping(value = "student/internshipTerm/{internshipTermId}", method = RequestMethod.GET)
    public List<Internship> getStudentByInternshipTerm(@PathVariable("internshipTermId") int internshipTermId){
        return studentService.getStudentByInternshipTerm(internshipTermId);
    }

    /**
     * Get Student By LecterersId And Last Internship Term
     *
     * @param httpServletRequest
     * @return
     * @throws Exception 
     */
    @RequiredRoles({Role.ADMIN, Role.LECTURERS})
    @RequestMapping(value = "lecturers/student", method = RequestMethod.GET)
    public List<Internship> getStudentByLecturersId(HttpServletRequest httpServletRequest) throws Exception {
        String token = httpServletRequest.getHeader("auth-token");
        return studentService.getStudentByLecturersId(token);
    }

    /**
     * Delete Student
     * 
     * @param studentId 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "student/delete/{studentId}", method = RequestMethod.DELETE)
    public void deleteStudent(@PathVariable("studentId") int studentId){
        studentService.deleteStudent(studentId);
    }

    /**
     * Show A Student
     *
     * @param studentId
     * @param request
     * @return 
     */
    @RequiredRoles({Role.STUDENT,Role.VIP_PARTNER,Role.ADMIN})
    @RequestMapping(value="student/{studentId}",method = RequestMethod.GET)
    public Student findStudent(@PathVariable("studentId") int studentId, HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        return studentService.findStudent(studentId, token);
    }

    /**
     * Get Student By Student Code
     *
     * @param infoBySchoolDTO
     * @return 
     */
    @RequestMapping(value = "student/studentCode", method = RequestMethod.POST)
    public List<InfoBySchool> findStudentByStudentCode(@RequestBody InfoBySchoolDTO infoBySchoolDTO){
        return studentService.findStudentByStudentCode(infoBySchoolDTO);
    }

    /**
     * Search partner
     *
     * @param partnerDTO
     * @return 
     */
    @RequiredRoles({Role.STUDENT, Role.ADMIN})
    @RequestMapping(value="searchPartner", method = RequestMethod.POST)
    public List<Partner> searchPartner(@RequestBody PartnerDTO partnerDTO){
        return studentService.searchPartner(partnerDTO);
    }

    /**
     * Search Post Description
     *
     * @param postDTO
     * @return 
     */
    @RequiredRoles({Role.STUDENT, Role.ADMIN})
    @RequestMapping(value="searchDescription", method = RequestMethod.POST)
    public List<Post> searchDescription(@RequestBody PostDTO postDTO){
        return studentService.searchDescription(postDTO);
    }

    /**
     * Search Post By Content
     *
     * @param postDTO
     * @return 
     */
    @RequiredRoles({Role.STUDENT, Role.ADMIN})
    @RequestMapping(value="searchContent", method = RequestMethod.POST)
    public List<Post> searchContent(@RequestBody PostDTO postDTO){
        return studentService.searchContent(postDTO);
    }

    /**
     * Show Info Of A Student
     *
     * @param request
     * @return 
     */
    @RequiredRoles({Role.ADMIN,Role.STUDENT,Role.VIP_PARTNER})
    @RequestMapping(value = "/studentInfo",method = RequestMethod.GET)
    public Student getStudentInfo(HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return studentService.getStudentInfo(token);
    }

    /**
     * Get Student Info
     *
     * @param userId
     * @return
     * @throws Exception 
     */
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "/student/user/{userId}",method = RequestMethod.GET)
    public StudentDTO getStudentInformationByUserId(@PathVariable("userId") int userId) throws Exception {
        return studentService.getStudentInformationByUserId(userId);
    }

    /**
     * Edit Student Info
     *
     * @param studentDTO
     * @param request
     * @return
     * @throws IOException 
     */
    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "/studentInfo",method = RequestMethod.PUT)
    public Student editStudentInfo(@RequestBody StudentDTO studentDTO, HttpServletRequest request) throws IOException {
        String token = request.getHeader("auth-token");
        return studentService.editStudentInfo(studentDTO,token);
    }

    /**
     * Change Avatar
     *
     * @param studentInfoDTO
     * @param request
     * @throws IOException 
     */
    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "changeAva", method = RequestMethod.PUT)
    public void changeAva(@RequestBody StudentDTO studentInfoDTO, HttpServletRequest request) throws IOException {
        String token = request.getHeader("auth-token");
        studentService.changeAva(studentInfoDTO, token);
    }
}
