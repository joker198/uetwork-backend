package uet.service;

//import com.sun.jmx.snmp.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uet.DTO.HashtagDTO;
import uet.DTO.PartnerContactDTO;
import uet.DTO.PostDTO;
import uet.config.GlobalConfig;
import uet.model.*;
import uet.repository.*;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static uet.model.PostType.*;

/**
 * Created by Trung on 8/29/2016.
 */
@Service
public class PostService {
    final UserRepository userRepository;
    private final PartnerRepository partnerRepository;
    private final PostRepository postRepository;
    private final HashtagRepository hashtagRepository;
    private final PartnerContactRepository partnerContactRepository;
    private final InternshipTermRepository internshipTermRepository;
    private final FollowRepository followRepository;

    @Autowired
    public PostService(
        UserRepository userRepository,
        PartnerRepository partnerRepository,
        PostRepository postRepository,
        HashtagRepository hashtagRepository,
        PartnerContactRepository partnerContactRepository,
        InternshipTermRepository internshipTermRepository,
        FollowRepository followRepository
    ) {
        this.userRepository = userRepository;
        this.partnerRepository = partnerRepository;
        this.postRepository = postRepository;
        this.hashtagRepository = hashtagRepository;
        this.partnerContactRepository = partnerContactRepository;
        this.internshipTermRepository = internshipTermRepository;
        this.followRepository = followRepository;
    }

    private Post createPost(Post post, PostDTO postDTO, Partner partner, User user) throws IOException {
        InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
        if(internshipTerm != null){
            post.setContent(postDTO.getContent());
            post.setDatePost(postDTO.getDatePost());
            post.setTitle(postDTO.getTitle());
            post.setExpiryTime(postDTO.getExpiryTime());
            post.setDurationTime(postDTO.getDurationTime());
            post.setRequiredNumber(postDTO.getRequiredNumber());
            post.setPartner(partner);
            post.setPostType(postDTO.getPostType());
            post.setPartnerName(partner.getPartnerName());
            if (user.getRole().equals(String.valueOf(Role.VIP_PARTNER)) || user.getRole().equals(String.valueOf(Role.ADMIN))) {
                post.setStatus("A");
            } else if (Objects.equals(user.getRole(), String.valueOf(Role.NORMAL_PARTNER))) {
                post.setStatus("D");
            }
            if(postDTO.getPartnerContactId() != null){
                if (postDTO.getPartnerContactId() != 0) {
                    PartnerContact partnerContact = partnerContactRepository.findById(postDTO.getPartnerContactId());
                    if(partnerContact != null){
                        if (partnerContact.getPartner().equals(partner)) {
                            post.setPartnerContact(partnerContact);
                        }
                    } else {
                        post.setPartnerContact(null);
                    }
                } else {
                    PartnerContactDTO partnerContactDTO = postDTO.getPartnerContactDTO();
                    if(partnerContactDTO != null){
                        PartnerContact partnerContact = new PartnerContact();
                        partnerContact.setPartner(partner);
                        partnerContact.setContactName(partnerContactDTO.getContactName());
                        partnerContact.setEmail(partnerContactDTO.getEmail());
                        partnerContact.setPhone(partnerContactDTO.getPhone());
                        partner.getPartnerContacts().add(partnerContact);
                        partnerContactRepository.save(partnerContact);
                        post.setPartnerContact(partnerContact);
                    }
                }
            }

            postRepository.save(post);
            List<Follow> follows = (List<Follow>) followRepository.findByInternshipTermAndPostIdAndPartner(internshipTerm.getId(), 0,partner);
            for(Follow follow : follows){
                follow.setPost(post);
                follow.setPostId(post.getId());
            }
            if (postDTO.getHashtagDTO() != null) {
                List<HashtagDTO> hashtagDTOS = postDTO.getHashtagDTO();
                createHashtag(post.getId(), hashtagDTOS);
            }
            internshipTerm.getPosts().add(post);
            int postCount = internshipTerm.getPostCount() + 1;
            internshipTerm.setPostCount(postCount);
            post.setInternshipTerm(internshipTerm);
            internshipTermRepository.save(internshipTerm);
            return postRepository.save(post);
        } else {
            throw new NullPointerException("Chưa có kì thực tập!");
        }

    }

    private Post uploadImage(String username, Post post, PostDTO postDTO) throws IOException
    {
        String pathname = GlobalConfig.sourceAddress + "/app/users_data/" + username + "/post/";
        File directory = new File(pathname);
        if (!directory.exists()) {
            directory.mkdir();
        }
        pathname = pathname + String.valueOf(post.getId()) + "/";
        directory = new File(pathname);
        if (!directory.exists()) {
            directory.mkdir();
        }
        String directoryName = "/users_data/" + username + "/post/" + String.valueOf(post.getId()) + "/";
        String fileName = username + "_" + String.valueOf(post.getId()) + ".jpg";
        byte[] btDataFile = DatatypeConverter.parseBase64Binary(postDTO.getImage());
        File of = new File(pathname + fileName);
        FileOutputStream osf = new FileOutputStream(of);
        osf.write(btDataFile);
        osf.flush();
        String result = directoryName + fileName;
        post.setImage(result);
        return post;
    }

    private void createHashtag(int postId, List<HashtagDTO> List)
    {
        Post post = postRepository.findById(postId);
        List<Hashtag> hashtags = (List<Hashtag>) hashtagRepository.findAll();
        for (HashtagDTO hashtagDTO : List) {
            String tag = hashtagDTO.getTag();
            if (hashtagRepository.findByTag(tag) != null) {
                for (Hashtag hashtag : hashtags) {
                    if (hashtag.getTag().equals(tag)) {
                        post.getHashtags().add(hashtagRepository.findByTag(tag));
                        postRepository.save(post);
                        break;
                    }
                }
            } else {
                Hashtag hashtag = new Hashtag();
                hashtag.setTag(tag);
                post.getHashtags().add(hashtag);
                hashtagRepository.save(hashtag);
            }
        }
    }

    public Page<Post> getAllPosts(String token, Pageable pageable)
    {
        User user = userRepository.findByToken(token);
        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            return postRepository.findAllByOrderByIdDesc(pageable);
        } else if (user.getRole().equals(String.valueOf(Role.STUDENT))) {
            Page<Post> listPost =  postRepository.findAllByStatusOrderByIdDesc("A", pageable);
            for(Post post : listPost.getContent()){
                post.setContent(null);
            }
            return listPost;
        } else {throw new NullPointerException("Error.");
        }
    }

    public List<Post> showAllPost(int partnerId, String token)
    {
        User user = userRepository.findByToken(token);
        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            Partner partner = partnerRepository.findById(partnerId);
            return partner.getPost();
        } else if (user.getRole().equals(String.valueOf(Role.STUDENT)) || user.getRole().equals(String.valueOf(Role.VIP_PARTNER))) {
            return postRepository.findByPartnerIdAndStatus(partnerId, "A");
        } else {
            throw new NullPointerException("Error.");
        }
    }

    public Post showPost(int postId)
    {
        return postRepository.findOne(postId);
    }

    public Post createPost(int partnerId, PostDTO postDTO, String token) throws Exception
    {
        User user = userRepository.findByToken(token);
        if(postDTO.getPostType().equals(PostType.Research)){
            InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
            Post post = postRepository.findByPostTypeAndInternshipTermId(PostType.Research, internshipTerm.getId());
            if(post == null){
                Partner partner = partnerRepository.findOne(partnerId);
                if (user.getRole().equals(String.valueOf(Role.ADMIN)) || user.getPartner().equals(partner)) {
                    Post post1 = new Post();
                    return this.createPost(post1, postDTO, partner, user);
                } else {
                    throw new NullPointerException("User doesn't match with Partner.");
                }
            } else {
                throw new Exception("Reseach post available!");
            }
        } else {
            if(partnerId == -1){
                Partner partner = postDTO.getPartner();
                PartnerContactDTO  partnerContactDTO = postDTO.getPartnerContactDTO();
                partnerRepository.save(partner);
                PartnerContact partnerContact = new PartnerContact();
                partnerContact.setPhone(partnerContactDTO.getPhone());
                partnerContact.setEmail(partnerContactDTO.getEmail());
                partnerContact.setContactName(partnerContactDTO.getContactName());
                partnerContact.setPartner(partner);
                partnerContactRepository.save(partnerContact);
                Post post = new Post();
                postDTO.setPartnerContactId(partnerContact.getId());
                return this.createPost(post, postDTO, partner, user);
            } else {
                Partner partner = partnerRepository.findOne(partnerId);
                if (user.getRole().equals(String.valueOf(Role.ADMIN)) || user.getPartner().equals(partner)) {
                    Post post = new Post();
                    return this.createPost(post, postDTO, partner, user);
                } else {
                    throw new NullPointerException("User doesn't match with Partner.");
                }
            }
        }
    }

    public Post editPost(int postId, PostDTO postDTO, String token) throws IOException
    {
        User user = userRepository.findByToken(token);
        Post post = postRepository.findById(postId);
        if (user.getRole().equals(String.valueOf(Role.ADMIN)) || post.getPartner().equals(user.getPartner())) {
            if(postDTO.getPartnerName() != null){
                post.setPartnerName(postDTO.getPartnerName());
            }
            if (postDTO.getContent() != null) {
                post.setContent(postDTO.getContent());
            }
            if (postDTO.getTitle() != null) {
                post.setTitle(postDTO.getTitle());
            }
            if (postDTO.getRequiredNumber() != null) {
                post.setRequiredNumber(postDTO.getRequiredNumber());
            }
            if (postDTO.getDatePost() != null) {
                post.setDatePost(postDTO.getDatePost());
            }
            if (postDTO.getHashtagDTO() != null) {
                List<HashtagDTO> hashtagDTOS = postDTO.getHashtagDTO();
                post.getHashtags().clear();
                createHashtag(postId, hashtagDTOS);
            }
            if (postDTO.getPartnerContactId() > 0) {
                post.setPartnerContact(partnerContactRepository.findById(postDTO.getPartnerContactId()));
            } else if(postDTO.getPartnerContactId() == 0){
                if(postDTO.getPartnerContactDTO() != null){
                    Partner partner = post.getPartner();
                    PartnerContactDTO  partnerContactDTO = postDTO.getPartnerContactDTO();
                    partnerRepository.save(partner);
                    PartnerContact partnerContact = new PartnerContact();
                    partnerContact.setPhone(partnerContactDTO.getPhone());
                    partnerContact.setEmail(partnerContactDTO.getEmail());
                    partnerContact.setContactName(partnerContactDTO.getContactName());
                    partnerContact.setPartner(partner);
                    partnerContactRepository.save(partnerContact);
                    post.setPartnerContact(partnerContact);
                }
            } else if(postDTO.getPartnerContactId() == -1){
                post.setPartnerContact(null);
            }
            if(postDTO.getExpiryTime() != null){
                post.setExpiryTime(postDTO.getExpiryTime());
            }
            return postRepository.save(post);
        } else {
            throw new NullPointerException("User doesn't match with Partner.");
        }
    }

    public void deletePost(int postId, String token)
    {
        User user = userRepository.findByToken(token);
        Post post = postRepository.findById(postId);
        if (user.getRole().equals(String.valueOf(Role.ADMIN)) || post.getPartner().equals(user.getPartner())) {
            post.getPartner().getPost().remove(post);
            post.getHashtags().clear();
            InternshipTerm internshipTerm = post.getInternshipTerm();
            postRepository.delete(post);
            if(internshipTerm != null){
                internshipTerm.setPostCount(internshipTerm.getPostCount() - 1);
                internshipTermRepository.save(internshipTerm);
            }
        } else {
            throw new NullPointerException("User doesn't match with Partner.");
        }
    }

    public Post changeStatus(int postId)
    {
        Post post = postRepository.findOne(postId);
        if (post.getStatus().equals("A")) {
            post.setStatus("D");
        } else if (post.getStatus().equals("D")) {
            post.setStatus("A");
        }
        return postRepository.save(post);
    }

    public List<Hashtag> getAllHashtags()
    {
        return (List<Hashtag>) hashtagRepository.findAll();
    }

    public List<Post> getAllPostByInternshipTerm(int internshipTermId)
    {
        InternshipTerm internshipTerm = internshipTermRepository.findById(internshipTermId);
        if (internshipTerm != null) {
            return internshipTerm.getPosts();
        } else {
            throw new NullPointerException("Internship Term not found!");
        }
    }

    public Page<Post> getResearchPost(PostType postType, Pageable pageable)
    {
        InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
        return postRepository.findByPostTypeAndInternshipTermIdOrderByIdDesc(postType, internshipTerm.getId(), pageable);
    }
}