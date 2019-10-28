package uet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uet.DTO.MessageDTO;
import uet.config.GlobalConfig;
import uet.model.*;
import uet.model.Role;
import uet.repository.*;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by nhkha on 17/05/2017.
 */
@Service
public class MessageService {
    final
    UserRepository userRepository;
    private final
    MessageRepository messageRepository;
    private final
    StudentRepository studentRepository;
    private final
    InternshipService internshipService;
    private final
    FollowRepository followRepository;
    private final InternshipTermRepository internshipTermRepository;

    @Autowired
    public MessageService(UserRepository userRepository, MessageRepository messageRepository, StudentRepository studentRepository, InternshipService internshipService, FollowRepository followRepository, InternshipTermRepository internshipTermRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.studentRepository = studentRepository;
        this.internshipService = internshipService;
        this.followRepository = followRepository;
        this.internshipTermRepository = internshipTermRepository;
    }


    private String attachFileMessage(MessageDTO messageDTO) throws IOException {
        String fileFolder = UUID.randomUUID().toString();
        String pathname = GlobalConfig.sourceAddress + "/app/users_data/" + messageDTO.getSenderName() + "/report/" +
                fileFolder + "/";
        File directory = new File(pathname);
        if (!directory.exists()) {
            directory.mkdir();
        }
        String directoryName = "/users_data/" + messageDTO.getSenderName() + "/report/" + fileFolder + "/";
//        System.out.print(messageDTO.getAttachFile());
        if(messageDTO.getFileType().equals("doc") || messageDTO.getFileType().equals("docx")){
            String fileName = messageDTO.getFileName();
            byte[] btDataFile = DatatypeConverter.parseBase64Binary(messageDTO.getAttachFile());
            File of = new File(pathname + fileName);
            FileOutputStream osf = new FileOutputStream(of);
            osf.write(btDataFile);
            osf.flush();
        }
        return directoryName + messageDTO.getFileName();
    }

    public void createPassInterviewMessage(List<MessageDTO> listMessageDTO, String token) {
        User user = userRepository.findByToken(token);
        for (MessageDTO messageDTO : listMessageDTO) {
            if (messageDTO.getMessageType().equals(MessageType.PassInterview)) {
                if (user.getRole().equals(String.valueOf(Role.ADMIN)) || user.getRole().equals(String.valueOf(Role.VIP_PARTNER))) {
                    Student student = studentRepository.findById(messageDTO.getReceiverId());
//                    PassInterview passInterview = internshipService.createPassinterviewLink(messageDTO.getReceiverId(),
//                            messageDTO.getPartnerId());
                    messageDTO.setContent(messageDTO.getContent() + "<br />" +
//                            "Bạn có thể click vào link sau để lựa chọn nơi" +
//                            " thực tập: <br />http://128.199.155.163:8000/#/confirmationLink/" + passInterview.getComfirmationLink() + "" +
                            "<br />Kiểm tra danh sách công ty đã đăng ký để chọn nơi thực tập.");
                    Message message = new Message(messageDTO.getTitle(), messageDTO.getContent(), "NEW", user.getUserName(),
                            MessageType.PassInterview, student.getUser().getUserName());
                    message.setUser(student.getUser());
                    message.setLastUpdated(new Date());
                    messageRepository.save(message);
                    InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
                    Follow follow;
                    if(messageDTO.getPostTitle() != null){
                        follow = followRepository.findByStudentIdAndPostTitleAndInternshipTermAndPostId(messageDTO.getReceiverId(), messageDTO.getPostTitle(), internshipTerm.getId(), messageDTO.getPostId());
                    } else {
                        follow = followRepository.findByStudentIdAndPostIdAndInternshipTerm(messageDTO.getReceiverId(), messageDTO.getPostId(), internshipTerm.getId());
                    }

                    follow.setStatus("PASS");
                    followRepository.save(follow);
                }
            }
        }
    }

    public void createFailInterview(List<MessageDTO> listMessageDTO, String token) {
        User user = userRepository.findByToken(token);
        for (MessageDTO messageDTO : listMessageDTO) {
//            if (messageDTO.getMessageType().equals(MessageType.PassInterview)) {
            if (user.getRole().equals(String.valueOf(Role.ADMIN)) || user.getRole().equals(String.valueOf(Role.VIP_PARTNER))) {
                Student student = studentRepository.findById(messageDTO.getReceiverId());
                Message message = new Message(messageDTO.getTitle(), messageDTO.getContent(), "NEW", user.getUserName(),
                        MessageType.FailInterview, student.getUser().getUserName());
                message.setUser(student.getUser());
                message.setLastUpdated(new Date());
                messageRepository.save(message);
                InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
                Follow follow;
                if(messageDTO.getPostTitle() != null){
                    follow = followRepository.findByStudentIdAndPostTitleAndInternshipTermAndPostId(messageDTO.getReceiverId(), messageDTO.getPostTitle(), internshipTerm.getId(), messageDTO.getPostId());
                } else {
                    follow = followRepository.findByStudentIdAndPostIdAndInternshipTerm(messageDTO.getReceiverId(), messageDTO.getPostId(), internshipTerm.getId());
                }
                follow.setStatus("FAIL");
                followRepository.save(follow);
            }
//            }
        }
    }
//        if(messageDTO.getReceiverRole().equals(Role.STUDENT)){
//            User user = studentRepository.findById(messageDTO.getReceiverId()).getUser();
//            if(user != null){
//                Message message = new Message(messageDTO.getTitle(), messageDTO.getContent(), "NEW",
//                        messageDTO.getSenderName(), messageDTO.getMessageType(),
//                        messageDTO.getReceiverRole(), messageDTO.getSenderRole(), messageDTO.getReceiverId(),
//                        messageDTO.getSenderId());
//                if(messageDTO.getMessageId() != 0){
//                    Message message1 = messageRepository.findById(messageDTO.getMessageId());
//                    if(message1 != null){
//                        message.setMessage(message1);
//                        message.setMessageType(MessageType.Reply);
//                    }
//                }
//                else {
//                    message.setUser(user);
//                }

//            } else {
//                throw new NullPointerException("User not found!");
//            }
//        } else{
//            throw new NullPointerException("LOI");
//        }
//        User user = userRepository.findById(messageDTO.getUserId());

//    }


    public Page<Message> getMessage(String token, Pageable pageable, Date lastUpdated) {
        User user = userRepository.findByToken(token);
//        Integer last = Integer.valueOf(lastUpdated);
        if(lastUpdated != null){
            return messageRepository.findByUserIdAndMessageTypeNotLikeAndLastUpdatedLessThanOrderByLastUpdatedDesc(user.getId(),
                    MessageType.Reply, lastUpdated, pageable);
        } else {
            return messageRepository.findByUserIdAndMessageTypeNotLikeOrderByLastUpdatedDesc(user.getId(),
                    MessageType.Reply, pageable);
        }
//        if(pageable.getPageSize() > messagesNew.getNumberOfElements()){
//            pageable.set
//        }
//        return user.getMessages();
    }

    public List<Message> getNewMessage(String token, Pageable pageable) {
        User user = userRepository.findByToken(token);
        return messageRepository.findByUserIdAndStatusOrderByIdDesc(user.getId(), "NEW");
    }

    public void markMessageAsSeen(int messageId, String token) {
        User user = userRepository.findByToken(token);
        int userId = user.getId();
        Message message = messageRepository.findByUserIdAndId(userId, messageId);
        if (message != null) {
            message.setStatus("SEEN");
            messageRepository.save(message);
        } else {
            throw new NullPointerException("Message not found!");
        }
    }

//    public List<Message> getPassInterviewMessage(String token, MessageType messageType) {
//        User user = userRepository.findByToken(token);
//        if (user.getStudent() != null) {
//            return messageRepository.findByUserIdAndMessageType(user.getId(), messageType);
//        } else {
//            throw new NullPointerException("No permission!");
//        }
//    }

    public Message writeMessage(MessageDTO messageDTO, String token) throws IOException {
        User user = userRepository.findByToken(token);
        if(user.getUserName().equals(messageDTO.getReceiverName())){
            throw new NullPointerException("Cannot send message to yourself!");
        } else {
            messageDTO.setSenderName(user.getUserName());
            if (messageDTO.getMessageId() != 0) {
                Message message1 = messageRepository.findById(messageDTO.getMessageId());
                if (message1.getMessageType().equals(MessageType.Normal)) {
                    User user1 = userRepository.findByUserName(messageDTO.getReceiverName());
                    if (user1 != null) {
                        Message message = new Message("Reply: " + message1.getTitle(), messageDTO.getContent(), "NEW", user.getUserName(),
                                MessageType.Normal, messageDTO.getReceiverName());
                        if (message1.getMessages().isEmpty()) {
                            message.setMessageType(MessageType.Inbox);
                            message.setParentId(message1.getId());
                            message.setLastUpdated(new Date());
                            message1.setLastUpdated(new Date());
                            messageRepository.save(message1);
                        } else {
                            if(!message1.getSenderName().equals(user.getUserName())){
                                Message message2 = messageRepository.findByMessageTypeAndMessageId(MessageType.Inbox, messageDTO.getMessageId());
//                                if(message2 != null){
                                message2.setStatus("NEW");
                                message2.setLastUpdated(new Date());
                                messageRepository.save(message2);
                                message.setParentId(message2.getId());
                                messageRepository.save(message);
                            }
                            message.setMessageType(MessageType.Reply);
                            message.setStatus(null);

                        }
                        message.setMessage(message1);
                        if(!message1.getReceiverName().equals(user.getUserName())){
                            message1.setStatus("NEW");
                            message1.setLastUpdated(new Date());
                            messageRepository.save(message1);
                            message.setParentId(message1.getId());
                            messageRepository.save(message);
                        }
                        messageRepository.save(message);
                        message.setUser(user1);
                        if(messageDTO.getFileType() != null && messageDTO.getAttachFile() != null && messageDTO.getFileName() != null){
                            message.setAttachFileAdd(attachFileMessage(messageDTO));
                            messageRepository.save(message);
                        }
                        return messageRepository.save(message);
                    } else {
                        throw new NullPointerException("User not found!");
                    }
                } else {
                    throw new NullPointerException("Cannot reply this message!");
                }
            } else {
                User user1 = userRepository.findByUserName(messageDTO.getReceiverName());
                if (user1 != null) {
                    Message message = new Message(messageDTO.getTitle(), messageDTO.getContent(), "NEW", user.getUserName(),
                            MessageType.Normal, messageDTO.getReceiverName());
                    message.setUser(user1);
                    if(messageDTO.getFileType() != null && messageDTO.getAttachFile() != null && messageDTO.getFileName() != null){
                        message.setAttachFileAdd(attachFileMessage(messageDTO));
                        messageRepository.save(message);
                    }
                    message.setLastUpdated(new Date());
                    if(user.getRole().equals(String.valueOf(Role.ADMIN))) {
                        String email;
                        if (user1.getRole().equals(String.valueOf(Role.STUDENT))) {
                            email = user1.getStudent().getEmail();
                        } else if (user1.getRole().equals(String.valueOf(Role.LECTURERS))) {
                            email = user1.getLecturers().getEmail();
                        } else {
                            email = user1.getPartner().getEmail();
                        }
//                        ApplicationContext context =
//                                new ClassPathXmlApplicationContext("Spring-Mail.xml");
//                        SendMail mm = (SendMail) context.getBean("sendMail");
//
//                        mm.sendMail("carbc@vnu.edu.vn",
//                                email,
//                                messageDTO.getTitle(),
//                                messageDTO.getContent());
                    }
                    return messageRepository.save(message);
                } else {
                    throw new NullPointerException("User not found!");
                }
            }
        }
    }

    public Message getOneMessage(int messageId, String token) {
        User user = userRepository.findByToken(token);
        Message message = messageRepository.findById(messageId);
        if (message.getUser().equals(user)) {
            if (message.getMessageType().equals(MessageType.Reply) || message.getMessageType().equals(MessageType.Inbox)) {
                return message.getMessage();
            } else {
                return message;
            }
        } else {
            throw new NullPointerException("User not match!");
        }
    }

    public Message getParentMessage(int messageId, String token) {
        User user = userRepository.findByToken(token);
        Message message = messageRepository.findById(messageId);
        if (message.getUser().equals(user)) {
            return message.getMessage();
        } else {
            throw new NullPointerException("User not match!");
        }
    }

    public void createNotificationMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setMessageType(MessageType.Notification);
        messageRepository.save(message);
    }

    public List<Message> getNotificationMessage() {
        return messageRepository.findByMessageType(MessageType.Notification);
    }

    public void deleteNotificationMessage(int messageId) {
        Message message = messageRepository.findByIdAndMessageType(messageId, MessageType.Notification);
        if (message != null) {
            messageRepository.delete(message);
        } else {
            throw new NullPointerException("Message not found!");
        }
    }

}
