package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by nhkha on 14/05/2017.
 */
@Entity
@Table(name="Lecturers")
public class Lecturers {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String fullName;
    private String phoneNumber;
    private String emailVNU;
    private String email;
    private String about;
    private String avatar;
    private String subject; // bo mon

    public Lecturers(){
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "lecturers")
    @JsonIgnore
    private List<Internship> internships;

    @ManyToOne
    @JoinColumn(name="faculty_id")
    private Faculty faculty; //khoa

    @OneToMany(mappedBy = "lecturers")
    @JsonIgnore
    private List<Student> students;

    public Lecturers(Faculty faculty){
        this.faculty = faculty;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailVNU() {
        return emailVNU;
    }

    public void setEmailVNU(String emailVNU) {
        this.emailVNU = emailVNU;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<Internship> getInternships() {
        return internships;
    }

    public void setInternships(List<Internship> internships)
    {
        this.internships = internships;
    }
}
