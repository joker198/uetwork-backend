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

    /**
     * Create Internship Term
     *
     * @param internshipTermDTO 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "internshipTerm/create", method = RequestMethod.POST)
    public void createInternShipTerm(@RequestBody InternshipTermDTO internshipTermDTO){
        internshipTermService.createInternshipTerm(internshipTermDTO);
    }

    /**
     * Delete Internship Term
     *
     * @param internshipTermId 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "internshipTerm/{internshipTermId}/delete", method = RequestMethod.DELETE)
    public void deleteInternshipTerm(@PathVariable("internshipTermId") int internshipTermId){
        internshipTermService.deleteInternshipTerm(internshipTermId);
    }

    /**
     * Edit Internship Term
     *
     * @param internshipTermDTO 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "internshipTerm/edit", method = RequestMethod.PUT)
    public void editInternshipTerm(@RequestBody InternshipTermDTO internshipTermDTO){
        internshipTermService.editInternshipTerm(internshipTermDTO);
    }

    /**
     * Get All Internship Term
     *
     * @return 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "internshipTerm", method = RequestMethod.GET)
    public List<InternshipTerm> getAllInternshipTerm(){
        return internshipTermService.getAllInternshipTerm();
    }
    
    /**
     * Get All Internship Term
     *
     * @return 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "/validTerms", method = RequestMethod.GET)
    public List<InternshipTerm> getValidTerms(){
        return internshipTermService.getValidTerms();
    }
}
