package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgv on 8/30/2016.
 */
@Entity
@Table(name="partnerContact")
public class PartnerContact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="partner_id")
    @JsonIgnore
    private Partner partner;

    @OneToMany(mappedBy = "contactPoint")
    @JsonIgnore
    private List<Contract> contract;


    @OneToMany(mappedBy = "partnerContact")
    @JsonIgnore
    private List<Contract> contractL;

    @OneToMany(mappedBy = "partnerContact")
    @JsonIgnore
    private List<Post> post;

    private String contactName;
    private String address;
    private String skype;
    private String email;
    private String phone;
    private String about;
    private String avatar;

    public PartnerContact(){}

    public PartnerContact(
        String contactName,
        String phone,
        String email,
        String skype,
        String about,
        Partner partner
    ) {
        this.contactName = contactName;
        this.phone = phone;
        this.email = email;
        this.skype = skype;
        this.about = about;
        this.partner = partner;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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


    public void setPosts(List<Post> post) {
        this.post = post;
    }

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

    public List<Contract> getContract() {
        return contract;
    }

    public void setContract(List<Contract> contract) {
        this.contract = contract;
    }

    public List<Contract> getContractL() {
        return contractL;
    }

    public void setContractL(List<Contract> contractL) {
        this.contractL = contractL;
    }
}
