package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Trung on 7-8-2016.
 */

@Entity
@Table(name="Student")
public class Student {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String fullName;
    private String birthday;
    private String phoneNumber;
    private String address;
    private String skype;
    private String email;
    private String desire;
    private String avatar;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "user_id")
    @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private InfoBySchool infoBySchool;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Internship> internship;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Follow> follows;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private Comment comment;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<JobSkill> jobSkills;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PassInterview> passInterviews;

    @ManyToOne
    @JoinColumn(name="lecturers_id")
    private Lecturers lecturers;

    @ManyToOne
    @JoinColumn(name="internship_term_id")
    @JsonIgnore
    private InternshipTerm internshipTerm;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {    return id; }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesire() {
        return desire;
    }

    public void setDesire(String desire) {
        this.desire = desire;
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

    public InfoBySchool getInfoBySchool() {
        return infoBySchool;
    }

    public void setInfoBySchool(InfoBySchool infoBySchool) {
        this.infoBySchool = infoBySchool;
    }

    public List<JobSkill> getJobSkills()
    {
        return jobSkills;
    }

    public void setJobSkills(List<JobSkill> jobSkills)
    {
        this.jobSkills = jobSkills;
    }

    public void add(Student student)
    {
        //
    }

    public Lecturers getLecturers() {
        return lecturers;
    }

    public void setLecturers(Lecturers lecturers) {
        this.lecturers = lecturers;
    }

    public List<PassInterview> getPassInterviews() {
        return passInterviews;
    }

    public void setPassInterviews(List<PassInterview> passInterviews) {
        this.passInterviews = passInterviews;
    }

    public InternshipTerm getInternshipTerm() {
        return internshipTerm;
    }

    public void setInternshipTerm(InternshipTerm internshipTerm) {
        this.internshipTerm = internshipTerm;
    }

    public List<Internship> getInternship() {
        return internship;
    }

    public void setInternship(List<Internship> internship) {
        this.internship = internship;
    }

    public List<Follow> getFollows() {
        return follows;
    }

    public void setFollows(List<Follow> follows) {
        this.follows = follows;
    }
}
