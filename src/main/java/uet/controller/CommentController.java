package uet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.DTO.CommentDTO;
import uet.model.Comment;
import uet.model.Role;
import uet.service.CommentService;
import uet.stereotype.RequiredRoles;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tu on 16-Feb-17.
 */
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * Show All Comments
     *
     * @return 
     */
    @RequiredRoles({Role.STUDENT, Role.ADMIN})
    @RequestMapping(value="/showAllComment", method = RequestMethod.GET)
    public List<HashMap<String, String>> showAllComment(){
        return (List<HashMap<String, String>>) commentService.showAllComment();
    }

    /**
     * Show All Comments Of A Partner
     *
     * @param partnerId
     * @return 
     */
    @RequiredRoles({Role.STUDENT, Role.ADMIN})
    @RequestMapping(value="/showAllCommentOfOnePartner/{partnerId}", method = RequestMethod.GET)
    public List<Comment> showAllCommentOfOnePartner(@PathVariable("partnerId") int partnerId) {
        return commentService.showAllCommentOfOnePartner(partnerId);
    }

    /**
     * Comment A Partner
     *
     * @param partnerId
     * @param commentDTO
     * @param request
     * @return 
     */
    @RequiredRoles({Role.STUDENT})
    @RequestMapping(value="writeComment/partner/{partnerId}", method = RequestMethod.POST)
    public Comment writeComment(@PathVariable("partnerId") int partnerId,
                                @RequestBody CommentDTO commentDTO, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return commentService.writeComment(partnerId, commentDTO, token);
    }

    /**
     * Show 5 Comments To Homepage Which Are Filtered By Admin ( filter field != null )
     * 
     * @return 
     */
    @RequiredRoles({Role.ADMIN, Role.VIP_PARTNER, Role.STUDENT, Role.NORMAL_PARTNER, Role.OTHER_PARTNER})
    @RequestMapping(value="/showTopComment", method = RequestMethod.GET)
    public List<Comment> showTopComment(){
        return commentService.showTopComment();
    }

    /**
     * Admin Change Filter Field In Order To Add Comment To Homepage
     *
     * @param commentId
     * @param commentDTO
     * @return 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value="/changeFilterValue/{commentId}", method = RequestMethod.PUT)
    public Comment changeFilterValue(@PathVariable("commentId") int commentId, @RequestBody CommentDTO commentDTO){
        return commentService.changeFilterValue(commentId, commentDTO);
    }

    /**
     * Check Comment
     *
     * @param studentId
     * @return 
     */
    @RequiredRoles({Role.STUDENT, Role.ADMIN})
    @RequestMapping(value="/student/{studentId}/checkComment", method = RequestMethod.GET)
    public Comment checkComment(@PathVariable("studentId") int studentId){
        return commentService.checkComment(studentId);
    }

}
