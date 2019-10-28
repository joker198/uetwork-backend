package uet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.DTO.FollowDTO;
import uet.DTO.InfoBySchoolDTO;
import uet.model.Follow;
import uet.model.Role;
import uet.service.FollowService;
import uet.stereotype.RequiredRoles;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by nhkha on 16/02/2017.
 */
@RestController
public class FollowController {

    private final
    FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    //show all follow
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "follows", method = RequestMethod.GET)
    public List<Follow> getAllFollow(){
        return followService.getAllFollow();
    }

    // show all wait interview follow
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "follow/waitInterview", method = RequestMethod.GET)
    public List<Follow> getWaitInterviewFollow(){
        return followService.getWaitInterviewFollow();
    }

    //show all follow of partner
    @RequiredRoles({Role.ADMIN, Role.VIP_PARTNER})
    @RequestMapping(value = "/follow/partner/{partnerId}", method = RequestMethod.GET)
    public List<Follow> getAllFollowBypartner(@PathVariable("partnerId") int partnerId){
        return followService.getAllFollowBypartner(partnerId);
    }

//    getAllFollowByInternshipTerm
@RequiredRoles(Role.ADMIN)
@RequestMapping(value = "follow/internshipTerm", method = RequestMethod.GET)
public List<Follow> getAllFollowByInternshipTerm() throws Exception {
    return followService.getAllFollowByInternshipTerm();
}

    // show all follows of student
    @RequiredRoles({Role.STUDENT, Role.ADMIN})
    @RequestMapping(value="student/{studentId}/follow",method = RequestMethod.GET)
    public List<Follow> showAllFollowsOfStudent(@PathVariable("studentId") int studentId, HttpServletRequest request) throws Exception {
        String token =request.getHeader("auth-token");
        return followService.showAllFollowsOfStudent(studentId, token);
    }

    // show all follows of post
    @RequiredRoles({Role.STUDENT, Role.VIP_PARTNER,Role.ADMIN})
    @RequestMapping(value="post/{postId}/follow",method = RequestMethod.GET)
    public List<Follow> showAllFollowsOfPost(@PathVariable("postId") int postId, HttpServletRequest request){
        String token =request.getHeader("auth-token");
        return followService.showAllFollowsOfPost(postId, token);
    }

    //check follow
    @RequiredRoles({Role.STUDENT, Role.VIP_PARTNER})
    @RequestMapping(value="/post/{postId}/checkFollow",method = RequestMethod.PUT)
    public FollowDTO checkFollow(@PathVariable("postId") int postId, HttpServletRequest request, @RequestBody FollowDTO followDTO){
        String token =request.getHeader("auth-token");
        return followService.checkFollow(token, followDTO);
    }

    //follow a post
    @RequiredRoles({Role.STUDENT})
    @RequestMapping(value="/post/{postId}/follow",method = RequestMethod.PUT)
    public void createFollow(@PathVariable("postId") int postId, @RequestBody FollowDTO followDTO, HttpServletRequest request) throws Exception {
        String token =request.getHeader("auth-token");
        followService.createFollow(postId , token, followDTO);
    }

    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value="/partner/{partnerId}/follownew",method = RequestMethod.POST)
    public void createFollowNew(@PathVariable("partnerId") int partnerId, @RequestBody String emailVNU, HttpServletRequest request) throws Exception {
        String token =request.getHeader("auth-token");
        followService.createFollowNew(partnerId , emailVNU, token);
    }

//    follow nckh
    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "follow/research/{lecturersId}", method = RequestMethod.PUT)
    public void followResearch(@PathVariable("lecturersId") int lecturersId, HttpServletRequest request) throws Exception {
        String token =request.getHeader("auth-token");
        followService.followResearch(lecturersId, token);
    }

    //Add follow by students
    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "/addFollowByStudent", method = RequestMethod.POST)
    public void addFollowByStudent(@RequestBody FollowDTO followDTO, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        followService.addFollowByStudent(followDTO, token);
    }

    //unfollow
    @RequestMapping(value="/post/{postId}/student/unfollow", method = RequestMethod.DELETE)
    @RequiredRoles({Role.STUDENT,Role.ADMIN})
    public void deleteJs(@PathVariable("postId") int postId, HttpServletRequest request, @RequestBody FollowDTO followDTO){
        String token = request.getHeader("auth-token");
        followService.unfollow(postId,token, followDTO);
    }

    //change post tittle trong follow thanh post type
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "change/follow/postType", method = RequestMethod.GET)
    public void changeFollow(){
        followService.changeFollow();
    }

    //send mail thuc tap ngoai
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "recruitment/sendmail", method = RequestMethod.PUT)
    public void sendMailRecruitmentOther(){
        followService.sendMailRecruitmentOther();
    }

    //get follow cty ngoai cua 1 sinh vien

//    @RequestMapping(value = "")
}
