package uet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.DTO.JobSkillDTO;
import uet.model.JobSkill;
import uet.model.Role;
import uet.service.JobSkillService;
import uet.stereotype.RequiredRoles;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by fgv on 7/8/2016.
 */
@RestController
public class JobSkillController {

    private final JobSkillService jobSkillService;

    @Autowired
    public JobSkillController(JobSkillService jobSkillService) {
        this.jobSkillService = jobSkillService;
    }

    /**
     * Show All Job Skill
     *
     * @return 
     */
    @RequiredRoles({Role.STUDENT, Role.VIP_PARTNER,Role.ADMIN})
    @RequestMapping(value = "/jobSkill",method = RequestMethod.GET)
    public List<JobSkill> getalljobskill(){
        return jobSkillService.getJobSkills();
    }

    /**
     * Show all Job Skill Of A Student
     *
     * @param studentId
     * @return 
     */
    @RequiredRoles({Role.STUDENT, Role.VIP_PARTNER,Role.ADMIN})
    @RequestMapping(value="student/{studentId}/jobSkill",method = RequestMethod.GET)
    public List<JobSkill> getallInStudent(@PathVariable("studentId") int studentId){
        return jobSkillService.getallInStudent(studentId);
    }

    /**
     * Show Job Skill By Id
     *
     * @param jobskillId
     * @return 
     */
    @RequiredRoles({Role.STUDENT,Role.ADMIN,Role.VIP_PARTNER})
    @RequestMapping(value="/jobSkill/{jobSkillId}", method = RequestMethod.GET)
    public JobSkill showById(@PathVariable("jobSkillId") int jobskillId){
        return jobSkillService.showJobSkill(jobskillId);
    }

    /**
     * Create Job Skill
     *
     * @param studentId
     * @param jobSkillDTO
     * @param request
     * @return 
     */
    @RequiredRoles({Role.STUDENT})
    @RequestMapping(value="/student/{studentId}/jobSkill",method = RequestMethod.POST)
    public JobSkill createJs(@PathVariable("studentId") int studentId,@RequestBody JobSkillDTO jobSkillDTO,HttpServletRequest request){
        String token =request.getHeader("auth-token");
        return jobSkillService.createJs(studentId,jobSkillDTO,token);
    }

    /**
     * Edit Job Skill
     *
     * @param id
     * @param jobSkillDTO
     * @param request
     * @return 
     */
    @RequiredRoles({Role.STUDENT})
    @RequestMapping(value="/jobSkill/{jobSkillId}", method = RequestMethod.PUT)
    public JobSkill ChangeJs(@PathVariable("jobSkillId") int id, @RequestBody JobSkillDTO jobSkillDTO,HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return jobSkillService.ChangeJsById(id,jobSkillDTO,token);
    }

    /**
     * Delete Job Skill
     *
     * @param id
     * @param request 
     */
    @RequestMapping(value="/jobSkill/{jobSkillId}", method = RequestMethod.DELETE)
    @RequiredRoles({Role.STUDENT,Role.ADMIN})
    public void deleteJs(@PathVariable("jobSkillId") int id,HttpServletRequest request){
        String token = request.getHeader("auth-token");
        jobSkillService.deleteJobSkill(id,token);
    }

}
