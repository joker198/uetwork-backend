package uet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.DTO.FacultyDTO;
import uet.model.Faculty;
import uet.model.Role;
import uet.service.FacultyService;
import uet.stereotype.RequiredRoles;

import java.util.List;

/**
 * Created by nhkha on 14/05/2017.
 */
@RestController
public class FacultyController {
    private final
    FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    //get all faculty
    @RequestMapping(value = "faculty", method = RequestMethod.GET)
    public List<Faculty> getAllFaculty(){
        return facultyService.getAllFaculty();
    }

    //create faculty
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "faculty/create", method = RequestMethod.POST)
    public void createFaculty(@RequestBody FacultyDTO facultyDTO){
        facultyService.createFaculty(facultyDTO);
    }

    //edit faculty name
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "faculty/edit", method = RequestMethod.PUT)
    public void editFaculty(@RequestBody FacultyDTO facultyDTO){
        facultyService.editFaculty(facultyDTO);
    }

    //delete faculty
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "faculty/{facultyId}/delete", method = RequestMethod.PUT)
    public void editFaculty(@PathVariable("facultyId") int facultyId){
        facultyService.deleteFaculty(facultyId);
    }
}
