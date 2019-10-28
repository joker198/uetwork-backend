package uet.DTO;

import uet.model.Partner;
import uet.model.Role;

import java.util.Date;

/**
 * Created by Tu on 05-Jul-16.
 */
public class UserDTO {
    private int id;
    private String userName;
    private String password;
    private String role;
    private String token;
    private Date expiryTime;
    private String status;
    private int facultyId;
    private String subject;
    private String phoneNumber;
    private String emailvnu;
    private String fullName;
    private Partner partner;
    private int studentCode;
    private String rolesAndSigningLevel;
    private String universityName;

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

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailvnu() {
        return emailvnu;
    }

    public void setEmailvnu(String emailvnu) {
        this.emailvnu = emailvnu;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public int getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(int studentCode) {
        this.studentCode = studentCode;
    }

    public String getRolesAndSigningLevel() {
        return rolesAndSigningLevel;
    }

    public void setRolesAndSigningLevel(String rolesAndSigningLevel) {
        this.rolesAndSigningLevel = rolesAndSigningLevel;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

//    public boolean isStatus() {
//        return status;
//    }
//
//    public void setStatus(boolean status) {
//        this.status = status;
//    }
}