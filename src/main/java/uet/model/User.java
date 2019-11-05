package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Tu on 02-May-16.
 */
@Entity
@Table(name="User")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userName;

    @JsonIgnore
    private String password;

    private String role;

    private String token;

    private Date expiryTime;

    private String status;

    private Date lastLogin;

    private Date createdAt;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private Student student;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private Partner partner;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private Lecturers Lecturers;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Message> messages;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private UnitName unitName;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private RolesAndSigningLevel rolesAndSigningLevel;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ActivityLog> activityLog;

    public User()
    {
        this.createdAt = new Date(System.currentTimeMillis() + 1000 * 60);
    }

    public User(UnitName unitName)
    {
        this.unitName = unitName;
        this.createdAt = new Date(System.currentTimeMillis() + 1000 * 60);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public uet.model.Lecturers getLecturers() {
        return Lecturers;
    }

    public void setLecturers(uet.model.Lecturers lecturers) {
        Lecturers = lecturers;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public UnitName getUnitName() {
        return unitName;
    }

    public void setUnitName(UnitName unitName) {
        this.unitName = unitName;
    }

    public List<ActivityLog> getActivityLog() {
        return activityLog;
    }

    public void setActivityLog(List<ActivityLog> activityLog) {
        this.activityLog = activityLog;
    }

    public RolesAndSigningLevel getRolesAndSigningLevel() {
        return rolesAndSigningLevel;
    }

    public void setRolesAndSigningLevel(RolesAndSigningLevel rolesAndSigningLevel) {
        this.rolesAndSigningLevel = rolesAndSigningLevel;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
