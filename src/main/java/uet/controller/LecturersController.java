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

    /**
     * Edit Info
     *
     * @param lecturersDTO
     * @param request 
     */
    @RequiredRoles({Role.LECTURERS, Role.ADMIN})
    @RequestMapping(value = "lecturers/info/edit", method = RequestMethod.PUT)
    public void editInfoLecturers(@RequestBody LecturersDTO lecturersDTO, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        lecturersService.editInfoLecturers(lecturersDTO, token);
    }

    /**
     * Show Lecturers
     *
     * @param lecturersId
     * @return 
     */
    @RequestMapping(value = "lecturers/{lecturersId}", method = RequestMethod.GET)
    public Lecturers getInfoLecturers(@PathVariable("lecturersId") int lecturersId){
        return lecturersService.getInfoLecturers(lecturersId);
    }

    /**
     * Edit Lecturers Student
     *
     * @param studentDTO
     * @param request
     * @throws Exception 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "edit/lecturers/student", method = RequestMethod.POST)
    public void editLecturersStudent(@RequestBody StudentDTO studentDTO, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        lecturersService.editLecturersStudent(studentDTO, token);
    }

    /**
     * Change Ava
     *
     * @param lecturersDTO
     * @param request
     * @throws IOException 
     */
    @RequiredRoles(Role.LECTURERS)
    @RequestMapping(value = "lecturers/avatar", method = RequestMethod.PUT)
    public void changeAva(@RequestBody LecturersDTO lecturersDTO, HttpServletRequest request) throws IOException {
        String token = request.getHeader("auth-token");
        lecturersService.changeAva(lecturersDTO, token);
    }

    /**
     * Show All Lecturers
     *
     * @return 
     */
    @RequestMapping(value = "lecturers", method = RequestMethod.GET)
    public List<Lecturers> getAllLecturers(){
        return lecturersService.getAllLecturers();
    }

    /**
     * Show Lecturers By Faculty
     *
     * @param facultyId
     * @return 
     */
    @RequestMapping(value = "lecturers/{facultyId}", method = RequestMethod.GET)
    public List<Lecturers> getLecturersByFaculty(@PathVariable("facultyId") int facultyId){
        return lecturersService.getLecturersByFaculty(facultyId);
    }

    /**
     * get list lecturersId and name
     *
     * @return 
     */
    @RequestMapping(value = "lecturers/nameAndId", method = RequestMethod.GET)
    public List<HashMap<String, String>> getLecturersNameAndId(){
        return lecturersService.getLecturersNameAndId();
    }

    /**
     * Delete Lecturers
     *
     * @param lecturersId 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "lecturers/delete/{lecturersId}", method = RequestMethod.DELETE)
    public void deleteLecturers(@PathVariable("lecturersId") int lecturersId){
        lecturersService.deleteLecturers(lecturersId);
    }

    /**
     * Lecturers Assignment
     *
     * @param studentDTOs
     * @param request
     * @param lecturersId
     * @throws IOException 
     */
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "student/add/lecturers/{lecturersId}", method = RequestMethod.POST)
    public void addLecturersForStudent(@RequestBody List<StudentDTO> studentDTOs, HttpServletRequest request,
                                       @PathVariable("lecturersId") int lecturersId) throws IOException {
        String token = request.getHeader("auth-token");
        lecturersService.addLecturersForStudent(studentDTOs, lecturersId, token);
    }

    /**
     * Check Excel Lecturers Assignment
     *
     * @param studentDTOs
     * @param request
     * @return 
     */
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "student/check/lecturers/excel", method = RequestMethod.POST)
    public List<StudentDTO> checkLecturersForStudentExcel(@RequestBody List<StudentDTO> studentDTOs, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return lecturersService.checkLecturersForStudentExcel(studentDTOs, token);
    }

    /**
     * Lecturers Assignment Excel
     *
     * @param studentDTOs
     * @param request
     * @throws IOException 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "lecturers/assignment/excel", method = RequestMethod.POST)
    public void LecturersAssignmentExcel(@RequestBody List<StudentDTO> studentDTOs, HttpServletRequest request) throws IOException {
        String token = request.getHeader("auth-token");
        lecturersService.LecturersAssignmentExcel(studentDTOs, token);
    }

    /**
     * Remove Lecturers-Student
     *
     * @param request
     * @param studentId
     * @throws Exception 
     */
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "student{studentId}/remove/lecturers", method = RequestMethod.POST)
    public void removeLecturersOfStudent(HttpServletRequest request, @PathVariable("studentId") int studentId) throws Exception {
        String token = request.getHeader("auth-token");
        lecturersService.removeLecturersOfStudent(studentId, token);
    }

    /**
     * Submit Lecturers
     *
     * @param request
     * @throws Exception 
     */
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "noti/lecturers/on", method = RequestMethod.POST)
    public void submitLecturers(HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        lecturersService.submitLecturers(token);
    }
}
