package uet.controller;

import uet.DTO.*;
import uet.model.AnnualActivity;
import uet.model.Contract;
import uet.model.ContractShare;
import uet.model.Role;
import uet.service.ContractService;
//import uet.stereotype.RequiedToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.stereotype.RequiredRoles;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by nhkha on 27/03/2017.
 */
@RestController
public class ContractController {
    private final
    ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    //create  contract
    @RequestMapping(value = "contract/create", method = RequestMethod.POST)
    public void createContract(@RequestBody ContractDTO contractDTO, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        contractService.createContract(contractDTO, token);
    }

    @RequiredRoles({Role.ADMIN_UNIT, Role.UNIT,Role.ADMIN_VNU})
    @RequestMapping(value = "contract/{contractId}/renew", method = RequestMethod.POST)
    public void renewContract(@PathVariable("contractId") int contractId,@RequestBody ContractDTO contractDTO,
                              HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        contractService.renewContract(contractDTO, contractId, token);
    }

    //edit contract
    @RequiredRoles({Role.ADMIN_VNU, Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "contract/{contractId}/edit", method = RequestMethod.POST)
    public void editContract(@PathVariable("contractId") int contractId, @RequestBody ContractDTO contractDTO,
                             HttpServletRequest request) throws IOException {
        String token = request.getHeader("auth-token");
        contractService.editContract(contractDTO, contractId, token);
    }

    //delete contract
    @RequestMapping(value = "contract/{contractId}/delete", method = RequestMethod.DELETE)
    public void deleteContract(@PathVariable("contractId") int contractId, HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        contractService.deleteContract(contractId, token);
    }

    //show all contract
//    @RequiedToken
    @RequiredRoles({Role.ADMIN_VNU, Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "contract", method = RequestMethod.GET)
    public List<Contract> getAllContract() {
        return contractService.getAllContract();
    }

    @RequiredRoles({Role.ADMIN_VNU, Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "contract/RoleAndSigningLevel", method = RequestMethod.GET)
    public List<Contract> getContractByRoleAndSigningLevel(HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        return contractService.getContractByRoleAndSigningLevel(token);
    }

    //check contract
    @RequestMapping(value = "checkContract", method = RequestMethod.POST)
    public CheckContractDTO checkContract(@RequestBody CheckContractDTO checkContractDTO) {
        return contractService.checkContract(checkContractDTO);
    }

    //import excel
    @RequestMapping(value = "contract/excel", method = RequestMethod.POST)
    public List<ExcelContractDTO> importExcel(@RequestBody Set<ExcelContractDTO> list, HttpServletRequest request) throws IOException {
        String token = request.getHeader("auth-token");
        return contractService.importExcel(list, token);
    }

    //edit cooperate activity
    @RequestMapping(value = "cooperateActivity/edit", method = RequestMethod.PUT)
    public void editCooperateActivity(@RequestBody CooperateActivityDTO cooperateActivityDTO, HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        contractService.editCooperateActivity(cooperateActivityDTO, token);
    }

    //delete cooperate activity
    @RequestMapping(value = "cooperateActivity/{cooperateActivityId}/delete", method = RequestMethod.DELETE)
    public void deleteCooperateActivity(@PathVariable("cooperateActivityId") int cooperateActivityId,
                                        HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        contractService.deleteCooperateActivity(cooperateActivityId, token);
    }

    //add cooperate activity
    @RequestMapping(value = "contract/{contractId}/cooperateActivity/add", method = RequestMethod.POST)
    public void addCooperateActivity(@RequestBody CooperateActivityDTO cooperateActivityDTO,
                                     @PathVariable("contractId") int contractId,
                                     HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        contractService.addCooperateActivity(cooperateActivityDTO, contractId, token);
    }

    //get all contract of unit
    @RequestMapping(value = "unit/contract", method = RequestMethod.GET)
    public List<Contract> getContractOfUnit(HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        return contractService.getContractOfUnit(token);
    }

    //get all contract ò partner
    @RequiredRoles(Role.ADMIN_UNIT)
    @RequestMapping(value = "partner/{partnerId}/contract", method = RequestMethod.GET)
    public List<Contract> getAllContractOfPartner(@PathVariable("partnerId") int partnerId){
        return contractService.getAllContractOfPartner(partnerId);
    }

    @RequiredRoles({Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "contract/share/create", method = RequestMethod.POST)
    public void shareContract (@RequestBody List<ContractDTO> contractDTOList){
        contractService.shareContract(contractDTOList);
    }

    @RequiredRoles({Role.ADMIN_UNIT, Role.ADMIN_VNU})
    @RequestMapping(value = "contract/vnu/share/create", method = RequestMethod.POST)
    public void shareContractVnu (@RequestBody List<ContractDTO> contractDTOList){
        contractService.shareContractVnu(contractDTOList);
    }

    @RequiredRoles({Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "contract/share/delete", method = RequestMethod.POST)
    public void deleteContractShare (@RequestBody List<Integer> listId){
        contractService.deleteContractShare(listId);
    }

    @RequiredRoles({Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "contract/share/unit/{unitNameId}", method = RequestMethod.GET)
    public List<ContractShare> getAllContractShareOfUnit (@PathVariable("unitNameId") int unitNameId){
        return contractService.getAllContractShareOfUnit(unitNameId);
    }

    @RequiredRoles({Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "contract/share/contract/{contractId}", method = RequestMethod.GET)
    public List<ContractShare> getAllContractShareOfContract (@PathVariable("contractId") int contractId){
        return contractService.getAllContractShareOfContract(contractId);
    }

    //ađ annual activity for partner
//    @RequiredRoles(Role.ADMIN_UNIT)
//    @RequestMapping(value = "partner/annualActivity/add", method = RequestMethod.POST)
//    public AnnualActivity addAnnualActivity(@RequestBody AnnualActivityDTO annualActivityDTO) throws Exception {
//        return contractService.addAnnualActivity(annualActivityDTO);
//    }

    //edit annual activity
    @RequiredRoles(Role.ADMIN_UNIT)
    @RequestMapping(value = "partner/annualActivity/edit", method = RequestMethod.POST)
    public void editAnnualActivity(@RequestBody AnnualActivityDTO annualActivityDTO) throws Exception {
        contractService.editAnnualActivity(annualActivityDTO);
    }

    //delete annual activity  deleteAnnaulActivity
    @RequiredRoles(Role.ADMIN_UNIT)
    @RequestMapping(value = "partner/annualActivity/delete", method = RequestMethod.DELETE)
    public void deleteAnnaulActivity(@RequestBody int annualActivityId) throws Exception {
        contractService.deleteAnnaulActivity(annualActivityId);
    }

    //getAllAnnualActivityOfPartner
//    @RequiredRoles(Role.ADMIN_UNIT)
//    @RequestMapping(value = "partner/annualActivity/{partnerId}", method = RequestMethod.GET)
//    public List<AnnualActivity> getAllAnnualActivityOfPartner(@PathVariable("partnerId") int partnerId) throws Exception {
//        return contractService.getAllAnnualActivityOfPartner(partnerId);
//    }

    //getAllAnnualActivityOfContract
//    @RequiredRoles(Role.ADMIN_UNIT)
//    @RequestMapping(value = "contract/annualActivity/{contractId}", method = RequestMethod.GET)
//    public List<AnnualActivity> getAllAnnualActivityOfContract(@PathVariable("contractId") int contractId) throws Exception {
//        return contractService.getAllAnnualActivityOfContract(contractId);
//    }

    //get all annualActivity
//    @RequiredRoles(Role.ADMIN_UNIT)
//    @RequestMapping(value = "annualActivity", method = RequestMethod.GET)
//    public List<AnnualActivity> getAllAnnualActivity() throws Exception {
//        return contractService.getAllAnnualActivity();
//    }

    @RequiredRoles({Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "cooperate/activity/detail/edit", method = RequestMethod.POST)
    public void getAllAnnualActivity(@RequestBody CooperateActivityDetailDTO cooperateActivityDetailDTO) throws Exception {
        contractService.editCooperateActivityDetail(cooperateActivityDetailDTO);
    }

    @RequiredRoles({Role.ADMIN_UNIT, Role.UNIT,Role.ADMIN_VNU})
    @RequestMapping(value = "contract/{contractId}/remove/renew", method = RequestMethod.POST)
    public void getAllAnnualActivity(@PathVariable("contractId") int contractId, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        contractService.removeRenew(token, contractId);
    }

}
