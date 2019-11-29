package uet.controller;

import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.model.Role;
import uet.service.PartnerTermService;
import uet.stereotype.RequiredRoles;

/**
 *
 * @author joker
 */
@RestController
public class PartnerTermController {
    @Autowired
    private PartnerTermService partnerTermService;

    public PartnerTermController()
    {
        //                                                                  
    }

    public PartnerTermController(PartnerTermService partnerTermService) {
        this.partnerTermService = partnerTermService;
    }
    
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "/partner-term", method = RequestMethod.POST)
    public void create(@RequestBody String request, HttpServletResponse response)
    {
        JSONObject data = new JSONObject(request);
        if (validation(data)) {
            int internshipTermId = data.getInt("internshipTerm");
            JSONArray partnerIds = data.getJSONArray("partnerIds");
            this.partnerTermService.create(internshipTermId, partnerIds);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    private boolean validation(JSONObject request)
    {
        if (!request.has("internshipTerm") || !request.has("partnerIds")) {
            return false;
        }
        return request.getJSONArray("partnerIds").length() > 0;
    }
}
