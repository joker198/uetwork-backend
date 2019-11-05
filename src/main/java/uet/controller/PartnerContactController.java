package uet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.DTO.PartnerContactDTO;
import uet.model.PartnerContact;
import uet.model.Role;
import uet.service.PartnerContactService;
import uet.stereotype.RequiredRoles;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by fgv on 9/2/2016.
 */
@RestController
public class PartnerContactController {
    @Autowired
    private PartnerContactService partnerContactService;

    /**
     * Show All Partner Contact
     *
     * @return 
     */
    @RequiredRoles({Role.STUDENT,Role.ADMIN,Role.VIP_PARTNER})
    @RequestMapping(value="/partnerContact",method = RequestMethod.GET)
    public List<PartnerContact> getallPartnerContact(){
        return partnerContactService.getpartnerContacts();
    }

    /**
     * Show List Contact Of A Partner
     *
     * @param partnerId
     * @return 
     */
    @RequiredRoles({Role.ADMIN,Role.VIP_PARTNER,Role.STUDENT, Role.NORMAL_PARTNER})
    @RequestMapping(value="/partner/{partnerId}/partnerContact",method = RequestMethod.GET)
    public List<PartnerContact> showAllContact(@PathVariable("partnerId") int partnerId){
        return partnerContactService.showAllContact(partnerId);
    }
    
    /**
     * Show A Partner Contact
     *
     * @param partnerCtId
     * @return 
     */
    @RequiredRoles({Role.ADMIN, Role.VIP_PARTNER, Role.STUDENT, Role.NORMAL_PARTNER})
    @RequestMapping(value="/partnerContact/{partnerContactId}", method = RequestMethod.GET)
    public PartnerContact showCont(@PathVariable("partnerContactId") int partnerCtId){
        return partnerContactService.showContact(partnerCtId);
    }
    
    /**
     * Create A Partner Contact
     *
     * @param partnerId
     * @param partnerContactDTO
     * @param request
     * @return 
     */
    @RequiredRoles({Role.VIP_PARTNER, Role.NORMAL_PARTNER, Role.ADMIN})
    @RequestMapping(value="/partner/{partnerId}/partnerContact", method = RequestMethod.POST)
    public PartnerContact createcontact(@PathVariable("partnerId") int partnerId, @RequestBody PartnerContactDTO partnerContactDTO, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return partnerContactService.createContact(partnerId, partnerContactDTO, token);
    }

    /**
     * Edit Contact Of A Partner
     *
     * @param partnerContactId
     * @param partnerContactDTO
     * @param request
     * @return 
     */
    @RequiredRoles({Role.VIP_PARTNER, Role.NORMAL_PARTNER, Role.ADMIN})
    @RequestMapping(value="/partnerContact/{partnerContactId}", method = RequestMethod.PUT)
    public PartnerContact editcontact(@PathVariable("partnerContactId") int partnerContactId, @RequestBody PartnerContactDTO partnerContactDTO, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return partnerContactService.editContact(partnerContactId, partnerContactDTO, token);
    }

    /**
     * Delete Contact Of A Partner
     *
     * @param partnerCtId
     * @param request 
     */
    @RequiredRoles({Role.VIP_PARTNER, Role.NORMAL_PARTNER,Role.ADMIN})
    @RequestMapping(value="/partnerContact/{partnerContactId}", method = RequestMethod.DELETE)
    public void deleteCont(@PathVariable("partnerContactId") int partnerCtId, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        partnerContactService.deleteContact(partnerCtId, token);
    }
}
