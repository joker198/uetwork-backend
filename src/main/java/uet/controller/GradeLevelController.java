package uet.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.service.GradeLevelService;
import uet.model.GradeLevel;

/**
 * @author joker
 */
@RestController
public class GradeLevelController
{
    @Autowired
    private GradeLevelService gradeLevelService;

    @RequestMapping(value = "/grade-levels", method = RequestMethod.GET)
    public List<GradeLevel> getAll()
    {
        return gradeLevelService.getAll();
    }
}
