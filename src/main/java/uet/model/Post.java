package uet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Trung on 8/27/2016.
 */
@Entity
@Table(name="Post")
public class Post {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="internship_term_id")
    @JsonIgnore
    private InternshipTerm internshipTerm;

    @ManyToOne
    @JoinColumn(name="partner_contact_id")
    private PartnerContact partnerContact;

    @ManyToOne
    @JoinColumn(name="partner")
    @JsonIgnore
    private Partner partner;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Follow> follows = new ArrayList<Follow>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="post_hashtag",
            joinColumns = @JoinColumn(name="post_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="hashtag_id", referencedColumnName = "id"))
    private List<Hashtag> hashtags = new ArrayList<Hashtag>();

    private String title;
    @Column(name="content", length = 2800000)
    private String content;
    @Column(name="describePost", length = 2800000)
    private String describePost;
    private String image;
    private String status;
    private Integer requiredNumber;
    private Date expiryTime; // ngay het han cua bai dang
    private Date startDate; // ngay bat dau thuc tap
    private String durationTime; // thoi gian dien ra cua dot thuc tap
    private String partnerName;
    @Enumerated(EnumType.STRING)
    private PostType postType;

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
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

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    private Date datePost;

    public Date getDatePost() {
        return datePost;
    }

    public void setDatePost(Date datePost) {
        this.datePost = datePost;
    }

    public String getDescribePost() {
        return describePost;
    }

    public void setDescribePost(String describePost)
    {
        this.describePost = describePost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) { this.image = image; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRequiredNumber() {
        return requiredNumber;
    }

    public void setRequiredNumber(Integer requiredNumber) {
        this.requiredNumber = requiredNumber;
    }

    public List<Follow> getFollows() {
        return follows;
    }

    public void setFollows(List<Follow> follows) {
        this.follows = follows;
    }

    public PartnerContact getPartnerContact() {
        return partnerContact;
    }

    public void setPartnerContact(PartnerContact partnerContact) {
        this.partnerContact = partnerContact;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InternshipTerm getInternshipTerm() {
        return internshipTerm;
    }

    public void setInternshipTerm(InternshipTerm internshipTerm) {
        this.internshipTerm = internshipTerm;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }
}
