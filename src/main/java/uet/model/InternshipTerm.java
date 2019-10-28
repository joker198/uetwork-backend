package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhkha on 14/05/2017.
 */
@Entity
@Table(name="internshipTerm")
public class InternshipTerm {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(mappedBy = "internshipTerm")
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(mappedBy = "internshipTerm")
    @JsonIgnore
    private List<Internship> internships;

    @OneToMany(mappedBy = "internshipTerm")
    @JsonIgnore
    private List<Student> students;

    private String year;
    private int term;
    private String startDate;
    private String endDate;
    private int postCount;
    private int internshipCount;
    private boolean notiLecturers;
    private boolean notiScore;
    private String expiredDate;

    public InternshipTerm(){}

    public InternshipTerm(String year, int term, int postCount, int internshipCount, String startDate, String endDate, String expiredDate){
        this.year = year;
        this.term = term;
        this.postCount = postCount;
        this.internshipCount = internshipCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notiLecturers = false;
        this.notiScore = false;
        this.expiredDate = expiredDate;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

//    public List<Internship> getInternships() {
//        return internships;
//    }
//
//    public void setInternships(List<Internship> internships) {
//        this.internships = internships;
//    }

    public int getInternshipCount() {
        return internshipCount;
    }

    public void setInternshipCount(int internshipCount) {
        this.internshipCount = internshipCount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Internship> getInternships() {
        return internships;
    }

    public void setInternships(List<Internship> internships) {
        this.internships = internships;
    }

    public boolean isNotiLecturers() {
        return notiLecturers;
    }

    public void setNotiLecturers(boolean notiLecturers) {
        this.notiLecturers = notiLecturers;
    }

    public boolean isNotiScore() {
        return notiScore;
    }

    public void setNotiScore(boolean notiScore) {
        this.notiScore = notiScore;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }
}
