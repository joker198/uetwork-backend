package uet.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.model.*;
import uet.repository.*;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author joker
 */
@Service
public class GradeLevelService
{
    private final UserRepository userRepository;
    private final GradeLevelRepository gradeLevelRepository;

    @Autowired
    public GradeLevelService (
        UserRepository userRepository,
        GradeLevelRepository gradeLevelRepository
    ) {
        this.userRepository = userRepository;
        this.gradeLevelRepository = gradeLevelRepository;
    }
    
    public List<GradeLevel> getAll(String token)
    {
        User user = userRepository.findByToken(token);
        if (user == null) {
            return null;
        }
        List<GradeLevel> gradeLevels = gradeLevelRepository.findAllOrderByShortNameDesc();
        return gradeLevels;
    }
    
    public GradeLevel create(String token, JSONObject params)
    {
        User user = userRepository.findByToken(token);
        if (user == null) {
            return null;
        }
        GradeLevel gradeLevel = new GradeLevel();
        gradeLevel.setCode(params.getString("code"));
        gradeLevel.setShortName(params.getString("short_name"));
        gradeLevelRepository.save(gradeLevel);
        return gradeLevel;
    }

    public boolean delete(String token, int gradeLevelId)
    {
        User user = userRepository.findByToken(token);
        if (user == null) {
            return false;
        }
        GradeLevel gradeLevel = gradeLevelRepository.findById(gradeLevelId);
        if (gradeLevel == null) {
            return false;
        }
        gradeLevelRepository.delete(gradeLevel);
        return true;
    }
}

