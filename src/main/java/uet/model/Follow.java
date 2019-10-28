package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by nhkha on 16/02/2017.
 */
@Entity
@Table(name="Follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private int id;

    @ManyToOne
    @JoinColumn(name = "internship")
    @JsonIgnore
    private Internship internship ;

    @ManyToOne
    @JoinColumn(name = "student")
    private Student student ;

    @ManyToOne
    @JoinColumn(name="post")
    @JsonIgnore
    private Post post;

    @ManyToOne
    @JoinColumn(name="partner")
    @JsonIgnore
    private Partner partner;

    private int postId;
    @Column(name="postTitle", length = 2800000)
    private String postTitle;
    private String studentName;
    private int partnerId;
    private String partnerName;
    private String status;
    private String lecturersName;
    private int internshipTerm;
    private Date createdAt;

    public Follow(){
        this.createdAt = new Date(System.currentTimeMillis() + 1000 * 60);
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLecturersName() {
        return lecturersName;
    }

    public void setLecturersName(String lecturersName) {
        this.lecturersName = lecturersName;
    }

    public int getInternshipTerm() {
        return internshipTerm;
    }

    public void setInternshipTerm(int internshipTerm) {
        this.internshipTerm = internshipTerm;
    }

    public Internship getInternship() {
        return internship;
    }

    public void setInternship(Internship internship) {
        this.internship = internship;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
