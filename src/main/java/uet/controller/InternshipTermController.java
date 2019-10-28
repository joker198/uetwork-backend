package uet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.DTO.InternshipTermDTO;
import uet.model.InternshipTerm;
import uet.model.Role;
import uet.service.InternshipTermService;
import uet.stereotype.RequiredRoles;

import java.util.List;

/**
 * Created by nhkha on 14/05/2017.
 */
@RestController
public class InternshipTermController {
    private final
    InternshipTermService internshipTermService;

    @Autowired
    public InternshipTermController(InternshipTermService internshipTermService) {
        this.internshipTermService = internshipTermService;
    }

    //create internship term
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "internshipTerm/create", method = RequestMethod.POST)
    public void createInternShipTerm(@RequestBody InternshipTermDTO internshipTermDTO){
        internshipTermService.createInternshipTerm(internshipTermDTO);
    }

    //delete internship term
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "internshipTerm/{internshipTermId}/delete", method = RequestMethod.DELETE)
    public void deleteInternshipTerm(@PathVariable("internshipTermId") int internshipTermId){
        internshipTermService.deleteInternshipTerm(internshipTermId);
    }

    //edit internship term
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "internshipTerm/edit", method = RequestMethod.PUT)
    public void editInternshipTerm(@RequestBody InternshipTermDTO internshipTermDTO){
        internshipTermService.editInternshipTerm(internshipTermDTO);
    }

    //get all internshipTerm
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "internshipTerm", method = RequestMethod.GET)
    public List<InternshipTerm> getAllInternshipTerm(){
        return internshipTermService.getAllInternshipTerm();
    }

    //get one internship term
//    @RequiredRoles(Role.ADMIN)
//    @RequestMapping(value = "internshipTerm/{internshipTermId}", method = RequestMethod.GET)
//    public List<InternshipTerm> getInternshipTerm(@PathVariable("internshipTermId") int internshipTermId){
//        return internshipTermService.getInternshipTerm(internshipTermId);
//    }
}
