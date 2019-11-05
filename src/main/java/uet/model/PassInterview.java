package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by nhkha on 17/05/2017.
 */
@Entity
@Table(name = "passInterview")
public class PassInterview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String comfirmationLink;
    @ManyToOne
    @JoinColumn(name="partner_id")
    private Partner partner;
    
    @ManyToOne
    @JoinColumn(name="student_id")
    @JsonIgnore
    private Student student;

    public PassInterview(){}

    public PassInterview(Partner partner, Student student, String comfirmationLink)
    {
        this.partner = partner;
        this.student = student;
        this.comfirmationLink = comfirmationLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getComfirmationLink() {
        return comfirmationLink;
    }

    public void setComfirmationLink(String comfirmationLink) {
        this.comfirmationLink = comfirmationLink;
    }
}
