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
    //Show all student information by school
    @RequiredRoles({Role.VIP_PARTNER,Role.ADMIN})
    @RequestMapping(value="/student", method = RequestMethod.GET)
    public List<HashMap<String, String>> getAllInfo(HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return (List<HashMap<String, String>>) studentService.getAllInfo(token);
    }

    //show all student
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "student/all", method = RequestMethod.GET)
    public Page<Student> getAllStudent(Pageable pageable){
        return studentService.getAllStudent(pageable);
    }

    //show all student no lectutrers
//    @RequiredRoles(Role.ADMIN)
//    @RequestMapping(value = "student/no/lectutrers", method = RequestMethod.GET)
//    public List<Student> getAllStudentNoLecturers(){
//        return studentService.getAllStudentNoLecturers();
//    }

    //get student bu internship term
    @RequestMapping(value = "student/internshipTerm/{internshipTermId}", method = RequestMethod.GET)
    public List<Internship> getStudentByInternshipTerm(@PathVariable("internshipTermId") int internshipTermId){
        return studentService.getStudentByInternshipTerm(internshipTermId);
    }

    //get student by lecterersId and last internship term
    @RequiredRoles({Role.ADMIN, Role.LECTURERS})
    @RequestMapping(value = "lecturers/student", method = RequestMethod.GET)
    public List<Internship> getStudentByLecturersId(HttpServletRequest httpServletRequest) throws Exception {
        String token = httpServletRequest.getHeader("auth-token");
        return studentService.getStudentByLecturersId(token);
    }

    //get student by internship term no lecturers
//    @RequestMapping(value = "student/internshipTerm/no/lecturers", method = RequestMethod.GET)
//    public List<Student> getAllStudentByInternshipTermAndNOLecturers(){
//        return studentService.getAllStudentByInternshipTermAndNOLecturers();
//    }

    //getCountStudentNoFollow
//    @RequestMapping(value = "getCountStudentNoFollow", method = RequestMethod.GET)
//    public StudentDTO getCountStudentNoFollow(){
//        return studentService.getCountStudentNoFollow();
//    }

    //get student no internship term
//    @RequestMapping(value = "student/no/internshipTerm", method = RequestMethod.GET)
//    public List<Student> getStudentNoInternshipTerm(){
//        return studentService.getStudentNoInternshipTerm();
//    }

    //delete student
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "student/delete/{studentId}", method = RequestMethod.DELETE)
    public void deleteStudent(@PathVariable("studentId") int studentId){
        studentService.deleteStudent(studentId);
    }
//    //Create
//    @RequiredRoles({Role.STUDENT,Role.ADMIN})
//    @RequestMapping(value="/user/{userId}/student", method = RequestMethod.POST)
//    public Student createStudent(@PathVariable("userId") int userId, @RequestBody StudentDTO studentDTO){
//        return studentService.createStudent(userId, studentDTO);
//    }

    //Show a student
    @RequiredRoles({Role.STUDENT,Role.VIP_PARTNER,Role.ADMIN})
    @RequestMapping(value="student/{studentId}",method = RequestMethod.GET)
    public Student findStudent(@PathVariable("studentId") int studentId, HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        return studentService.findStudent(studentId, token);
    }

    //get student by student code
    @RequestMapping(value = "student/studentCode", method = RequestMethod.POST)
    public List<InfoBySchool> findStudentByStudentCode(@RequestBody InfoBySchoolDTO infoBySchoolDTO){
        return studentService.findStudentByStudentCode(infoBySchoolDTO);
    }

//    @RequiredRoles(Role.ADMIN)
//    @RequestMapping(value = "internshipTerm/add/student", method = RequestMethod.PUT)
//    public void addAllStudentToInternshipTerm(){
//        studentService.addAllStudentToInternshipTerm();
//    }

    //Student search partner
    @RequiredRoles({Role.STUDENT, Role.ADMIN})
    @RequestMapping(value="searchPartner", method = RequestMethod.POST)
    public List<Partner> searchPartner(@RequestBody PartnerDTO partnerDTO){
        return studentService.searchPartner(partnerDTO);
    }

    //Student search post description
    @RequiredRoles({Role.STUDENT, Role.ADMIN})
    @RequestMapping(value="searchDescription", method = RequestMethod.POST)
    public List<Post> searchDescription(@RequestBody PostDTO postDTO){
        return studentService.searchDescription(postDTO);
    }

    //Student search post by content
    @RequiredRoles({Role.STUDENT, Role.ADMIN})
    @RequestMapping(value="searchContent", method = RequestMethod.POST)
    public List<Post> searchContent(@RequestBody PostDTO postDTO){
        return studentService.searchContent(postDTO);
    }

    //show info of a student
    @RequiredRoles({Role.ADMIN,Role.STUDENT,Role.VIP_PARTNER})
    @RequestMapping(value = "/studentInfo",method = RequestMethod.GET)
    public Student getStudentInfo(HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return studentService.getStudentInfo(token);
    }

    //get student info, infobyschool, internship by user id
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "/student/user/{userId}",method = RequestMethod.GET)
    public StudentDTO getStudentInformationByUserId(@PathVariable("userId") int userId) throws Exception {
        return studentService.getStudentInformationByUserId(userId);
    }

    //edit info of a student
    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "/studentInfo",method = RequestMethod.PUT)
    public Student editStudentInfo(@RequestBody StudentDTO studentDTO, HttpServletRequest request) throws IOException {
        String token = request.getHeader("auth-token");
        return studentService.editStudentInfo(studentDTO,token);
    }

    //change avatar
    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "changeAva", method = RequestMethod.PUT)
    public void changeAva(@RequestBody StudentDTO studentInfoDTO, HttpServletRequest request) throws IOException {
        String token = request.getHeader("auth-token");
        studentService.changeAva(studentInfoDTO, token);
    }

    //delete info of a student
//    @RequiredRoles({Role.STUDENT,Role.ADMIN})
//    @RequestMapping(value = "/studentInfo/{id}",method = RequestMethod.DELETE)
//    public void deleteStudentInfo(@PathVariable("id") int id, HttpServletRequest request){
//        String token = request.getHeader("auth-token");
//        studentService.deleteStudentInfo(id, token);
//    }
}
