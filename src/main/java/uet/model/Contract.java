package uet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by nhkha on 25/03/2017.
 */
@Entity
@Table(name = "contract")
public class Contract
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String result;
    private Boolean renew;
    @Column(name = "notice", length  = 2800000)
    private String notice;
    private String funding;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;
    private int ordinaryNumber;
    private int number;
    private String attachFileAdd;
    private java.util.Date createdAt;
    private String uetManName;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @ManyToOne
    @JoinColumn(name = "contact_point")
    private PartnerContact contactPoint;

    @ManyToOne
    @JoinColumn(name = "partner_contact_id")
    private PartnerContact partnerContact;

    @ManyToMany
    @JoinTable(name = "contract_uet_man", joinColumns = @JoinColumn(name = "contract_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "uet_man_id", referencedColumnName = "id"))
    private List<UetMan> uetMan;

    @Column(name = "contentContract", length  = 2800000)
    private String contentContract;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private List<CooperateActivity> cooperateActivity;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private List<ContractShare> contractShares;


    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private List<ContractShareVnu> contractShareVnus;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private List<Notice> notices;

    @ManyToOne
    @JoinColumn(name="roles_signing_level_id")
    private RolesAndSigningLevel rolesSigningLevel;

    public Contract()
    {
        //
    }

    public Contract(
        Partner partner,
        PartnerContact partnerContact,
        List<UetMan> uetMans,
        RolesAndSigningLevel rolesSigningLevel
    ) {
        this.partner = partner;
        this.partnerContact = partnerContact;
        this.uetMan = uetMans;
        this.rolesSigningLevel = rolesSigningLevel;
    }


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public Boolean getRenew()
    {
        return renew;
    }

    public void setRenew(Boolean renew)
    {
        this.renew = renew;
    }

    public String getNotice()
    {
        return notice;
    }

    public void setNotice(String notice)
    {
        this.notice = notice;
    }

    public String getFunding()
    {
        return funding;
    }

    public void setFunding(String funding)
    {
        this.funding = funding;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public int getOrdinaryNumber()
    {
        return ordinaryNumber;
    }

    public void setOrdinaryNumber(int ordinaryNumber)
    {
        this.ordinaryNumber = ordinaryNumber;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }


    public Partner getPartner()
    {
        return partner;
    }

    public void setPartner(Partner partner)
    {
        this.partner = partner;
    }

    public List<UetMan> getUetMan()
    {
        return uetMan;
    }

    public void setUetMan(List<UetMan> uetMans)
    {
        this.uetMan = uetMans;
    }

    public String getContentContract()
    {
        return contentContract;
    }

    public void setContentContract(String contentContract)
    {
        this.contentContract = contentContract;
    }


    public PartnerContact getPartnerContact()
    {
        return partnerContact;
    }

    public void setPartnerContact(PartnerContact partnerContact)
    {
        this.partnerContact = partnerContact;
    }


    public List<CooperateActivity> getCooperateActivity()
    {
        return cooperateActivity;
    }

    public void setCooperateActivity(List<CooperateActivity> cooperateActivity)
    {
        this.cooperateActivity = cooperateActivity;
    }

    public String getAttachFileAdd()
    {
        return attachFileAdd;
    }

    public void setAttachFileAdd(String attachFileAdd)
    {
        this.attachFileAdd = attachFileAdd;
    }

    public java.util.Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public List<ContractShare> getContractShares()
    {
        return contractShares;
    }

    public void setContractShares(List<ContractShare> contractShares)
    {
        this.contractShares = contractShares;
    }

    public String getUetManName()
    {
        return uetManName;
    }

    public void setUetManName(String uetManName)
    {
        this.uetManName = uetManName;
    }

    public List<Notice> getNotices()
    {
        return notices;
    }

    public void setNotices(List<Notice> notices)
    {
        this.notices = notices;
    }

    public RolesAndSigningLevel getRolesAndSigningLevel()
    {
        return rolesSigningLevel;
    }

    public void setRolesAndSigningLevel(RolesAndSigningLevel rolesSigningLevel)
    {
        this.rolesSigningLevel = rolesSigningLevel;
    }

    public PartnerContact getContactPoint()
    {
        return contactPoint;
    }

    public void setContactPoint(PartnerContact contactPoint)
    {
        this.contactPoint = contactPoint;
    }

    public List<ContractShareVnu> getContractShareVnus()
    {
        return contractShareVnus;
    }

    public void setContractShareVnus(List<ContractShareVnu> contractShareVnus)
    {
        this.contractShareVnus = contractShareVnus;
    }
}
