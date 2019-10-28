package uet.DTO;

import uet.model.InfoBySchool;
import uet.model.Internship;
import uet.model.Student;

import java.util.List;

/**
 * Created by Trung on 7/11/2016.
 */
public class StudentDTO {
    private InfoBySchoolDTO infoBySchoolDTO1;
    private JobSkillDTO jobSkillDTO;
    private InfoBySchoolDTO infoBySchoolDTO;
    private int id;
    private String fullName;
    private String birthday;
    private String phoneNumber;
    private String address;
    private String skype;
    private String email;
    private String desire;
    private String avatar;
    private String lecturersEmail;
    private String STT;
    private int lecturersId;
    private Float score;
    private int internId;
    private String message;
    private int studentCode;
    private String courseClass;
    private String lecturersName;
    private Boolean check;
    private List<Internship> internshipList;
    private InfoBySchool infoBySchool;
    private Student student;

    public InfoBySchoolDTO getInfoBySchoolDTO1() {
        return infoBySchoolDTO1;
    }

    public void setInfoBySchoolDTO1(InfoBySchoolDTO infoBySchoolDTO1) {
        this.infoBySchoolDTO1 = infoBySchoolDTO1;
    }

    public JobSkillDTO getJobSkillDTO() {
        return jobSkillDTO;
    }

    public void setJobSkillDTO(JobSkillDTO jobSkillDTO) {
        this.jobSkillDTO = jobSkillDTO;
    }

    public InfoBySchoolDTO getInfoBySchoolDTO() {
        return infoBySchoolDTO;
    }

    public void setInfoBySchoolDTO(InfoBySchoolDTO infoBySchoolDTO) {
        this.infoBySchoolDTO = infoBySchoolDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getDesire() {
        return desire;
    }

    public void setDesire(String desire) {
        this.desire = desire;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLecturersEmail() {
        return lecturersEmail;
    }

    public void setLecturersEmail(String lecturersEmail) {
        this.lecturersEmail = lecturersEmail;
    }

    public String getSTT() {
        return STT;
    }

    public void setSTT(String STT) {
        this.STT = STT;
    }

    public int getLecturersId() {
        return lecturersId;
    }

    public void setLecturersId(int lecturersId) {
        this.lecturersId = lecturersId;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public int getInternId() {
        return internId;
    }

    public void setInternId(int internId) {
        this.internId = internId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(int studentCode) {
        this.studentCode = studentCode;
    }

    public String getCourseClass() {
        return courseClass;
    }

    public void setCourseClass(String courseClass) {
        this.courseClass = courseClass;
    }

    public String getLecturersName() {
        return lecturersName;
    }

    public void setLecturersName(String lecturersName) {
        this.lecturersName = lecturersName;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public List<Internship> getInternshipList() {
        return internshipList;
    }

    public void setInternshipList(List<Internship> internshipList) {
        this.internshipList = internshipList;
    }

    public InfoBySchool getInfoBySchool() {
        return infoBySchool;
    }

    public void setInfoBySchool(InfoBySchool infoBySchool) {
        this.infoBySchool = infoBySchool;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
