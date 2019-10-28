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

    //create pass interview message to student
    @RequestMapping(value = "message/passInterview/create", method = RequestMethod.POST)
    public void createMessage(@RequestBody List<MessageDTO> listMessageDTO, HttpServletRequest request){
        String token= request.getHeader("auth-token");
        messageService.createPassInterviewMessage(listMessageDTO, token);
    }

    //fail interview message
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "message/failInterview/create", method = RequestMethod.POST)
    public void createFailInterview(@RequestBody List<MessageDTO> listMessageDTO, HttpServletRequest request){
        String token= request.getHeader("auth-token");
        messageService.createFailInterview(listMessageDTO, token);
    }
    // create message
    @RequestMapping(value = "message/create", method = RequestMethod.POST)
    public Message writeMessage(@RequestBody MessageDTO messageDTO, HttpServletRequest request) throws IOException {
        String token= request.getHeader("auth-token");
        Message message = messageService.writeMessage(messageDTO, token);
        User user = userRepository.findByUserName(message.getReceiverName());
        int userId = user.getId();
//        message.getMessages().add(message.getMessage());
        simpMessagingTemplate.convertAndSend("/user/" + message.getReceiverName() + "/**", message);
        return message;
    }

    //get message
    @RequestMapping(value = "message", method = RequestMethod.GET)
    public Page<Message> getMessage(HttpServletRequest request, Pageable pageable, @RequestParam(value = "lastUpdated",
            required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date lastUpdated){
        String token= request.getHeader("auth-token");
        return messageService.getMessage(token, pageable, lastUpdated);
    }

    //get message from sender
//    @RequestMapping(value = "message/sender/")

    @RequestMapping(value = "message/parent/{messageId}", method = RequestMethod.GET)
    public Message getParentMessage(@PathVariable("messageId") int messageId, HttpServletRequest request){
        String token= request.getHeader("auth-token");
        return messageService.getParentMessage(messageId, token);
    }

    //get one message
    @RequestMapping(value = "message/messageId/{messageId}", method = RequestMethod.GET)
    public Message getOneMessage(@PathVariable("messageId") int messageId, HttpServletRequest request){
        String token= request.getHeader("auth-token");
        return messageService.getOneMessage(messageId, token);
    }

    //get new Message
    @RequestMapping(value = "message/new", method = RequestMethod.GET)
    public List<Message> getNewMessage(HttpServletRequest request, Pageable pageable){
        String token= request.getHeader("auth-token");
        return messageService.getNewMessage(token, pageable);
    }

    //mark message as seen
    @RequestMapping(value = "message/{messageId}/seen", method = RequestMethod.PUT)
    public void markMessageAsSeen(@PathVariable("messageId") int messageId, HttpServletRequest request){
        String token= request.getHeader("auth-token");
        messageService.markMessageAsSeen(messageId, token);
    }

    //get meeage by type
//    @RequestMapping(value = "message/{messageType}", method = RequestMethod.GET)
//    public List<Message> getPassInterviewMessage(HttpServletRequest request,
//                                                 @PathVariable("messageType")MessageType messageType){
//        String token= request.getHeader("auth-token");
//        return messageService.getPassInterviewMessage(token, messageType);
//    }

    //create message for all student
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "message/notification/create", method = RequestMethod.POST)
    public void createNotificationMessage(@RequestBody MessageDTO messageDTO){
        messageService.createNotificationMessage(messageDTO);
    }

    //get notification message
    @NoAuthentication
    @RequestMapping(value = "message/notification", method = RequestMethod.GET)
    public List<Message> getNotificationMessage(){
        return messageService.getNotificationMessage();
    }

    //delete notification message
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "message/notification/delete/{messageId}", method = RequestMethod.DELETE)
    public void deleteNotificationMessage(@PathVariable("messageId") int messageId){
        messageService.deleteNotificationMessage(messageId);
    }

    //get

}
