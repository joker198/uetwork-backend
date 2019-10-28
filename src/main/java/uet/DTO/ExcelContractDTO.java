package uet.DTO;

import uet.model.UetMan;
import uet.model.UnitName;

import java.sql.Date;
import java.util.List;

/**
 * Created by nhkha on 10/04/2017.
 */
public class ExcelContractDTO {
    private int id;
    private String result;
    private String renew;
    private String notice;
    private String funding;
    private Date startDate;
    private Date endDate;
    private int ordinaryNumber;
    private int number;
    private String contentContract;
    private int partnerContactId;
    private int partnerId;
    private int uetManId;
    private int typeContractId;
    private int unitNameId;
    private int STT;
    private String attachFileAdd;
    private String attachFile;
    private String fileType;
    private String fileName;
    private List<Integer> listUnitNameId;
    private List<UetMan> uetManList;
    private List<UnitName> unitNames;
    private String roleAndSigningLevel;
    private int roleAndSigningLevelId;
    private int contactPointId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRenew() {
        return renew;
    }

    public void setRenew(String renew) {
        this.renew = renew;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getFunding() {
        return funding;
    }

    public void setFunding(String funding) {
        this.funding = funding;
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

    public int getOrdinaryNumber() {
        return ordinaryNumber;
    }

    public void setOrdinaryNumber(int ordinaryNumber) {
        this.ordinaryNumber = ordinaryNumber;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getContentContract() {
        return contentContract;
    }

    public void setContentContract(String contentContract) {
        this.contentContract = contentContract;
    }

    public int getPartnerContactId() {
        return partnerContactId;
    }

    public void setPartnerContactId(int partnerContactId) {
        this.partnerContactId = partnerContactId;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public int getUetManId() {
        return uetManId;
    }

    public void setUetManId(int uetManId) {
        this.uetManId = uetManId;
    }

    public int getTypeContractId() {
        return typeContractId;
    }

    public void setTypeContractId(int typeContractId) {
        this.typeContractId = typeContractId;
    }

    public int getUnitNameId() {
        return unitNameId;
    }

    public void setUnitNameId(int unitNameId) {
        this.unitNameId = unitNameId;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public String getAttachFileAdd() {
        return attachFileAdd;
    }

    public void setAttachFileAdd(String attachFileAdd) {
        this.attachFileAdd = attachFileAdd;
    }

    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<Integer> getListUnitNameId() {
        return listUnitNameId;
    }

    public void setListUnitNameId(List<Integer> listUnitNameId) {
        this.listUnitNameId = listUnitNameId;
    }

    public List<UetMan> getUetManList() {
        return uetManList;
    }

    public void setUetManList(List<UetMan> uetManList) {
        this.uetManList = uetManList;
    }

    public List<UnitName> getUnitNames() {
        return unitNames;
    }

    public void setUnitNames(List<UnitName> unitNames) {
        this.unitNames = unitNames;
    }

    public String getRoleAndSigningLevel() {
        return roleAndSigningLevel;
    }

    public void setRoleAndSigningLevel(String roleAndSigningLevel) {
        this.roleAndSigningLevel = roleAndSigningLevel;
    }

    public int getRoleAndSigningLevelId() {
        return roleAndSigningLevelId;
    }

    public void setRoleAndSigningLevelId(int roleAndSigningLevelId) {
        this.roleAndSigningLevelId = roleAndSigningLevelId;
    }

    public int getContactPointId() {
        return contactPointId;
    }

    public void setContactPointId(int contactPointId) {
        this.contactPointId = contactPointId;
    }
}
