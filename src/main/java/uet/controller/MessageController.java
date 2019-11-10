package uet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import uet.DTO.MessageDTO;
import uet.model.Message;
import uet.model.MessageType;
import uet.model.Role;
import uet.model.User;
import uet.repository.UserRepository;
import uet.service.MessageService;
import uet.stereotype.NoAuthentication;
import uet.stereotype.RequiredRoles;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by nhkha on 17/05/2017.
 */
@RestController
public class MessageController {
    private final  MessageService messageService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserRepository userRepository;

    @Autowired
    public MessageController(MessageService messageService, SimpMessagingTemplate simpMessagingTemplate, UserRepository userRepository) {
        this.messageService = messageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userRepository = userRepository;
    }

    /**
     * Create Pass Interview Message To Student
     *
     * @param listMessageDTO
     * @param request 
     */
    @RequestMapping(value = "message/passInterview/create", method = RequestMethod.POST)
    public void createMessage(@RequestBody List<MessageDTO> listMessageDTO, HttpServletRequest request){
        String token= request.getHeader("auth-token");
        messageService.createPassInterviewMessage(listMessageDTO, token);
    }

    /**
     * Fail Interview Message
     *
     * @param listMessageDTO
     * @param request 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "message/failInterview/create", method = RequestMethod.POST)
    public void createFailInterview(@RequestBody List<MessageDTO> listMessageDTO, HttpServletRequest request){
        String token= request.getHeader("auth-token");
        messageService.createFailInterview(listMessageDTO, token);
    }

    /**
     * Create Message
     *
     * @param messageDTO
     * @param request
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "message/create", method = RequestMethod.POST)
    public Message writeMessage(@RequestBody MessageDTO messageDTO, HttpServletRequest request) throws IOException {
        String token= request.getHeader("auth-token");
        Message message = messageService.writeMessage(messageDTO, token);
        User user = userRepository.findByUserName(message.getReceiverName());
        int userId = user.getId();
        simpMessagingTemplate.convertAndSend("/user/" + message.getReceiverName() + "/**", message);
        return message;
    }

    /**
     * Get Message
     *
     * @param request
     * @param pageable
     * @param lastUpdated
     * @return 
     */
    @RequestMapping(value = "message", method = RequestMethod.GET)
    public Page<Message> getMessage(HttpServletRequest request, Pageable pageable, @RequestParam(value = "lastUpdated",
            required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date lastUpdated){
        String token= request.getHeader("auth-token");
        return messageService.getMessage(token, pageable, lastUpdated);
    }

    /**
     * Get Parent Message
     *
     * @param messageId
     * @param request
     * @return 
     */
    @RequestMapping(value = "message/parent/{messageId}", method = RequestMethod.GET)
    public Message getParentMessage(@PathVariable("messageId") int messageId, HttpServletRequest request){
        String token= request.getHeader("auth-token");
        return messageService.getParentMessage(messageId, token);
    }

    /**
     * Get One Message
     *
     * @param messageId
     * @param request
     * @return 
     */
    @RequestMapping(value = "message/messageId/{messageId}", method = RequestMethod.GET)
    public Message getOneMessage(@PathVariable("messageId") int messageId, HttpServletRequest request){
        String token= request.getHeader("auth-token");
        return messageService.getOneMessage(messageId, token);
    }

    /**
     * Get New Message
     *
     * @param request
     * @param pageable
     * @return 
     */
    @RequestMapping(value = "message/new", method = RequestMethod.GET)
    public List<Message> getNewMessage(HttpServletRequest request, Pageable pageable){
        String token= request.getHeader("auth-token");
        return messageService.getNewMessage(token, pageable);
    }

    /**
     * Mark Message As Seen
     *
     * @param messageId
     * @param request 
     */
    @RequestMapping(value = "message/{messageId}/seen", method = RequestMethod.PUT)
    public void markMessageAsSeen(@PathVariable("messageId") int messageId, HttpServletRequest request){
        String token= request.getHeader("auth-token");
        messageService.markMessageAsSeen(messageId, token);
    }

    /**
     * Create Message For All Student
     *
     * @param messageDTO 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "message/notification/create", method = RequestMethod.POST)
    public void createNotificationMessage(@RequestBody MessageDTO messageDTO){
        messageService.createNotificationMessage(messageDTO);
    }

    /**
     * Get Notification Message
     *
     * @return 
     */
    @NoAuthentication
    @RequestMapping(value = "message/notification", method = RequestMethod.GET)
    public List<Message> getNotificationMessage(){
        return messageService.getNotificationMessage();
    }

    /**
     * Delete Notification Message
     *
     * @param messageId 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "message/notification/delete/{messageId}", method = RequestMethod.DELETE)
    public void deleteNotificationMessage(@PathVariable("messageId") int messageId){
        messageService.deleteNotificationMessage(messageId);
    }
}
