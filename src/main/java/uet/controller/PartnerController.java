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

    //show all list partner
//    @RequiredRoles({Role.STUDENT, Role.ADMIN, Role.VIP_PARTNER})
//    @RequestMapping(value = "/partner", method = RequestMethod.GET)
//    public List<Partner> getPartners() {
//        return partnerService.getPartners();
//    }

    //get fit partner
    @RequestMapping(value = "partner/fit", method = RequestMethod.GET)
    public List<Partner> getFitPartner(){
        return partnerService.getFitPartner();
    }

    //get other partner
    @RequestMapping(value = "partner/other", method = RequestMethod.GET)
    public List<Partner> getOtherPartner(){
        return partnerService.getOtherPartner();
    }

    //show a partner info
    @RequiredRoles({Role.ADMIN, Role.STUDENT, Role.VIP_PARTNER})
    @RequestMapping(value = "/partner/{partnerId}", method = RequestMethod.GET)
    public Partner showPartner(@PathVariable("partnerId") int partnerId) {
        return partnerService.showPartner(partnerId);
    }

    //find partner by contact id
    @RequiredRoles({Role.ADMIN, Role.STUDENT, Role.VIP_PARTNER})
    @RequestMapping(value = "/partnerContact/get/partner/{partnerContactId}", method = RequestMethod.GET)
    public Partner findPartnerByContactId(@PathVariable("partnerContactId") int partnerContactId) throws Exception {
        return partnerService.findPartnerByContactId(partnerContactId);
    }

    //find partner by post id
    @RequiredRoles({Role.ADMIN, Role.STUDENT, Role.VIP_PARTNER})
    @RequestMapping(value = "/post/get/partner/{postId}", method = RequestMethod.GET)
    public Partner findPartnerByPostId(@PathVariable("postId") int postId) throws Exception {
        return partnerService.findPartnerByPostId(postId);
    }

    //get partnerId and partnerName
    @RequestMapping(value = "partner/nameAndId/user/{string}", method = RequestMethod.GET)
    public List<HashMap<String, String>> getPartnerIdAndPartnerName(@PathVariable("string") String string){
        return partnerService.getPartnerIdAndPartnerName(string);
    }

    //get partnerId name and status
    @RequestMapping(value = "partnerId/name", method = RequestMethod.GET)
    public List<PartnerDTO> getPartnerAndId(){
        return partnerService.getPartnerAndId();
    }

    @RequestMapping(value = "partnerId/name/other", method = RequestMethod.GET)
    public List<PartnerDTO> getPartnerNameAndIdOfOtherPartner(){
        return partnerService.getPartnerNameAndIdOfOtherPartner();
    }

    @RequestMapping(value = "partnerId/name/fit", method = RequestMethod.GET)
    public List<PartnerDTO> getPartnerNameAndIdOfFitPartner(){
        return partnerService.getPartnerNameAndIdOfFitPartner();
    }

    //get partner and id
//    @RequestMapping(value = "partner/id", method = RequestMethod.GET)
//    public List<PartnerDTO> getPartnerAndId(){
//        return partnerService.getPartnerAndId();
//    }

    //partner search students
    @RequiredRoles({Role.VIP_PARTNER, Role.ADMIN})
    @RequestMapping(value = "searchStudent", method = RequestMethod.POST)
    public List<Student> searchStudent(@RequestBody StudentDTO studentDTO) {
        return partnerService.searchStudent(studentDTO);
    }

    //edit info of a partner
    @RequiredRoles({Role.VIP_PARTNER, Role.NORMAL_PARTNER, Role.ADMIN})
    @RequestMapping(value="partnerInfo/{partnerInfoId}", method = RequestMethod.PUT)
    public Partner editInfo(@PathVariable("partnerInfoId") int partnerInfoId, @RequestBody PartnerDTO partnerDTO,
                                HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return partnerService.editInfo(partnerInfoId, partnerDTO, token);
    }

    //change logo
    @RequiredRoles({Role.VIP_PARTNER, Role.NORMAL_PARTNER})
    @RequestMapping(value="/changeLogo", method = RequestMethod.PUT)
    public void changeLogo(@RequestBody PartnerDTO partnerDTO,
                           HttpServletRequest request) throws IOException {
        String token = request.getHeader("auth-token");
        partnerService.changeLogo(partnerDTO, token);
    }

    //get partner vip logo
    @RequiredRoles({Role.ADMIN, Role.VIP_PARTNER, Role.STUDENT})
    @RequestMapping(value="partnerLogo", method = RequestMethod.GET)
    public List<HashMap<String, String>> getPartnerVipLogo(){
        return (List<HashMap<String, String>>) partnerService.getPartnerViplogo();
    }

    //get all wait partner
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "partner/wait", method = RequestMethod.GET)
    public List<Partner> getAllWaitPartner(){
        return partnerService.getAllWaitPartner();
    }

    //checkPartner
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "/checkPartner", method = RequestMethod.POST)
    public void checkPartner(@RequestBody List<PartnerDTO> partnerDTO, HttpServletRequest request) throws Exception{
        String token = request.getHeader("auth-token");
        partnerService.checkPartner(partnerDTO, token);
    }

    //deletePartner
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "partner/delete/{partnerId}", method = RequestMethod.DELETE)
    public void deletePartner(@PathVariable("partnerId") int partnerId){
        partnerService.deletePartner(partnerId);
    }
    //get partner name and id
//    @RequestMapping(value = "partner/name/id", method = RequestMethod.GET)
//    public ListPartner

    //create partner
    @RequestMapping(value = "partner/create", method = RequestMethod.POST)
    public Partner createPartner(@RequestBody PartnerDTO partnerDTO, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        return partnerService.createPartner(partnerDTO, token);
    }

    //delete partner
    @RequestMapping(value = "partner/{partnerId}/delete", method = RequestMethod.DELETE)
    public void deleteParner(@PathVariable("partnerId") int partnerId, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        partnerService.deletePartner(partnerId, token);
    }

    //edit partner info and nation
    @RequestMapping(value = "partner/edit", method = RequestMethod.PUT)
    public Partner editPartnerInfo(@RequestBody PartnerDTO partnerInfoDTO, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return partnerService.editPartnerInfo(partnerInfoDTO, token);
    }

    //create partner contact
    @RequestMapping(value = "partner/{partnerId}/contact/create", method = RequestMethod.POST)
    public PartnerContact createPartnerContact(@PathVariable("partnerId") int partnerId,
                                               @RequestBody PartnerContactDTO partnerContactDTO,
                                               HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return partnerService.createPartnerContact(partnerId, partnerContactDTO, token);
    }

    //edit partner contact
    @RequestMapping(value = "partner/contact/edit", method = RequestMethod.PUT)
    public void editPartnerContact(@RequestBody PartnerContactDTO partnerContactDTO, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        partnerService.editPartnerContact(partnerContactDTO, token);
    }

    //delete partner contact
    @RequestMapping(value = "partner/contact/{contactId}/delete", method = RequestMethod.DELETE)
    public void deletePartnerContact(@PathVariable("contactId") int contactId, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        partnerService.deletePartnerContact(contactId, token);
    }

    //show all partner
    @RequestMapping(value = "partner", method = RequestMethod.GET)
    public List<Partner> showAllPartner(){
        return partnerService.showAllPartner();
    }

    //show one partner
//    @RequestMapping(value = "partner/{partnerId}", method = RequestMethod.GET)
//    public Partner showAllPartner(@PathVariable("partnerId") int partnerId){
//        return partnerService.showPartner(partnerId);
//    }

    //show all contacts of partner
    @RequestMapping(value = "partner/{partnerId}/contact", method = RequestMethod.GET)
    public List<PartnerContact> showAllPartnerContact(@PathVariable("partnerId") int partnerId){
        return partnerService.showAllPartnerContact(partnerId);
    }



}
