package uet.DTO;

import uet.model.Partner;
import uet.model.PartnerContact;

import java.util.Date;

/**
 * Created by fgv on 7/11/2016.
 */
public class InternshipDTO {
    private MessageDTO messageDTO;
    private int id;
    private Partner partner;
    private PartnerContactDTO partnerContactDTO;
    private String company;
    private Date startDate;
    private Date  endDate;
    private String supervisor;
    private int studentId;
    private int studentCode;
    private String studentName;
    private String grade;
    private String studentClass;
    private String birthday;
    private int partnerId;
    private int lecturersId;
    private String emailVNU;
    private String internshipType;
    private String partnerName;
    private String attachFileAdd;
    private String attachFile;
    private String fileType;
    private String fileName;
    private float score;

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(int studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public int getLecturersId() {
        return lecturersId;
    }

    public void setLecturersId(int lecturersId) {
        this.lecturersId = lecturersId;
    }

    public String getEmailVNU() {
        return emailVNU;
    }

    public void setEmailVNU(String emailVNU) {
        this.emailVNU = emailVNU;
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

    public PartnerContactDTO getPartnerContactDTO() {
        return partnerContactDTO;
    }

    public void setPartnerContactDTO(PartnerContactDTO partnerContactDTO) {
        this.partnerContactDTO = partnerContactDTO;
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

    public MessageDTO getMessageDTO() {
        return messageDTO;
    }

    public void setMessageDTO(MessageDTO messageDTO) {
        this.messageDTO = messageDTO;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
