package uet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.model.Role;
import uet.model.StudentClass;
import uet.service.StudentClassService;
import uet.stereotype.RequiredRoles;

import java.util.List;

/**
 * Created by nhkha on 6/16/2017.
 */
@RestController
public class StudentClassController {
    @Autowired
    private
    StudentClassService studentClassService;

    /**
     * Get All Student Class
     *
     * @return 
     */
    @RequestMapping(value = "studentClass", method = RequestMethod.GET)
    public List<StudentClass> getAllStudentClass() {
        return studentClassService.getAllStudentClass();
    }

    /**
     * Create Student Class
     *
     * @param studentClass
     * @throws Exception 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "studentClass/create", method = RequestMethod.POST)
    public void createStudentClass(@RequestBody StudentClass studentClass) throws Exception {
        studentClassService.createStudentClass(studentClass);
    }

    /**
     * Edit Student Class
     *
     * @param studentClass
     * @throws Exception 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "studentClass/edit", method = RequestMethod.POST)
    public void editStudentClass(@RequestBody StudentClass studentClass) throws Exception {
        studentClassService.editStudentClass(studentClass);
    }

    /**
     * Delete Student Class
     *
     * @param studentClassId
     * @throws Exception 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "studentClass/{studentClassId}/delete", method = RequestMethod.DELETE)
    public void editStudentClass(@PathVariable("studentClassId") int studentClassId) throws Exception {
        studentClassService.deleteStudentClass(studentClassId);
    }
}
