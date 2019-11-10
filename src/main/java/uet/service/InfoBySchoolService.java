package uet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.DTO.InfoBySchoolDTO;
import uet.model.*;
import uet.repository.InfoBySchoolRepository;
import uet.repository.StudentClassRepository;
import uet.repository.StudentRepository;
import uet.repository.UserRepository;

/**
 * Created by Tu on 07-Jul-16.
 */
@Service
public class InfoBySchoolService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    private StudentClassRepository studentClassRepository;
    @Autowired
    private InfoBySchoolRepository infoBySchoolRepository;

    //create info
    public InfoBySchool createInfo(int studentId, InfoBySchoolDTO infoBySchoolDTO){
        Student student = studentRepository.findOne(studentId);
        InfoBySchool infoBySchool = new InfoBySchool();
        infoBySchool.setStudentName(infoBySchoolDTO.getStudentName());
        infoBySchool.setStudentCode(infoBySchoolDTO.getStudentCode());
        infoBySchool.setMajor(infoBySchoolDTO.getMajor());
        infoBySchool.setGPA(infoBySchoolDTO.getGPA());
        infoBySchool.setDiploma(infoBySchoolDTO.getDiploma());
        infoBySchool.setGrade(infoBySchoolDTO.getGrade());
        infoBySchool.setGraduationYear(infoBySchoolDTO.getGraduationYear());
        infoBySchool.setStudentClass(infoBySchoolDTO.getStudentClass());
        student.setInfoBySchool(infoBySchool);
        return infoBySchoolRepository.save(infoBySchool);
    }

    //show info of a student
    public InfoBySchool getInfo(int infoId, String token){
        return infoBySchoolRepository.findById(infoId);
    }

    //edit info of a student
    public InfoBySchool editInfo(int infoId, InfoBySchoolDTO infoBySchoolDTO) {
        InfoBySchool info = infoBySchoolRepository.findOne(infoId);
        if (infoBySchoolDTO.getStudentCode() != null) {
            info.setStudentCode(infoBySchoolDTO.getStudentCode());
        }
        if (infoBySchoolDTO.getStudentName() != null) {
            info.setStudentName(infoBySchoolDTO.getStudentName());
            Student student = info.getStudent();
            student.setFullName(infoBySchoolDTO.getStudentName());
            studentRepository.save(student);
        }
        if (infoBySchoolDTO.getMajor() != null) {
            info.setMajor(infoBySchoolDTO.getMajor());
        }
        if (infoBySchoolDTO.getGPA() != null) {
            info.setGPA(infoBySchoolDTO.getGPA());
        }
        if (infoBySchoolDTO.getDiploma() != null) {
            info.setDiploma(infoBySchoolDTO.getDiploma());
        }
        if (infoBySchoolDTO.getGrade() != null) {
            info.setGrade(infoBySchoolDTO.getGrade());
        }
        if (infoBySchoolDTO.getGraduationYear() != null) {
            info.setGraduationYear(infoBySchoolDTO.getGraduationYear());
        }
        if (infoBySchoolDTO.getStudentClass() != null) {
            info.setStudentClass(infoBySchoolDTO.getStudentClass());
        }
        return infoBySchoolRepository.save(info);
    }

    //delete info of a student
    public void deleteInfo(int infoId){
        InfoBySchool info = infoBySchoolRepository.findOne(infoId);
        info.setStudentName(null);
        info.setStudentClass(null);
        info.setGrade(null);
        info.setDiploma(null);
        info.setMajor(null);
        info.setGPA(null);
        info.setGraduationYear(null);
        info.setStudentCode(null);
        infoBySchoolRepository.save(info);
    }

    public InfoBySchool getInfoByStudentId(int studentId) {
        return infoBySchoolRepository.findByStudentId(studentId);
    }

    public void editClass(InfoBySchoolDTO infoBySchoolDTO, String token) throws Exception {
        User user = userRepository.findByToken(token);
        if(user != null){
            Student student = user.getStudent();
            if(student != null){
                StudentClass studentClass = studentClassRepository.findByStudentClass(infoBySchoolDTO.getStudentClass());
                if(studentClass != null){
                    InfoBySchool infoBySchool = student.getInfoBySchool();
                    infoBySchool.setStudentClass(studentClass.getStudentClass());
                    infoBySchool.setMajor(studentClass.getClassName());
                    infoBySchool.setGrade(infoBySchoolDTO.getGrade());
                    infoBySchoolRepository.save(infoBySchool);
                } else if(infoBySchoolDTO.getStudentClass().equals("N")){
                    InfoBySchool infoBySchool = student.getInfoBySchool();
                    infoBySchool.setStudentClass("CN");
                    infoBySchool.setGrade(infoBySchoolDTO.getGrade());
                    infoBySchoolRepository.save(infoBySchool);
                } else {
                    throw new Exception("Class not found!");
                }
            } else {
                throw new Exception("User is not a student!");
            }
        } else {
            throw new Exception("User not found!");
        }
    }
}

