package uet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import uet.DTO.FollowDTO;
import uet.DTO.MessageDTO;
import uet.model.*;
import uet.repository.*;
import uet.stereotype.SendMail;

import javax.xml.ws.http.HTTPException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by nhkha on 16/02/2017.
 */
@Service
public class FollowService {
    private final
    FollowRepository followRepository;
    final
    UserRepository userRepository;
    private final
    PartnerRepository partnerRepository;
    private final
    StudentRepository studentRepository;
    private final
    PostRepository postRepository;
    private InfoBySchoolRepository infoBySchoolRepository;
    private MessageRepository messageRepository;
    private InternshipTermRepository internshipTermRepository;
    private LecturersRepository lecturersRepository;
    private InternshipService internshipService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final InternshipRepository internshipRepository;
    private final PartnerType partnerType;

    @Autowired
    public FollowService(FollowRepository followRepository, UserRepository userRepository, PartnerRepository partnerRepository,
                         StudentRepository studentRepository, PostRepository postRepository, InfoBySchoolRepository infoBySchoolRepository, MessageRepository messageRepository, InternshipTermRepository internshipTermRepository,
                         LecturersRepository lecturersRepository, InternshipService internshipService, SimpMessagingTemplate simpMessagingTemplate, InternshipRepository internshipRepository, PartnerType partnerType) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.partnerRepository = partnerRepository;
        this.studentRepository = studentRepository;
        this.postRepository = postRepository;
        this.infoBySchoolRepository = infoBySchoolRepository;
        this.messageRepository = messageRepository;
        this.internshipTermRepository = internshipTermRepository;
        this.lecturersRepository = lecturersRepository;
        this.internshipService = internshipService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.internshipRepository = internshipRepository;
        this.partnerType = partnerType;
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public List<Follow> showAllFollowsOfStudent(int studentId, String token) throws Exception {
        User user = userRepository.findByToken(token);
        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            return followRepository.findByStudentId(studentId);
        } else if (user.getRole().equals(String.valueOf(Role.STUDENT))) {
//            InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
//            Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(studentId, internshipTerm);
//            return followRepository.findByInternshipTermAndStudent(internshipTerm.getId(), internship.getId());
            return user.getStudent().getFollows();
        } else {
            throw new Exception("error");
        }
//        if (user.getStudent().getId() == studentId) {
////            System.out.print(user.getStudent().getFollows());
//
//        } else {
//            throw new NullPointerException("User doesn't match with Student");
//        }
    }

    public List<Follow> showAllFollowsOfPost(int postId, String token) {
        User user = userRepository.findByToken(token);
        Post post = postRepository.findById(postId);
        if (user.getRole().equals(String.valueOf(Role.ADMIN)) || post.getPartner().equals(user.getPartner())) {
            return post.getFollows();
        } else {
            throw new NullPointerException("User doesn't match with Partner");
        }

    }

    public void createFollow(int postId, String token, FollowDTO followDTO) throws Exception {
        User user = userRepository.findByToken(token);
        Post post = postRepository.findById(postId);
        if (post != null) {
            Date yesterday = this.yesterday();
            if (post.getExpiryTime().after(yesterday)) {
                Student student = user.getStudent();
                int studentId = student.getId();
                InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
                Internship internship = internshipService.createInternship(token);
//                Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(studentId, internshipTerm);
//                if (internship == null) {
//                    internship = new Internship(null, student);
//                    internship.setInternshipTerm(internshipTerm);
//                    internshipRepository.save(internship);
//                    int internshipCount = internshipTerm.getInternshipCount() + 1;
//                    internshipTerm.setInternshipCount(internshipCount);
////                                student.setInternshipTerm(internshipTerm);
//                    internshipTermRepository.save(internshipTerm);
//                }
                if (followRepository.findByInternshipIdAndPostId(internship.getId(), postId) == null) {
                    if (!isBlank(student.getFullName()) && !isBlank(student.getEmail()) && !isBlank(student.getPhoneNumber())) {

//                        Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(student.getId(), internshipTerm);

                        Follow follow = new Follow();
                        follow.setPost(post);
                        follow.setInternship(internship);
                        follow.setStudent(student);
                        follow.setPostId(postId);
                        follow.setPostTitle(String.valueOf(post.getPostType()));
                        follow.setStudentName(followDTO.getStudentName());
                        follow.setStatus("WAIT");
//                student.getFollows().add(follow);
                        Partner partner = post.getPartner();
                        follow.setPartner(partner);
                        follow.setPartnerId(partner.getId());
                        follow.setPartnerName(partner.getPartnerName());
//                post.getFollows().add(follow);
                        follow.setInternshipTerm(internshipTermRepository.findTopByOrderByIdDesc().getId());
                        followRepository.save(follow);


//                        int internshipCount = internshipTerm.getInternshipCount() + 1;
//                        internshipTerm.setInternshipCount(internshipCount);
//                        internshipTermRepository.save(internshipTerm);
//                        if(student.getInternshipTerm() == null){
//                            int internshipCount = internshipTerm.getInternshipCount() + 1;
//                            internshipTerm.setInternshipCount(internshipCount);
//                            student.setInternshipTerm(internshipTerm);
//                            internshipTermRepository.save(internshipTerm);
//                            studentRepository.save(student);
//                        } else {
//                            if(student.getInternshipTerm() != internshipTerm){
//                                student.setInternshipTerm(internshipTerm);
//                                studentRepository.save(student);
//
//                            }
//                        }
                    } else {
                        throw new NullPointerException("Please fill all your information in profile!");
                    }
                } else {
                    throw new NullPointerException("Post followed");
                }
            } else {
                throw new NullPointerException("Post expired!");
            }
        } else {
            throw new NullPointerException("Post not found");
        }
//        studentRepository.findById(studentId).setFollows(postRepository.findById(postId));
    }

    // cai nayf thực tế ko sử dụng
    public void createFollowNew(int partnerId, String emailVNU, String token) throws Exception {
        User user = userRepository.findByToken(token);
        InfoBySchool infoBySchool = infoBySchoolRepository.findByEmailvnu(emailVNU);
        Student student = infoBySchool.getStudent();
//        Post post = postRepository.findById(partnerId);
        int studentId = student.getId();
        InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
        Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(studentId, internshipTerm);
        Partner partner = partnerRepository.findById(partnerId);
        if (followRepository.findByInternshipIdAndPartnerIdAndStudent(internship.getId(), partnerId, student) == null) {
            Follow follow = new Follow();
            follow.setInternship(internship);
            follow.setStudent(student);
            follow.setStudentName(student.getFullName());
            follow.setStatus("PASS");
            follow.setPostTitle(partner.getPartnerName());
            follow.setPartner(partner);
            follow.setPartnerId(partner.getId());
            follow.setPartnerName(partner.getPartnerName());
            follow.setInternshipTerm(internshipTerm.getId());
            followRepository.save(follow);

            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setUserId(user.getId());
            messageDTO.setTitle("Thông báo được nhận thực tập");
            messageDTO.setReceiverId(studentId);
            messageDTO.setPartnerId(partner.getId());
            messageDTO.setMessageType(MessageType.PassInterview);
            if (user.getRole().equals(String.valueOf(Role.ADMIN)) || user.getRole().equals(String.valueOf(Role.VIP_PARTNER))) {
//                PassInterview passInterview = internshipService.createPassinterviewLink(messageDTO.getReceiverId(),
//                        messageDTO.getPartnerId());
                messageDTO.setContent("Bạn có thể chọn " + partner.getPartnerName() + " để làm thực tập. <br />" +
                        "Vui lòng kiểm tra " + "danh sách thực tập để chọn nơi thực tập.");
                Message message = new Message(messageDTO.getTitle(), messageDTO.getContent(), "NEW", user.getUserName(),
                        MessageType.PassInterview, student.getUser().getUserName());
                message.setUser(student.getUser());
                message.setLastUpdated(new Date());
                ApplicationContext context =
                        new ClassPathXmlApplicationContext("Spring-Mail.xml");
                SendMail mm = (SendMail) context.getBean("sendMail");

                mm.sendMail("carbc@vnu.edu.vn",
                        student.getEmail(),
                        messageDTO.getTitle(),
                        "Bạn có thể chọn " + partner.getPartnerName() + " để làm thực tập. " +
                                "Vui lòng kiểm tra danh sách thực tập để chọn nơi thực tập.");
                messageRepository.save(message);
            }
        }
    }

    public void sendMessage(User user, Student student) {
    }

    //add follow by students
    public void addFollowByStudent(FollowDTO followDTO, String token) throws Exception {

        User user = userRepository.findByToken(token);
        Student student = user.getStudent();
        if (followDTO.getPartner() != null) {
            if (student != null) {
                InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
                Internship internship = internshipService.createInternship(token);
//                Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(student.getId(), internshipTerm);
//
//                if (internship == null) {
//                    internship = new Internship(null, student);
//                    internship.setInternshipTerm(internshipTerm);
//                    internshipRepository.save(internship);
//                    int internshipCount = internshipTerm.getInternshipCount() + 1;
//                    internshipTerm.setInternshipCount(internshipCount);
//                    internshipTermRepository.save(internshipTerm);
//                }
                Follow follow1 = followRepository.findByInternshipIdAndPostIdAndPostTitle(internship.getId(), 0, followDTO.getPostTitle());
                if (follow1 == null) {
                    if (!isBlank(student.getFullName()) && !isBlank(student.getEmail()) && !isBlank(student.getPhoneNumber())) {

                        Partner partner = partnerRepository.findById(followDTO.getPartner().getId());
                        if (partner != null) {
//                            Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(student.getId(), internshipTerm);

                            //check là có phải partner ko phải là fit và đã dc accept chưa, nếu được accept rồi thì cứ thế mà tạo, còn nếu mà chưa
                            // thì retunrn
                            Follow follow = new Follow();
                            follow.setInternship(internship);
                            follow.setStudent(student);
                            follow.setStudentName(student.getFullName());

                            follow.setStatus("WAIT");
                            if(partner.getUser() == null){
                                follow.setPostTitle("Recruitment_other");
                                if(partner.getStatus() != null){
                                    switch (partner.getStatus()) {
                                        case "ACCEPTED":
                                            follow.setStatus("PASS");
                                            break;
                                        case "WAIT":
                                            follow.setStatus("WAIT");
                                            break;
                                        case "NOT_ACCEPTED":
                                            Message message = new Message();
                                            message.setTitle("Thông báo đăng ký công ty thực tập không được chấp nhận");
                                            message.setContent("Đăng kí thực tập tại " + partner.getPartnerName() + " không được chấp nhận." + "<br />" + "");
                                            message.setStatus("NEW");
                                            message.setSendDate(new Date());
                                            message.setSenderName("pdt");
                                            message.setReceiverName(user.getUserName());
                                            message.setMessageType(MessageType.Normal);
                                            message.setLastUpdated(new Date());
                                            message.setUser(student.getUser());
                                            messageRepository.save(message);
                                            simpMessagingTemplate.convertAndSend("/user/" + message.getReceiverName() + "/**", message);
//                                            internshipRepository.delete(internship);
//                                            int internshipCount = internshipTerm.getInternshipCount() - 1;
//                                            internshipTerm.setInternshipCount(internshipCount);
//                                            internshipTermRepository.save(internshipTerm);

                                            throw new Exception("NOT_ACCEPTED");
                                    }
                                } else {
                                    follow.setStatus("WAIT");
                                    partner.setStatus("WAIT");
                                    partnerRepository.save(partner);
                                }
                            } else {
                                follow.setPostTitle("Recruitment");
                                follow.setStatus("PASS");
                            }
                            follow.setPartner(partner);
                            follow.setPartnerId(partner.getId());
                            follow.setPartnerName(partner.getPartnerName());
                            follow.setInternshipTerm(internshipTermRepository.findTopByOrderByIdDesc().getId());
                            followRepository.save(follow);
//                            InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();

                            if(followDTO.getPostTitle().equals("Recruitment_other")){
                                Message message = new Message();
                                message.setTitle("Thông báo xác nhận thông tin thực tập");
                                message.setContent("Đăng kí thực tập tại " + partner.getPartnerName() + " thành công." + "<br />" + "Hãy chờ thông báo phê duyệt công ty.");
                                message.setStatus("NEW");
                                message.setSendDate(new Date());
                                message.setSenderName("pdt");
                                message.setReceiverName(user.getUserName());
                                message.setMessageType(MessageType.Normal);
                                message.setLastUpdated(new Date());
                                message.setUser(student.getUser());
                                messageRepository.save(message);
                                simpMessagingTemplate.convertAndSend("/user/" + message.getReceiverName() + "/**", message);
                            }
                        } else if (partner == null) {
                            if (followDTO.getPartnerDTO().getPartnerName() != null && followDTO.getPartnerDTO().getAddress() != null
                                    && followDTO.getPartnerDTO().getEmail() != null && followDTO.getPartnerDTO().getPhone() != null) {
                                Partner partner2 = partnerRepository.findByPartnerName(followDTO.getPartnerDTO().getPartnerName());
                                if (partner2 == null) {
                                    Partner partner1 = new Partner();
                                    partner1.setPartnerName(followDTO.getPartnerDTO().getPartnerName());
                                    partner1.setPartnerType(partnerType.getOtherType());
                                    partner1.setDirector(followDTO.getPartnerDTO().getDirector());
                                    partner1.setEmail(followDTO.getPartnerDTO().getEmail());
                                    partner1.setWebsite(followDTO.getPartnerDTO().getWebsite());
                                    partner1.setPhone(followDTO.getPartnerDTO().getPhone());
                                    partner1.setAddress(followDTO.getPartnerDTO().getAddress());
                                    partner1.setTaxCode(followDTO.getPartnerDTO().getTaxCode());
                                    partner1.setFieldWork(followDTO.getPartnerDTO().getFieldWork());
                                    partner1.setStatus("WAIT");
                                    partnerRepository.save(partner1);
                                    Follow follow = new Follow();
                                    follow.setInternship(internship);
                                    follow.setStudent(student);
                                    follow.setStudentName(student.getFullName());
                                    follow.setStatus("WAIT");
                                    follow.setPartner(partner1);
                                    follow.setPostTitle("Recruitment_other");
                                    follow.setPartnerId(partner1.getId());
                                    follow.setPartnerName(partner1.getPartnerName());
                                    follow.setInternshipTerm(internshipTerm.getId());
                                    followRepository.save(follow);


                                    Message message = new Message();
                                    message.setTitle("Thông báo đăng ký thông tin thực tập");
                                    message.setContent("Đăng kí thông tin của " + partner1.getPartnerName() + " thành công." + "<br />" + "Tin nhắn chấp nhận công ty sẽ được hệ thống gửi lại sau.");
                                    message.setStatus("NEW");
                                    message.setSendDate(new Date());
                                    message.setSenderName("pdt");
                                    message.setReceiverName(user.getUserName());
                                    message.setMessageType(MessageType.Normal);
                                    message.setLastUpdated(new Date());
                                    message.setUser(student.getUser());
                                    messageRepository.save(message);
                                    simpMessagingTemplate.convertAndSend("/user/" + message.getReceiverName() + "/**", message);
                                } else {
                                    throw new Exception("Partner existed!");
                                }
                            } else {
                                throw new Exception("Missing information!");
                            }
                        }
                    } else {
                        throw new Exception("Please fill all your information in profile!");
                    }
                } else {
                    throw new Exception("You already registered partner");
                }
            } else {
                throw new Exception("Student not match");
            }
        }
    }

    public void unfollow(int postId, String token, FollowDTO followDTO) {
        User user = userRepository.findByToken(token);
        int studentId = 0;
        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            if (followDTO != null) {
                if (followDTO.getStudentId() != 0) {
                    studentId = followDTO.getStudentId();
                }
            }
        } else if (user.getRole().equals(String.valueOf(Role.STUDENT))) {
            studentId = user.getStudent().getId();
        }
        InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
//        Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(studentId, internshipTerm);
        Follow follow;
        if(followDTO != null){
            if(followDTO.getPostTitle() != null){
                follow = followRepository.findByStudentIdAndPostTitleAndInternshipTermAndPostId(studentId, followDTO.getPostTitle(), internshipTerm.getId(), postId);
            } else {
                follow = followRepository.findByStudentIdAndPostIdAndInternshipTerm(studentId, postId, internshipTerm.getId());
            }
        } else {
            follow = followRepository.findByStudentIdAndPostIdAndInternshipTerm(studentId, postId, internshipTerm.getId());
        }


        if (follow != null) {
            if (follow.getPost() != null) {
                if (follow.getPost().getExpiryTime().after(this.yesterday())) {
                    if (follow.getStatus().equals("WAIT")) {
                        Internship internship = follow.getInternship();
                        followRepository.delete(follow);
//                        if (internship.getFollows().isEmpty()) {
//                            internshipRepository.delete(internship);
//                            if (internshipTerm.getInternshipCount() > 0) {
//                                internshipTerm.setInternshipCount(internshipTerm.getInternshipCount() - 1);
//                                internshipTermRepository.save(internshipTerm);
//                            }
//                        }
                    } else {
                        throw new NullPointerException("Cannot unfollow!");
                    }
                } else {
                    throw new NullPointerException("Post exprired!");
                }
            } else if (postId == 0) {
                Partner partner = follow.getPartner();
                Internship internship = follow.getInternship();
                followRepository.delete(follow);
//                if (internship.getFollows().isEmpty()) {
//                    internshipRepository.delete(internship);
//                    if (internshipTerm.getInternshipCount() > 0) {
//                        internshipTerm.setInternshipCount(internshipTerm.getInternshipCount() - 1);
//                        internshipTermRepository.save(internshipTerm);
//                    }
//                }
                if (partner.getStatus() != null) {
                    //chỗ này tức là cty vẫn chưa được chấp nhận, vì vậy nếu như ko còn ai đăng kí thì sẽ xóa cty khỏi db
                    if (partner.getStatus().equals("WAIT")) {
                        List<Follow> follows = partner.getFollows();
                        if (follows.isEmpty()) {
                            partnerRepository.delete(partner);
                        }

                    }

                }

            }

        } else {
            throw new NullPointerException("Not found");
        }

//        if (userRepository.findByToken(token).getStudent().getId() == studentId) {
//            Follow follow = followRepository.findByStudentIdAndPostId(studentId, postId);
//            followRepository.delete(follow);
//        } else {
//            throw new NullPointerException("User doesn't match with Student");
//        }
    }

    public FollowDTO checkFollow(String token, FollowDTO followDTO) {
        User user = userRepository.findByToken(token);
        Student student = user.getStudent();
        int studentId = student.getId();
        FollowDTO follow = new FollowDTO();
        InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
//        Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(studentId, internshipTerm);
        Follow follow1;
        if(followDTO != null){
            if(followDTO.getPostTitle() != null){
                follow1 = followRepository.findByStudentIdAndPostTitleAndInternshipTermAndPostId(studentId, followDTO.getPostTitle(), internshipTerm.getId(), 0);
            } else {
                follow1 = followRepository.findByStudentIdAndPostIdAndInternshipTerm(studentId, followDTO.getPostId(), internshipTerm.getId());
            }
//            System.out.print("\n" + String.valueOf(follow1) +"\n");
            if (follow1 == null) {
                follow.setId(0);
                return follow;
            } else {
                follow.setId(1);
                follow.setLecturersName(follow1.getLecturersName());
                if (follow1.getPartnerName() != null) {
                    Partner partner = new Partner();
                    partner.setPartnerName(follow1.getPartner().getPartnerName());
                    partner.setAddress(follow1.getPartner().getAddress());
                    partner.setEmail(follow1.getPartner().getEmail());
                    partner.setPhone(follow1.getPartner().getPhone());
                    follow.setPartner(partner);

                }
                follow.setPostTitle(follow1.getPostTitle());
                follow.setStatus(follow1.getStatus());
                return follow;
            }
        } else {
            throw new HTTPException(404);
        }

    }

    public List<Follow> getAllFollowByInternshipTerm() throws Exception {
        InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
        if (internshipTerm != null) {
            return followRepository.findByInternshipTerm(internshipTerm.getId());
        } else {
            throw new Exception("");
        }
    }

    public List<Follow> getAllFollow() {
        return (List<Follow>) followRepository.findAll();
    }

    public List<Follow> getAllFollowBypartner(int partnerId) {
        return followRepository.findByPartnerId(partnerId);
    }

    public List<Follow> getWaitInterviewFollow() {
        return followRepository.findByStatus("WAIT");
    }

    public void changeFollow() {
        List<Follow> listFollow = (List<Follow>) followRepository.findAll();
        for (Follow follow : listFollow) {
            follow.setPostTitle(String.valueOf(PostType.Recruitment));
            followRepository.save(follow);
        }
    }

    public void followResearch(int lecturersId, String token) throws Exception {
        InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
        Post post = postRepository.findByPostTypeAndInternshipTermId(PostType.Research, internshipTerm.getId());
        if (post != null) {
            Date yesterday = this.yesterday();
            if (post.getExpiryTime().after(yesterday)) {
                User user = userRepository.findByToken(token);
                Student student = user.getStudent();
                int studentId = student.getId();
                if (!isBlank(student.getFullName()) && !isBlank(student.getEmail()) && !isBlank(student.getPhoneNumber())) {
                    Lecturers lecturers = lecturersRepository.findById(lecturersId);
                    if (lecturers != null) {
                        Internship internship = internshipService.createInternship(token);
//                        Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(student.getId(), internshipTerm);
//                        if (internship == null) {
//                            internship = new Internship(null, student);
//                            internship.setInternshipTerm(internshipTerm);
//                            int internshipCount = internshipTerm.getInternshipCount() + 1;
//                            internshipTerm.setInternshipCount(internshipCount);
////                                student.setInternshipTerm(internshipTerm);
//                            internshipTermRepository.save(internshipTerm);
//                            internshipRepository.save(internship);
//                        }
                        Follow follow = followRepository.findByInternshipIdAndPostId(internship.getId(), post.getId());
                        if (follow != null) {
                            follow.setLecturersName(lecturers.getFullName());
                            followRepository.save(follow);
                        } else {
                            Follow follow1 = new Follow();
                            follow1.setPost(post);
                            follow1.setInternship(internship);
                            follow1.setPostId(post.getId());
                            follow1.setStudent(student);
                            follow1.setPostTitle(String.valueOf(post.getPostType()));
                            follow1.setStudentName(student.getFullName());
                            follow1.setStatus("WAIT");
                            follow1.setPartner(post.getPartner());
                            follow1.setPartnerName(post.getPartnerName());
                            follow1.setPartnerId(post.getPartner().getId());
                            follow1.setLecturersName(lecturers.getFullName());
                            follow1.setInternshipTerm(internshipTerm.getId());
                            followRepository.save(follow1);
                        }
                    } else {
                        throw new Exception("Lecturers not found!");
                    }
                } else {
                    throw new Exception("Please fill all your information in profile!");
                }
            } else {
                throw new Exception("Cannot register");
            }
        } else {
            throw new Exception("Not available!");
        }
    }

    public void sendMailRecruitmentOther() {
        List<Follow> followList = followRepository.findByPostTitle("Recruitment_other");
        int[] count = new int[1000];
        ApplicationContext context =
                new ClassPathXmlApplicationContext("Spring-Mail.xml");
        SendMail mm = (SendMail) context.getBean("sendMail");
        for (Follow follow : followList) {

            Student student = follow.getInternship().getStudent();
            if (student.getEmail() != null && count[student.getId()] != 1) {
                count[student.getId()] = 1;
                mm.sendMail("carbc@vnu.edu.vn",
                        student.getEmail(),
                        "test",
                        "test");
            }
        }
    }

}
