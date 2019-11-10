package uet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uet.DTO.LecturersDTO;
import uet.DTO.MessageDTO;
import uet.DTO.StudentDTO;
import uet.config.GlobalConfig;
import uet.model.*;
import uet.repository.*;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nhkha on 14/05/2017.
 */
@Service
public class LecturersService
{
    private final LecturersRepository lecturersRepository;
    final UserRepository userRepository;
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final MessageService messageService;
    private final InternshipTermRepository internshipTermRepository;
    private InfoBySchoolRepository infoBySchoolRepository;
    private final MessageRepository messageRepository;
    private final InternshipRepository internshipRepository;

    @Autowired
    public LecturersService(
        LecturersRepository lecturersRepository,
        UserRepository userRepository,
        FacultyRepository facultyRepository,
        StudentRepository studentRepository,
        MessageService messageService,
        InternshipTermRepository internshipTermRepository1,
        InfoBySchoolRepository infoBySchoolRepository,
        MessageRepository messageRepository,
        InternshipRepository internshipRepository
    ) {
        this.lecturersRepository = lecturersRepository;
        this.userRepository = userRepository;
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
        this.messageService = messageService;
        this.internshipTermRepository = internshipTermRepository1;
        this.infoBySchoolRepository = infoBySchoolRepository;
        this.messageRepository = messageRepository;
        this.internshipRepository = internshipRepository;
    }

    public Lecturers getInfoLecturers(int lecturersId)
    {
        return lecturersRepository.findById(lecturersId);
    }

    public void editInfoLecturers(LecturersDTO lecturersDTO, String token)
    {
        Lecturers lecturers = lecturersRepository.findById(lecturersDTO.getId());
        User user = userRepository.findByToken(token);
        if (user.getRole().equals(String.valueOf(Role.ADMIN)) || user.getLecturers() == lecturers) {
            lecturers.setFullName(lecturersDTO.getFullName());
            lecturers.setAbout(lecturersDTO.getAbout());
            lecturers.setEmail(lecturersDTO.getEmail());
            lecturers.setEmailVNU(lecturersDTO.getEmailVNU());
            lecturers.setPhoneNumber(lecturersDTO.getPhoneNumber());
            lecturers.setSubject(lecturersDTO.getSubject());
            lecturersRepository.save(lecturers);
        } else {
            throw new NullPointerException("Lecturers did not match!");
        }
    }

    public void changeAva(LecturersDTO lecturersDTO, String token) throws IOException {
        User user = userRepository.findByToken(token);
        Lecturers lecturers = lecturersRepository.findById(lecturersDTO.getId());
        String username = user.getUserName();
        if (lecturers.equals(user.getLecturers())) {
            String pathname = GlobalConfig.sourceAddress + "/app/users_data/" + username + "/";
            String directoryName = "/users_data/" + username + "/";
            String fileName = username + "_avatar.jpg";
            File directory = new File(pathname);
            if (!directory.exists()) {
                directory.mkdir();
            }
            byte[] btDataFile = DatatypeConverter.parseBase64Binary(lecturersDTO.getAvatar());
            File of = new File(pathname + fileName);
            FileOutputStream osf = new FileOutputStream(of);
            osf.write(btDataFile);
            osf.flush();
            lecturers.setAvatar(directoryName + username + "_avatar.jpg");
            lecturersRepository.save(lecturers);
        } else {
            throw new NullPointerException("Error");
        }
    }

    public List<Lecturers> getAllLecturers()
    {
        return (List<Lecturers>) lecturersRepository.findAll();
    }

    public List<Lecturers> getLecturersByFaculty(int facultyId)
    {
        Faculty faculty = facultyRepository.findById(facultyId);
        if (faculty != null) {
            return faculty.getLecturers();
        } else {
            throw new NullPointerException("Falcuty not found!");
        }
    }

    public List<HashMap<String, String>> getLecturersNameAndId()
    {
        List<HashMap<String, String>> listNameAndId = new ArrayList<HashMap<String, String>>();
        List<Lecturers> listPartner = (List<Lecturers>) lecturersRepository.findAll();
        for (Lecturers lecturers : listPartner) {
            HashMap<String, String> hashMapNameAndId = new HashMap<String, String>();
            hashMapNameAndId.put("lecturersName", lecturers.getFullName());
            hashMapNameAndId.put("lecturersId", String.valueOf(lecturers.getId()));
            listNameAndId.add(hashMapNameAndId);
        }
        return listNameAndId;
    }

    public void editLecturersStudent(StudentDTO studentDTO, String token) throws Exception
    {
        if (studentDTO.getLecturersId() != 0 && studentDTO.getInternId() != 0) {
            Lecturers lecturers = lecturersRepository.findById(studentDTO.getLecturersId());
            Internship internship = internshipRepository.findById(studentDTO.getInternId());
            InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
            if (lecturers != null) {
                if (internship != null) {
                    internship.setLecturers(lecturers);
                    internshipRepository.save(internship);
                    if (internshipTerm != null) {
                        if (internshipTerm.isNotiLecturers()) {
                            MessageDTO messageDTO = new MessageDTO();
                            messageDTO.setTitle("Giảng viên hướng dẫn");
                            messageDTO.setContent("Giảng viên hướng dẫn của bạn là: " + lecturers.getFullName() + "<br />" +
                                    "Liên hệ của giảng viên: <br />" +
                                    " + Số điện thoại: " + lecturers.getPhoneNumber() + ".<br />" +
                                    " + Email VNU: " + lecturers.getEmailVNU() + "." +
                                    " + Email: " + lecturers.getEmail() + ".");
                            messageDTO.setReceiverName(internship.getStudent().getUser().getUserName());
                            messageService.writeMessage(messageDTO, token);
                        }
                    }

                } else {
                    throw new Exception("Có lỗi xảy ra!");
                }
            } else {
                throw new Exception("Giảng viên không tồn tại!");
            }
        } else {
            throw new Exception("Error");
        }
    }

    public void addLecturersForStudent(List<StudentDTO> studentList, int lecturersId, String token) throws IOException
    {
        User user = userRepository.findByToken(token);
        Lecturers lecturers = lecturersRepository.findById(lecturersId);
        if (lecturers != null) {
            String fullName = lecturers.getFullName();
            String phone = lecturers.getPhoneNumber();
            String userName = lecturers.getUser().getUserName();
            String email = lecturers.getEmailVNU();
            InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
            for (StudentDTO studentDTO : studentList) {
                Internship internship = internshipRepository.findById(studentDTO.getInternId());
                if (internship != null) {
                    System.out.print(internship.getStudent().getFullName());
                    internship.setLecturers(lecturers);
                    internshipRepository.save(internship);
                    if (internshipTerm != null) {
                        if (internshipTerm.isNotiLecturers()) {
                            MessageDTO messageDTO = new MessageDTO();
                            messageDTO.setTitle("Giảng viên hướng dẫn");
                            messageDTO.setContent("Giảng viên hướng dẫn của bạn là: " + fullName + "<br />" +
                                    "Liên hệ của giảng viên: <br />" +
                                    " + Số điện thoại: " + phone + ".<br />" +
                                    " + Email VNU: " + email + "." +
                                    " + Email: " + lecturers.getEmail() + ".");
                            messageDTO.setReceiverName(internship.getStudent().getUser().getUserName());
                            messageService.writeMessage(messageDTO, token);
                        }
                    }
                }
            }
        } else {
            throw new NullPointerException("Lecturers not found!");
        }
    }

    @Transactional
    public void deleteLecturers(int lecturersId)
    {
        Lecturers lecturers = lecturersRepository.findById(lecturersId);
        if (lecturers != null) {
            if (lecturers.getInternships().isEmpty()) {
                messageRepository.updateUserId(lecturers.getUser().getId());
                userRepository.delete(lecturers.getUser());
            } else {
                throw new NullPointerException("Không thẻ xóa giảng viên");
            }
        } else {
            throw new NullPointerException("Lecturers not found!");
        }
    }

    public void removeLecturersOfStudent(int internId, String token) throws Exception
    {
        Internship internship = internshipRepository.findById(internId);
        if (internship != null) {
            if (internship.getLecturers() != null) {
                internship.setLecturers(null);
                internshipRepository.save(internship);
            } else {
                throw new Exception("Error");
            }
        } else {
            throw new Exception("Student not found!");
        }
    }

    public List<StudentDTO> checkLecturersForStudentExcel(List<StudentDTO> studentDTOs, String token)
    {
        for (StudentDTO studentDTO : studentDTOs) {
            InfoBySchool infoBySchool = infoBySchoolRepository.findByEmailvnu(studentDTO.getEmail());
            if (infoBySchool == null) {
                studentDTO.setEmail("nf");
            }
            Lecturers lecturers = lecturersRepository.findByEmailVNU(studentDTO.getLecturersEmail());
            if (lecturers == null) {
                studentDTO.setLecturersEmail("nf");
            }
        }
        return studentDTOs;
    }

    public void LecturersAssignmentExcel(List<StudentDTO> studentDTOs, String token) throws IOException
    {
        InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
        for (StudentDTO studentDTO : studentDTOs) {
            InfoBySchool infoBySchool = infoBySchoolRepository.findByEmailvnu(studentDTO.getEmail());
            Lecturers lecturers = lecturersRepository.findByEmailVNU(studentDTO.getLecturersEmail());
            if (infoBySchool != null && lecturers != null) {
                String fullName = lecturers.getFullName();
                String phone = lecturers.getPhoneNumber();
                String userName = lecturers.getUser().getUserName();
                String email = lecturers.getEmailVNU();
                Student student = infoBySchool.getStudent();
                Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(student.getId(), internshipTerm);
                if (internship == null) {
                    internship = new Internship();
                }
                internship.setStudent(student);
                internship.setLecturers(lecturers);
                internship.setInternshipTerm(internshipTerm);
                internshipRepository.save(internship);
                studentRepository.save(student);
            }
        }
    }

    public void submitLecturers(String token) throws Exception
    {
        User user = userRepository.findByToken(token);
        if (user != null) {
            InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
            internshipTerm.setNotiLecturers(true);
            internshipTermRepository.save(internshipTerm);
            for (Internship internship : internshipTerm.getInternships()) {
                MessageDTO messageDTO = new MessageDTO();
                messageDTO.setTitle("Giảng viên hướng dẫn");
                messageDTO.setContent("Giảng viên hướng dẫn của bạn là: " + internship.getLecturers().getFullName() + "<br />" +
                        "Liên hệ của giảng viên: <br />" +
                        " + Số điện thoại: " + internship.getLecturers().getPhoneNumber() + ".<br />" +
                        " + Email VNU: " + internship.getLecturers().getEmailVNU() + ".<br />" +
                        " + Email: " + internship.getLecturers().getEmail() + ".");
                messageDTO.setReceiverName(internship.getStudent().getUser().getUserName());
                messageService.writeMessage(messageDTO, token);
            }
        } else {
            throw new Exception("Permission denied!");
        }
    }
}
