package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by nhkha on 17/05/2017.
 */
@Entity
@Table(name="Message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private int id;

    private String title;
    @Column(name="content", length = 2800000)
    private String content;
    private String senderName;
    private Date sendDate;
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
    private String status;
    private String receiverName;
    private String attachFileAdd;
    private String fileName;
    private Integer parentId;
    private Date lastUpdated;

    @ManyToOne
    @JoinColumn(name="message_id")
    @JsonIgnore
    private Message message;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
//    @JsonIgnore
    private List<Message> messages;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    public Message(){

    }

    public Message (String title, String content, String status, String senderName, MessageType messageType, String receiverName){
        this.content = content;
        this.title = title;
        this.status = status;
        this.sendDate = new Date();
        this.senderName = senderName;
        this.messageType = messageType;
        this.receiverName = receiverName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getAttachFileAdd() {
        return attachFileAdd;
    }

    public void setAttachFileAdd(String attachFileAdd) {
        this.attachFileAdd = attachFileAdd;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
