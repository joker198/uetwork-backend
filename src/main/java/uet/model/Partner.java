package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tu on 03-May-16.
 */
@Entity
@Table(name="Partner")
public class Partner implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String partnerName;
    private String taxCode;
    private String director;
    private String fieldWork;
    private String website;
    private String address;
    private String phone;
    private String fax;
    private String email;
    private String logo;
    private Double averageRating;
    private Integer totalRating;
    private String description;
    private int status;
    private Date birthday;
    private String partnerType;

    @ManyToOne
    @JoinColumn(name = "nation_id")
    private Nation nation;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Contract> contracts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> post;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Internship> internships;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    private List<PartnerContact> partnerContacts;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Follow> follows;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<Comment>();

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PassInterview> passInterviews;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PartnerInternshipterm> partnerInternshipterms;

    public Partner(){}

    public Partner(Nation nation) {
        this.nation = nation;
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

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getFieldWork() {
        return fieldWork;
    }

    public void setFieldWork(String fieldWork) {
        this.fieldWork = fieldWork;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Integer totalRating) {
        this.totalRating = totalRating;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

    public List<Internship> getInternships() {
        return internships;
    }

    public void setInternships(List<Internship> internships) {
        this.internships = internships;
    }

    public List<PartnerContact> getPartnerContacts() {
        return partnerContacts;
    }

    public void setPartnerContacts(List<PartnerContact> partnerContacts) {
        this.partnerContacts = partnerContacts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<PassInterview> getPassInterviews() {
        return passInterviews;
    }

    public void setPassInterviews(List<PassInterview> passInterviews) {
        this.passInterviews = passInterviews;
    }

    public List<Follow> getFollows() {
        return follows;
    }

    public void setFollows(List<Follow> follows) {
        this.follows = follows;
    }

    public Nation getNation() {
        return nation;
    }

    public void setNation(Nation nation) {
        this.nation = nation;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public List<PartnerInternshipterm> getPartnerInternshipterms()
    {
        return this.partnerInternshipterms;
    }

}



