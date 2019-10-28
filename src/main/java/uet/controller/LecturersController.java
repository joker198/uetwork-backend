package uet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.DTO.LecturersDTO;
import uet.DTO.StudentDTO;
import uet.model.Lecturers;
import uet.model.Role;
import uet.service.LecturersService;
import uet.stereotype.RequiredRoles;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nhkha on 14/05/2017.
 */
@RestController
public class LecturersController {
    private final
    LecturersService lecturersService;

    @Autowired
    public LecturersController(LecturersService lecturersService) {
        this.lecturersService = lecturersService;
    }

    // edit info
    @RequiredRoles({Role.LECTURERS, Role.ADMIN})
    @RequestMapping(value = "lecturers/info/edit", method = RequestMethod.PUT)
    public void editInfoLecturers(@RequestBody LecturersDTO lecturersDTO, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        lecturersService.editInfoLecturers(lecturersDTO, token);
    }

    //show lecturers
    @RequestMapping(value = "lecturers/{lecturersId}", method = RequestMethod.GET)
    public Lecturers getInfoLecturers(@PathVariable("lecturersId") int lecturersId){
        return lecturersService.getInfoLecturers(lecturersId);
    }

    //edit lecturers student
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "edit/lecturers/student", method = RequestMethod.POST)
    public void editLecturersStudent(@RequestBody StudentDTO studentDTO, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        lecturersService.editLecturersStudent(studentDTO, token);
    }

    //change ava
    @RequiredRoles(Role.LECTURERS)
    @RequestMapping(value = "lecturers/avatar", method = RequestMethod.PUT)
    public void changeAva(@RequestBody LecturersDTO lecturersDTO, HttpServletRequest request) throws IOException {
        String token = request.getHeader("auth-token");
        lecturersService.changeAva(lecturersDTO, token);
    }

    //show all lecturers
    @RequestMapping(value = "lecturers", method = RequestMethod.GET)
    public List<Lecturers> getAllLecturers(){
        return lecturersService.getAllLecturers();
    }

    //show lecturers by faculty
    @RequestMapping(value = "lecturers/{facultyId}", method = RequestMethod.GET)
    public List<Lecturers> getLecturersByFaculty(@PathVariable("facultyId") int facultyId){
        return lecturersService.getLecturersByFaculty(facultyId);
    }

    // get lít lecturersId and name
    @RequestMapping(value = "lecturers/nameAndId", method = RequestMethod.GET)
    public List<HashMap<String, String>> getLecturersNameAndId(){
        return lecturersService.getLecturersNameAndId();
    }

    //delete lecturers
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "lecturers/delete/{lecturersId}", method = RequestMethod.DELETE)
    public void deleteLecturers(@PathVariable("lecturersId") int lecturersId){
        lecturersService.deleteLecturers(lecturersId);
    }

    //lecturers assignment
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "student/add/lecturers/{lecturersId}", method = RequestMethod.POST)
    public void addLecturersForStudent(@RequestBody List<StudentDTO> studentDTOs, HttpServletRequest request,
                                       @PathVariable("lecturersId") int lecturersId) throws IOException {
        String token = request.getHeader("auth-token");
        lecturersService.addLecturersForStudent(studentDTOs, lecturersId, token);
    }

    // check excel lecturers assignment
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "student/check/lecturers/excel", method = RequestMethod.POST)
    public List<StudentDTO> checkLecturersForStudentExcel(@RequestBody List<StudentDTO> studentDTOs, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return lecturersService.checkLecturersForStudentExcel(studentDTOs, token);
    }

    //lecturers assignment excel
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "lecturers/assignment/excel", method = RequestMethod.POST)
    public void LecturersAssignmentExcel(@RequestBody List<StudentDTO> studentDTOs, HttpServletRequest request) throws IOException {
        String token = request.getHeader("auth-token");
        lecturersService.LecturersAssignmentExcel(studentDTOs, token);
    }
    //remove lecturers-student
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "student{studentId}/remove/lecturers", method = RequestMethod.POST)
    public void removeLecturersOfStudent(HttpServletRequest request, @PathVariable("studentId") int studentId) throws Exception {
        String token = request.getHeader("auth-token");
        lecturersService.removeLecturersOfStudent(studentId, token);
    }

    //getLecturersNameOfStudent
//    @RequiredRoles({Role.STUDENT})
//    @RequestMapping(value = "student/lecturersName", method = RequestMethod.GET)
//    public Lecturers removeLecturersOfStudent(HttpServletRequest request) throws Exception {
//        String token = request.getHeader("auth-token");
//        return lecturersService.getLecturersNameOfStudent(token);
//    }
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "noti/lecturers/on", method = RequestMethod.POST)
    public void submitLecturers(HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        lecturersService.submitLecturers(token);
    }
}
