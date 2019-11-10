package uet.controller;

import uet.DTO.*;
import uet.model.AnnualActivity;
import uet.model.Contract;
import uet.model.ContractShare;
import uet.model.Role;
import uet.service.ContractService;
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

    /**
     * Create Contract
     *
     * @param contractDTO
     * @param request
     * @throws Exception 
     */
    @RequestMapping(value = "contract/create", method = RequestMethod.POST)
    public void createContract(@RequestBody ContractDTO contractDTO, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        contractService.createContract(contractDTO, token);
    }

    /**
     * Renew Contract
     *
     * @param contractId
     * @param contractDTO
     * @param request
     * @throws Exception 
     */
    @RequiredRoles({Role.ADMIN_UNIT, Role.UNIT,Role.ADMIN_VNU})
    @RequestMapping(value = "contract/{contractId}/renew", method = RequestMethod.POST)
    public void renewContract(@PathVariable("contractId") int contractId,@RequestBody ContractDTO contractDTO,
                              HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        contractService.renewContract(contractDTO, contractId, token);
    }

    /**
     * Edit Contract
     *
     * @param contractId
     * @param contractDTO
     * @param request
     * @throws IOException 
     */
    @RequiredRoles({Role.ADMIN_VNU, Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "contract/{contractId}/edit", method = RequestMethod.POST)
    public void editContract(@PathVariable("contractId") int contractId, @RequestBody ContractDTO contractDTO,
                             HttpServletRequest request) throws IOException {
        String token = request.getHeader("auth-token");
        contractService.editContract(contractDTO, contractId, token);
    }

    /**
     * Delete Contract
     *
     * @param contractId
     * @param request 
     */
    @RequestMapping(value = "contract/{contractId}/delete", method = RequestMethod.DELETE)
    public void deleteContract(@PathVariable("contractId") int contractId, HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        contractService.deleteContract(contractId, token);
    }

    /**
     * Show All Contract
     *
     * @return 
     */
    @RequiredRoles({Role.ADMIN_VNU, Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "contract", method = RequestMethod.GET)
    public List<Contract> getAllContract() {
        return contractService.getAllContract();
    }

    /**
     * 
     * @param request
     * @return 
     */
    @RequiredRoles({Role.ADMIN_VNU, Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "contract/RoleAndSigningLevel", method = RequestMethod.GET)
    public List<Contract> getContractByRoleAndSigningLevel(HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        return contractService.getContractByRoleAndSigningLevel(token);
    }

    /**
     * Check contract
     *
     * @param checkContractDTO
     * @return 
     */
    @RequestMapping(value = "checkContract", method = RequestMethod.POST)
    public CheckContractDTO checkContract(@RequestBody CheckContractDTO checkContractDTO) {
        return contractService.checkContract(checkContractDTO);
    }

    /**
     * Import Excel
     *
     * @param list
     * @param request
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "contract/excel", method = RequestMethod.POST)
    public List<ExcelContractDTO> importExcel(@RequestBody Set<ExcelContractDTO> list, HttpServletRequest request) throws IOException {
        String token = request.getHeader("auth-token");
        return contractService.importExcel(list, token);
    }

    /**
     * Edit Cooperate Activity
     *
     * @param cooperateActivityDTO
     * @param request 
     */
    @RequestMapping(value = "cooperateActivity/edit", method = RequestMethod.PUT)
    public void editCooperateActivity(@RequestBody CooperateActivityDTO cooperateActivityDTO, HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        contractService.editCooperateActivity(cooperateActivityDTO, token);
    }

    /**
     * Delete Cooperate Activity
     *
     * @param cooperateActivityId
     * @param request 
     */
    @RequestMapping(value = "cooperateActivity/{cooperateActivityId}/delete", method = RequestMethod.DELETE)
    public void deleteCooperateActivity(@PathVariable("cooperateActivityId") int cooperateActivityId,
                                        HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        contractService.deleteCooperateActivity(cooperateActivityId, token);
    }

    /**
     * Add Cooperate Activity
     *
     * @param cooperateActivityDTO
     * @param contractId
     * @param request 
     */
    @RequestMapping(value = "contract/{contractId}/cooperateActivity/add", method = RequestMethod.POST)
    public void addCooperateActivity(@RequestBody CooperateActivityDTO cooperateActivityDTO,
                                     @PathVariable("contractId") int contractId,
                                     HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        contractService.addCooperateActivity(cooperateActivityDTO, contractId, token);
    }

    /**
     * Get All Contract Of Unit
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = "unit/contract", method = RequestMethod.GET)
    public List<Contract> getContractOfUnit(HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        return contractService.getContractOfUnit(token);
    }

    /**
     * Get All Contract Of Partner
     *
     * @param partnerId
     * @return 
     */
    @RequiredRoles(Role.ADMIN_UNIT)
    @RequestMapping(value = "partner/{partnerId}/contract", method = RequestMethod.GET)
    public List<Contract> getAllContractOfPartner(@PathVariable("partnerId") int partnerId){
        return contractService.getAllContractOfPartner(partnerId);
    }

    /**
     * 
     * @param contractDTOList 
     */
    @RequiredRoles({Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "contract/share/create", method = RequestMethod.POST)
    public void shareContract (@RequestBody List<ContractDTO> contractDTOList){
        contractService.shareContract(contractDTOList);
    }

    /**
     * 
     * @param contractDTOList 
     */
    @RequiredRoles({Role.ADMIN_UNIT, Role.ADMIN_VNU})
    @RequestMapping(value = "contract/vnu/share/create", method = RequestMethod.POST)
    public void shareContractVnu (@RequestBody List<ContractDTO> contractDTOList){
        contractService.shareContractVnu(contractDTOList);
    }

    /**
     * 
     * @param listId 
     */
    @RequiredRoles({Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "contract/share/delete", method = RequestMethod.POST)
    public void deleteContractShare (@RequestBody List<Integer> listId){
        contractService.deleteContractShare(listId);
    }

    /**
     * Get All Contract Share Of Unit
     *
     * @param unitNameId
     * @return 
     */
    @RequiredRoles({Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "contract/share/unit/{unitNameId}", method = RequestMethod.GET)
    public List<ContractShare> getAllContractShareOfUnit (@PathVariable("unitNameId") int unitNameId){
        return contractService.getAllContractShareOfUnit(unitNameId);
    }

    /**
     * Get All Contract Share Of Contract
     *
     * @param contractId
     * @return 
     */
    @RequiredRoles({Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "contract/share/contract/{contractId}", method = RequestMethod.GET)
    public List<ContractShare> getAllContractShareOfContract (@PathVariable("contractId") int contractId){
        return contractService.getAllContractShareOfContract(contractId);
    }

    /**
     * Edit Annual Activity
     *
     * @param annualActivityDTO
     * @throws Exception 
     */
    @RequiredRoles(Role.ADMIN_UNIT)
    @RequestMapping(value = "partner/annualActivity/edit", method = RequestMethod.POST)
    public void editAnnualActivity(@RequestBody AnnualActivityDTO annualActivityDTO) throws Exception {
        contractService.editAnnualActivity(annualActivityDTO);
    }

    /**
     * Delete Annual Activity
     *
     * @param annualActivityId
     * @throws Exception 
     */
    @RequiredRoles(Role.ADMIN_UNIT)
    @RequestMapping(value = "partner/annualActivity/delete", method = RequestMethod.DELETE)
    public void deleteAnnaulActivity(@RequestBody int annualActivityId) throws Exception {
        contractService.deleteAnnaulActivity(annualActivityId);
    }

    /**
     * Get All Annual Activity
     *
     * @param cooperateActivityDetailDTO
     * @throws Exception 
     */
    @RequiredRoles({Role.ADMIN_UNIT, Role.UNIT})
    @RequestMapping(value = "cooperate/activity/detail/edit", method = RequestMethod.POST)
    public void getAllAnnualActivity(@RequestBody CooperateActivityDetailDTO cooperateActivityDetailDTO) throws Exception {
        contractService.editCooperateActivityDetail(cooperateActivityDetailDTO);
    }

    /**
     * Get All Annual Activity
     *
     * @param contractId
     * @param request
     * @throws Exception 
     */
    @RequiredRoles({Role.ADMIN_UNIT, Role.UNIT,Role.ADMIN_VNU})
    @RequestMapping(value = "contract/{contractId}/remove/renew", method = RequestMethod.POST)
    public void getAllAnnualActivity(@PathVariable("contractId") int contractId, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        contractService.removeRenew(token, contractId);
    }

}
