package uet.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.model.Partner;
import uet.service.PartnerTermService;
import uet.stereotype.RequiredRoles;
import uet.model.Role;

/**
 *
 * @author joker
 */
@RestController
public class PartnerTermController {
    private final PartnerTermService partnerTermService;

    @Autowired
    public PartnerTermController(PartnerTermService partnerTermService) {
        this.partnerTermService = partnerTermService;
    }
    
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "/partner-term", method = RequestMethod.POST)
    public List<Partner> create(@RequestBody String request, HttpServletResponse response)
    {
        JSONObject data = new JSONObject(request);
        if (validation(data)) {
            int internshipTermId = data.getInt("internshipTerm");
            JSONArray partnerIds = data.getJSONArray("partnerIds");
            List<Partner> waitpartners = this.partnerTermService.create(internshipTermId, partnerIds);
            return waitpartners;
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return null;
    }
    
    private boolean validation(JSONObject request)
    {
        if (!request.has("internshipTerm") || !request.has("partnerIds")) {
            return false;
        }
        return request.getJSONArray("partnerIds").length() > 0;
    }
    
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "/pit/{termId}/partners", method = RequestMethod.GET)
    public List<Partner> getPartnerByTerm(@PathVariable("termId") int termId, HttpServletResponse response)
    {
        List<Partner> listPartner = this.partnerTermService.getPartnerByTerm(termId);
        if (listPartner == null)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        return listPartner;
    }
    
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "/pit/{termId}/waits", method = RequestMethod.GET)
    public List<Partner> getWaitRecruitPartner(@PathVariable("termId") int termId, HttpServletResponse response)
    {
        List<Partner> listPartner = this.partnerTermService.getWaitRecruitPartner(termId);
        if (listPartner == null)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        return listPartner;
    }
    
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "/pit/{termId}/selected", method = RequestMethod.GET)
    public Map<String, Object> getPartnerSelected(@PathVariable("termId") int termId, HttpServletResponse response) {
        Map<String, Object> listPartner = this.partnerTermService.getPartnersSelected(termId);
        if (listPartner == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        return listPartner;
    }
}
