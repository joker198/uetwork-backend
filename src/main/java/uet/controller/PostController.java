package uet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uet.DTO.PostDTO;
import uet.model.Hashtag;
import uet.model.Post;
import uet.model.PostType;
import uet.model.Role;
import uet.service.PostService;
import uet.stereotype.RequiredRoles;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by Trung on 8/29/2016.
 */
@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Show All Post With Pagination
     *
     * @param request
     * @param pageable
     * @return 
     */
    @RequiredRoles({Role.ADMIN, Role.STUDENT})
    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public Page<Post> getAllPosts(HttpServletRequest request, Pageable pageable) {
        String token = request.getHeader("auth-token");
        return postService.getAllPosts(token, pageable);
    }

    /**
     * Show List Post Of A Partner
     *
     * @param partnerId
     * @param request
     * @return 
     */
    @RequiredRoles({Role.ADMIN, Role.STUDENT, Role.VIP_PARTNER})
    @RequestMapping(value = "/partner/{partnerId}/post", method = RequestMethod.GET)
    public List<Post> showAllPost(@PathVariable("partnerId") int partnerId, HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        return postService.showAllPost(partnerId, token);
    }

    /**
     * Show Research Post
     *
     * @param pageable
     * @param postType
     * @return 
     */
    @RequestMapping(value = "post/postType/{postType}", method = RequestMethod.GET)
    public Page<Post> getResearchPost(Pageable pageable, @PathVariable("postType") PostType postType) {
        return postService.getResearchPost(postType, pageable);
    }


    /**
     * Show A Post
     * 
     * @param postId
     * @return 
     */
    @RequiredRoles({Role.ADMIN, Role.VIP_PARTNER, Role.STUDENT})
    @RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
    public Post showPost(@PathVariable("postId") int postId) {
        return postService.showPost(postId);
    }

    /**
     * Create Post
     *
     * @param partnerId
     * @param postDTO
     * @param request
     * @return
     * @throws Exception 
     */
    @RequiredRoles({Role.VIP_PARTNER, Role.NORMAL_PARTNER, Role.ADMIN})
    @RequestMapping(value = "/partner/{partnerId}/post", method = RequestMethod.POST)
    public Post createPost(@PathVariable("partnerId") int partnerId, @RequestBody PostDTO postDTO, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        return postService.createPost(partnerId, postDTO, token);
    }

    /**
     * Edit Post
     *
     * @param postId
     * @param postDTO
     * @param request
     * @return
     * @throws IOException 
     */
    @RequiredRoles({Role.VIP_PARTNER, Role.NORMAL_PARTNER, Role.ADMIN})
    @RequestMapping(value = "/post/{postId}", method = RequestMethod.PUT)
    public Post editPost(@PathVariable("postId") int postId, @RequestBody PostDTO postDTO, HttpServletRequest request) throws IOException {
        String token = request.getHeader("auth-token");
        return postService.editPost(postId, postDTO, token);
    }

    /**
     * Delete Post
     *
     * @param postId
     * @param request 
     */
    @RequiredRoles({Role.VIP_PARTNER, Role.NORMAL_PARTNER, Role.ADMIN})
    @RequestMapping(value = "/post/{postId}", method = RequestMethod.DELETE)
    public void deletePost(@PathVariable("postId") int postId, HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        postService.deletePost(postId, token);
    }

    /**
     * Change Post Status
     *
     * @param postId
     * @return 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "post/{postId}/status", method = RequestMethod.PUT)
    public Post changeStatus(@PathVariable("postId") int postId) {
        return postService.changeStatus(postId);
    }

    /**
     * Get All Tag
     *
     * @return 
     */
    @RequestMapping(value = "hashtag")
    public List<Hashtag> getAllHashtags() {
        return postService.getAllHashtags();
    }

    /**
     * Get Post By Internship Term
     *
     * @param internshipTermId
     * @return 
     */
    @RequestMapping(value = "post/internshipTerm/{internshipTermId}", method = RequestMethod.GET)
    public List<Post> getAllPostByInternshipTerm(@PathVariable("internshipTermId") int internshipTermId) {
        return postService.getAllPostByInternshipTerm(internshipTermId);
    }
    
    /**
     * Get Post By Internship Term
     *
     * @param internshipTermId
     * @return 
     */
    @RequestMapping(value = "post/new/{internshipTermId}", method = RequestMethod.GET)
    public List<Post> getNewPosts(@PathVariable("internshipTermId") int internshipTermId) {
        return postService.getAllPostByInternshipTerm(internshipTermId);
    }
}
