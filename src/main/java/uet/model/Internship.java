package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by fgv on 7/11/2016.
 */
@Entity
@Table(name="Internship")
public class Internship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    //    private String company;
    private Date startDate;
    private Date endDate;
    private String internshipType;
    private String partnerName;
    private Float score;
    private String attachFileAdd;
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    @JsonIgnore
    private Partner partner;

    @ManyToOne
    @JoinColumn(name="lecturers_id")
    private Lecturers  lecturers;

    @ManyToOne
    @JoinColumn(name="internship_term_id")
    private InternshipTerm internshipTerm;

    @OneToMany(mappedBy = "internship", cascade = CascadeType.ALL)
    private List<Follow> follows;

    public Internship()
    {
        //
    }

    public Internship(
        Partner partner,
        Student student
    ) {
        this.partner = partner;
        this.student = student;
    }

    public Internship(
        InternshipTerm internshipTerm,
        Student student,
        Date date
    ) {
        this.internshipTerm = internshipTerm;
        this.student = student;
        this.createdAt = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getInternshipType() {
        return internshipType;
    }

    public void setInternshipType(String internshipType) {
        this.internshipType = internshipType;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getAttachFileAdd() {
        return attachFileAdd;
    }

    public void setAttachFileAdd(String attachFileAdd) {
        this.attachFileAdd = attachFileAdd;
    }

    public InternshipTerm getInternshipTerm() {
        return internshipTerm;
    }

    public void setInternshipTerm(InternshipTerm internshipTerm) {
        this.internshipTerm = internshipTerm;
    }

    public Lecturers getLecturers() {
        return lecturers;
    }

    public void setLecturers(Lecturers lecturers) {
        this.lecturers = lecturers;
    }

    public List<Follow> getFollows() {
        return follows;
    }

    public void setFollows(List<Follow> follows) {
        this.follows = follows;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
