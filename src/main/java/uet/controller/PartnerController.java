package uet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.DTO.AnnualActivityDTO;
import uet.DTO.PartnerContactDTO;
import uet.DTO.PartnerDTO;
import uet.DTO.StudentDTO;
import uet.model.*;
import uet.service.PartnerService;
import uet.stereotype.RequiredRoles;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tu on 28-Jun-16.
 */
@RestController
public class PartnerController {
    private final PartnerService partnerService;

    @Autowired
    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    /**
     * Get Fit Partner
     *
     * @return 
     */
    @RequestMapping(value = "partner/fit", method = RequestMethod.GET)
    public List<Partner> getFitPartner(){
        return partnerService.getFitPartner();
    }

    /**
     * Get Other Partner
     *
     * @return 
     */
    @RequestMapping(value = "partner/other", method = RequestMethod.GET)
    public List<Partner> getOtherPartner(){
        return partnerService.getOtherPartner();
    }

    /**
     * Show A Partner Info
     * 
     * @param partnerId
     * @return 
     */
    @RequiredRoles({Role.ADMIN, Role.STUDENT, Role.VIP_PARTNER})
    @RequestMapping(value = "/partner/{partnerId}", method = RequestMethod.GET)
    public Partner showPartner(@PathVariable("partnerId") int partnerId) {
        return partnerService.showPartner(partnerId);
    }

    /**
     * Find Partner By Contact Id
     *
     * @param partnerContactId
     * @return
     * @throws Exception 
     */
    @RequiredRoles({Role.ADMIN, Role.STUDENT, Role.VIP_PARTNER})
    @RequestMapping(value = "/partnerContact/get/partner/{partnerContactId}", method = RequestMethod.GET)
    public Partner findPartnerByContactId(@PathVariable("partnerContactId") int partnerContactId) throws Exception {
        return partnerService.findPartnerByContactId(partnerContactId);
    }

    /**
     * Find Partner By Post Id
     *
     * @param postId
     * @return
     * @throws Exception 
     */
    @RequiredRoles({Role.ADMIN, Role.STUDENT, Role.VIP_PARTNER})
    @RequestMapping(value = "/post/get/partner/{postId}", method = RequestMethod.GET)
    public Partner findPartnerByPostId(@PathVariable("postId") int postId) throws Exception {
        return partnerService.findPartnerByPostId(postId);
    }

    /**
     * Get PartnerId And PartnerName
     *
     * @param string
     * @return 
     */
    @RequestMapping(value = "partner/nameAndId/user/{string}", method = RequestMethod.GET)
    public List<HashMap<String, String>> getPartnerIdAndPartnerName(@PathVariable("string") String string){
        return partnerService.getPartnerIdAndPartnerName(string);
    }

    /**
     * Get PartnerId Name And Status
     *
     * @return 
     */
    @RequestMapping(value = "partnerId/name", method = RequestMethod.GET)
    public List<PartnerDTO> getPartnerAndId(){
        return partnerService.getPartnerAndId();
    }

    /**
     * Get Name And Id Of Other Partner
     *
     * @return 
     */
    @RequestMapping(value = "partnerId/name/other", method = RequestMethod.GET)
    public List<PartnerDTO> getPartnerNameAndIdOfOtherPartner(){
        return partnerService.getPartnerNameAndIdOfOtherPartner();
    }

    /**
     * Get Name And Id Of Fit Partner
     *
     * @return 
     */
    @RequestMapping(value = "partnerId/name/fit", method = RequestMethod.GET)
    public List<PartnerDTO> getPartnerNameAndIdOfFitPartner(){
        return partnerService.getPartnerNameAndIdOfFitPartner();
    }

    //partner search students
    @RequiredRoles({Role.VIP_PARTNER, Role.ADMIN})
    @RequestMapping(value = "searchStudent", method = RequestMethod.POST)
    public List<Student> searchStudent(@RequestBody StudentDTO studentDTO) {
        return partnerService.searchStudent(studentDTO);
    }

    /**
     * Edit Info Of A Partner
     *
     * @param partnerInfoId
     * @param partnerDTO
     * @param request
     * @return 
     */
    @RequiredRoles({Role.VIP_PARTNER, Role.NORMAL_PARTNER, Role.ADMIN})
    @RequestMapping(value="partnerInfo/{partnerInfoId}", method = RequestMethod.PUT)
    public Partner editInfo(@PathVariable("partnerInfoId") int partnerInfoId, @RequestBody PartnerDTO partnerDTO,
                                HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return partnerService.editInfo(partnerInfoId, partnerDTO, token);
    }

    /**
     * Change Logo
     *
     * @param partnerDTO
     * @param request
     * @throws IOException 
     */
    @RequiredRoles({Role.VIP_PARTNER, Role.NORMAL_PARTNER})
    @RequestMapping(value="/changeLogo", method = RequestMethod.PUT)
    public void changeLogo(@RequestBody PartnerDTO partnerDTO,
                           HttpServletRequest request) throws IOException {
        String token = request.getHeader("auth-token");
        partnerService.changeLogo(partnerDTO, token);
    }

    /**
     * Get Partner VIP Logo
     *
     * @return 
     */
    @RequiredRoles({Role.ADMIN, Role.VIP_PARTNER, Role.STUDENT})
    @RequestMapping(value="partnerLogo", method = RequestMethod.GET)
    public List<HashMap<String, String>> getPartnerVipLogo(){
        return (List<HashMap<String, String>>) partnerService.getPartnerViplogo();
    }

    /**
     * Get All Wait Partner
     *
     * @return 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "partner/wait", method = RequestMethod.GET)
    public List<Partner> getAllWaitPartner(){
        return partnerService.getAllWaitPartner();
    }

    /**
     * Check Partner
     *
     * @param partnerDTO
     * @param request
     * @throws Exception 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "/checkPartner", method = RequestMethod.POST)
    public void checkPartner(@RequestBody List<PartnerDTO> partnerDTO, HttpServletRequest request) throws Exception{
        String token = request.getHeader("auth-token");
        partnerService.checkPartner(partnerDTO, token);
    }

    /**
     * Delete Partner
     *
     * @param partnerId 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "partner/delete/{partnerId}", method = RequestMethod.DELETE)
    public void deletePartner(@PathVariable("partnerId") int partnerId){
        partnerService.deletePartner(partnerId);
    }

    /**
     * Create Partner
     *
     * @param partnerDTO
     * @param request
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "partner/create", method = RequestMethod.POST)
    public Partner createPartner(@RequestBody PartnerDTO partnerDTO, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        return partnerService.createPartner(partnerDTO, token);
    }

    /**
     * Delete Partner
     *
     * @param partnerId
     * @param request 
     */
    @RequestMapping(value = "partner/{partnerId}/delete", method = RequestMethod.DELETE)
    public void deleteParner(@PathVariable("partnerId") int partnerId, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        partnerService.deletePartner(partnerId, token);
    }

    /**
     * Edit Partner Info And Nation
     *
     * @param partnerInfoDTO
     * @param request
     * @return 
     */
    @RequestMapping(value = "partner/edit", method = RequestMethod.PUT)
    public Partner editPartnerInfo(@RequestBody PartnerDTO partnerInfoDTO, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return partnerService.editPartnerInfo(partnerInfoDTO, token);
    }

    /**
     * Create Partner Contact
     *
     * @param partnerId
     * @param partnerContactDTO
     * @param request
     * @return 
     */
    @RequestMapping(value = "partner/{partnerId}/contact/create", method = RequestMethod.POST)
    public PartnerContact createPartnerContact(@PathVariable("partnerId") int partnerId,
                                               @RequestBody PartnerContactDTO partnerContactDTO,
                                               HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return partnerService.createPartnerContact(partnerId, partnerContactDTO, token);
    }

    /**
     * Edit Partner Contact
     *
     * @param partnerContactDTO
     * @param request 
     */
    @RequestMapping(value = "partner/contact/edit", method = RequestMethod.PUT)
    public void editPartnerContact(@RequestBody PartnerContactDTO partnerContactDTO, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        partnerService.editPartnerContact(partnerContactDTO, token);
    }

    /**
     * Delete Partner Contact
     *
     * @param contactId
     * @param request 
     */
    @RequestMapping(value = "partner/contact/{contactId}/delete", method = RequestMethod.DELETE)
    public void deletePartnerContact(@PathVariable("contactId") int contactId, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        partnerService.deletePartnerContact(contactId, token);
    }

    /**
     * Show All Partner
     *
     * @return 
     */
    @RequestMapping(value = "partner", method = RequestMethod.GET)
    public List<Partner> showAllPartner(){
        return partnerService.showAllPartner();
    }

    /**
     * Show All Contacts Of Partner
     *
     * @param partnerId
     * @return 
     */
    @RequestMapping(value = "partner/{partnerId}/contact", method = RequestMethod.GET)
    public List<PartnerContact> showAllPartnerContact(@PathVariable("partnerId") int partnerId){
        return partnerService.showAllPartnerContact(partnerId);
    }
}
