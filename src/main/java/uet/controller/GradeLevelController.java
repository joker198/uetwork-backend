package uet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.model.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import uet.service.GradeLevelService;
import uet.stereotype.RequiredRoles;

/**
 * @author joker
 */
@RestController
public class GradeLevelController {
    private final GradeLevelService gradeLevelService;

    @Autowired
    public GradeLevelController(GradeLevelService gradeLevelService) {
        this.gradeLevelService = gradeLevelService;
    }

    @RequestMapping(value = "/grade-levels", method = RequestMethod.GET)
    public List<GradeLevel> getAll(HttpServletRequest request, HttpServletResponse response)
    {
        String token = request.getHeader("auth-token");
        List<GradeLevel> listGradeLevel = gradeLevelService.getAll(token);
        if (listGradeLevel == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
        return listGradeLevel;
    }
    
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "/grade-levels", method = RequestMethod.POST)
    public List<GradeLevel> create(HttpServletRequest request, @RequestBody String userRequest, HttpServletResponse response)
    {
        JSONObject params = new JSONObject(userRequest);
        String token = request.getHeader("auth-token");
        GradeLevel gradeLevel = gradeLevelService.create(token, params);
        if (gradeLevel == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
        return gradeLevelService.getAll(token);
    }

    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "/grade-levels/{gradeLevelId}", method = RequestMethod.DELETE)
    public List<GradeLevel> delete(HttpServletRequest request, @PathVariable("gradeLevelId") int gradeLevelId, HttpServletResponse response)
    {
        String token = request.getHeader("auth-token");
        boolean success = gradeLevelService.delete(token, gradeLevelId);
        if (!success) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return gradeLevelService.getAll(token);
    }
}
