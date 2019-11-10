package uet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uet.DTO.InfoBySchoolDTO;
import uet.DTO.PartnerDTO;
import uet.DTO.PostDTO;
import uet.DTO.StudentDTO;
import uet.config.GlobalConfig;
import uet.model.*;
import uet.repository.*;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Trung on 7/8/2016.
 */
@Service
public class StudentService {

    final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PartnerRepository partnerRepository;
    private final PostRepository postRepository;
    private final InfoBySchoolRepository infoBySchoolRepository;
    private final InternshipTermRepository internshipTermRepository;
    private FollowRepository followRepository;
    private final InternshipRepository internshipRepository;

    @Autowired
    public StudentService(
        UserRepository userRepository,
        StudentRepository studentRepository,
        PartnerRepository partnerRepository,
        PostRepository postRepository,
        InfoBySchoolRepository infoBySchoolRepository,
        InternshipTermRepository internshipTermRepository,
        FollowRepository followRepository1,
        InternshipRepository internshipRepository
    ) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.partnerRepository = partnerRepository;
        this.postRepository = postRepository;
        this.infoBySchoolRepository = infoBySchoolRepository;
        this.internshipTermRepository = internshipTermRepository;
        this.followRepository = followRepository1;
        this.internshipRepository = internshipRepository;
    }

    public List<HashMap<String, String>> getAllInfo(String token)
    {
        List<InfoBySchool> allInfoBySchool = (List<InfoBySchool>) infoBySchoolRepository.findAll();
        List<HashMap<String, String>> listPartnerInfo = new ArrayList<HashMap<String, String>>();
        User checkUser = userRepository.findByToken(token);
        if (checkUser.getRole().equals(String.valueOf(Role.VIP_PARTNER))) {
            for (InfoBySchool infoBySchool : allInfoBySchool) {
                Student student = studentRepository.findByInfoBySchoolId(infoBySchool.getId());
                User user = userRepository.findByStudentIdAndStatus(student.getId(), "A");
                HashMap<String, String> lInfoBySChool = new HashMap<String, String>();
                lInfoBySChool.put("userId", String.valueOf(user.getId()));
                lInfoBySChool.put("status", user.getStatus());
                lInfoBySChool.put("studentName", infoBySchool.getStudentName());
                lInfoBySChool.put("infoBySchoolId", String.valueOf(infoBySchool.getId()));
                lInfoBySChool.put("gpa", String.valueOf(infoBySchool.getGPA()));
                lInfoBySChool.put("diploma", infoBySchool.getDiploma());
                lInfoBySChool.put("grade", infoBySchool.getGrade());
                lInfoBySChool.put("graduationYear", infoBySchool.getGraduationYear());
                lInfoBySChool.put("major", infoBySchool.getMajor());
                lInfoBySChool.put("studentClass", infoBySchool.getStudentClass());
                lInfoBySChool.put("studentCode", String.valueOf(infoBySchool.getStudentCode()));
                listPartnerInfo.add(lInfoBySChool);
            }
        }
        if (checkUser.getRole().equals(String.valueOf(Role.ADMIN))) {
            for (InfoBySchool infoBySchool : allInfoBySchool) {
                Student student = studentRepository.findByInfoBySchoolId(infoBySchool.getId());
                User user = userRepository.findByStudentId(student.getId());
                HashMap<String, String> lInfoBySChool = new HashMap<String, String>();
                lInfoBySChool.put("userId", String.valueOf(user.getId()));
                lInfoBySChool.put("status", user.getStatus());
                lInfoBySChool.put("studentName", infoBySchool.getStudentName());
                lInfoBySChool.put("infoBySchoolId", String.valueOf(infoBySchool.getId()));
                lInfoBySChool.put("gpa", String.valueOf(infoBySchool.getGPA()));
                lInfoBySChool.put("diploma", infoBySchool.getDiploma());
                lInfoBySChool.put("grade", infoBySchool.getGrade());
                lInfoBySChool.put("graduationYear", infoBySchool.getGraduationYear());
                lInfoBySChool.put("major", infoBySchool.getMajor());
                lInfoBySChool.put("studentClass", infoBySchool.getStudentClass());
                lInfoBySChool.put("studentCode", String.valueOf(infoBySchool.getStudentCode()));
                listPartnerInfo.add(lInfoBySChool);
            }
        }
        return listPartnerInfo;
    }

    // show all student
    public Page<Student> getAllStudent(Pageable pageable)
    {
        return studentRepository.findAllByOrderByIdDesc(pageable);
    }

    // show info of a student
    public Student getStudentInfo(String token)
    {
        User user = userRepository.findByToken(token);
        Student student = user.getStudent();
        if (student != null) {
            return student;
        } else {
            throw new NullPointerException("No result");
        }
    }

    //edit info of a student
    public Student editStudentInfo(StudentDTO studentDTO, String token) throws IOException
    {
        User user = userRepository.findByToken(token);
        Student student = user.getStudent();
        if (student != null) {
            if (studentDTO.getBirthday() != null) {
                student.setBirthday(studentDTO.getBirthday());
            }
            if (studentDTO.getFullName() != null) {
                student.setFullName(studentDTO.getFullName());
            }
            if (studentDTO.getAddress() != null) {
                student.setAddress(studentDTO.getAddress());
            }
            if (studentDTO.getEmail() != null) {
                student.setEmail(studentDTO.getEmail());
            }
            if (studentDTO.getPhoneNumber() != null) {
                student.setPhoneNumber(studentDTO.getPhoneNumber());
            }
            if (studentDTO.getSkype() != null) {
                student.setSkype(studentDTO.getSkype());
            }
            if (studentDTO.getDesire() != null) {
                student.setDesire(studentDTO.getDesire());
            }
            return studentRepository.save(student);
        } else {
            throw new NullPointerException("Error ");
        }
    }

    //change Avatar
    public void changeAva(StudentDTO studentDTO, String token) throws IOException
    {
        User user = userRepository.findByToken(token);
        Student student = user.getStudent();
        String username = user.getUserName();
        Student studentInfo = studentRepository.findById(studentDTO.getId());
        if (studentInfo.equals(student)) {
            String pathname = GlobalConfig.sourceAddress + "/app/users_data/" + username + "/";
            String directoryName = "/users_data/" + username + "/";
            String fileName = username + "_avatar.jpg";
            File directory = new File(pathname);
            if (!directory.exists()) {
                directory.mkdir();
            }
            byte[] btDataFile = DatatypeConverter.parseBase64Binary(studentDTO.getAvatar());
            File of = new File(pathname + fileName);
            FileOutputStream osf = new FileOutputStream(of);
            osf.write(btDataFile);
            osf.flush();
            studentInfo.setAvatar(directoryName + username + "_avatar.jpg");
            studentRepository.save(studentInfo);
        } else {
            throw new NullPointerException("Error");
        }
    }

    public Student findStudent(int studentId, String token) {
        User user = userRepository.findByToken(token);
        if (user.getRole().equals(String.valueOf(Role.STUDENT))) {
            Student student1 = studentRepository.findById(studentId);
            if (user.getStudent().equals(student1)) {
                return student1;
            } else {
                throw new NullPointerException("No result.");
            }
        } else {
            Student student2 = studentRepository.findById(studentId);
            if (student2 != null) {
                return student2;
            } else {
                throw new NullPointerException("No result.");
            }
        }
    }

    //Student search partner
    public List<Partner> searchPartner(PartnerDTO partnerDTO) {
        List<Partner> allPartnerMatched = (List<Partner>) partnerRepository.findByPartnerNameContaining(partnerDTO.getPartnerName());
        return allPartnerMatched;
    }

    //Student search post description
    public List<Post> searchDescription(PostDTO postDTO) {
        List<Post> allPostMatched = (List<Post>) postRepository.findByDescribePostContaining(postDTO.getDescribePost());
        return allPostMatched;
    }

    //Student search post by content
    public List<Post> searchContent(PostDTO postDTO)
    {
        List<Post> allPostMatched = (List<Post>) postRepository.findByContentContaining(postDTO.getContent());
        return allPostMatched;
    }

    public void deleteStudent(int studentId)
    {
        Student student = studentRepository.findById(studentId);
        if(student != null){
            userRepository.delete(student.getUser());
        } else {
            throw new NullPointerException("Student not found!");
        }
    }

    public List<InfoBySchool> findStudentByStudentCode(InfoBySchoolDTO info)
    {
         List<InfoBySchool> listInfoBySchool = infoBySchoolRepository.findByStudentCodeContaining(info.getStudentCode());
         for(InfoBySchool infoBySchool : listInfoBySchool){
             infoBySchool.setStudent(infoBySchool.getStudent());
         }
         return listInfoBySchool;
    }

    public List<Internship> getStudentByInternshipTerm(int internshipTermId)
    {
        InternshipTerm internshipTerm = internshipTermRepository.findById(internshipTermId);
        if(internshipTerm != null){
            return internshipTerm.getInternships();
        } else {
            throw new NullPointerException("InternshipTerm not found!");
        }
    }

    public List<Internship> getStudentByLecturersId(String token) throws Exception
    {
        User user = userRepository.findByToken(token);
        if (user != null) {
            if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
                return null;
            } else if (user.getRole().equals(String.valueOf(Role.LECTURERS))) {
                InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
                return internshipRepository.findByInternshipTermAndLecturersId(internshipTerm, user.getLecturers().getId());
            } else {
                throw new Exception("No permission!");
            }
        } else {
            throw new Exception("No permission!");
        }
    }

    public StudentDTO getStudentInformationByUserId(int userId) throws Exception
    {
        User user = userRepository.findById(userId);
        if(user != null){
            if(user.getStudent() != null){
                Student student = user.getStudent();
                StudentDTO studentDTO = new StudentDTO();
                studentDTO.setInfoBySchool(student.getInfoBySchool());
                studentDTO.setInternshipList(student.getInternship());
                studentDTO.setStudent(student);
                return studentDTO;
            } else {
                throw new Exception("User not found");
            }
        } else {
            throw new Exception("User not found");
        }
    }
}
