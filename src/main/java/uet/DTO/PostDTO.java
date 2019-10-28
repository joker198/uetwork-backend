package uet.DTO;

import uet.model.Partner;
import uet.model.PartnerContact;
import uet.model.PostType;

import java.util.Date;
import java.util.List;

/**
 * Created by Trung on 8/29/2016.
 */
public class PostDTO {
    private List<HashtagDTO> hashtagDTO;
    private int id;
    private String content;
    private Date datePost;
    private String partnerName;
    private String describePost;
    private String image;
    private String status;
    private Integer requiredNumber;
    private Integer partnerContactId;
    private Date expiryTime; // ngay het han cua bai dang
    private Date startDate; // ngay bat dau thuc tap
    //    private Date endDate; //ngay ket thuc
    private String durationTime; // thoi gian dien ra cua dot thuc tap
    private String title;
    private PartnerContactDTO partnerContactDTO;
    private PostType postType;
    private Partner partner;

    public List<HashtagDTO> getHashtagDTO() {
        return hashtagDTO;
    }

    public void setHashtagDTO(List<HashtagDTO> hashtagDTO) {
        this.hashtagDTO = hashtagDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public Date getDatePost() { return datePost; }

    public void setDatePost(Date datePost) {
        this.datePost = datePost;
    }

    public String getDescribePost() { return describePost; }

    public void setDescribePost(String describePost) { this.describePost = describePost; }

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

    public Integer getPartnerContactId() {
        return partnerContactId;
    }

    public void setPartnerContactId(Integer partnerContactId) {
        this.partnerContactId = partnerContactId;
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

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public PartnerContactDTO getPartnerContactDTO() {
        return partnerContactDTO;
    }

    public void setPartnerContactDTO(PartnerContactDTO partnerContactDTO) {
        this.partnerContactDTO = partnerContactDTO;
    }
}
