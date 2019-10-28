package uet.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.DTO.InternshipDTO;
import uet.DTO.StudentDTO;
import uet.config.GlobalConfig;
import uet.model.*;
import uet.repository.*;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by fgv on 7/11/2016.
 */
@Service
public class InternshipService {
    private final StudentRepository studentRepository;
    private final InternshipRepository internshipRepository;
    private final UserRepository userRepository;
    private final PartnerRepository partnerRepository;
    private final AdminNotificationRepository adminNotificationRepository;
    private final LecturersRepository lecturersRepository;
    //    private final PassInterviewRepository passInterviewRepository;
    private final InternshipTermRepository internshipTermRepository;
    private final InfoBySchoolRepository infoBySchoolRepository;
    private PartnerContactRepository partnerContactRepository;
    private FollowRepository followRepository;
    private MessageRepository messageRepository;

    @Autowired
    public InternshipService(StudentRepository studentRepository, InternshipRepository internshipRepository,
                             UserRepository userRepository, PartnerRepository partnerRepository,
                             AdminNotificationRepository adminNotificationRepository, LecturersRepository lecturersRepository,
                             InternshipTermRepository internshipTermRepository,
                             InfoBySchoolRepository infoBySchoolRepository, PartnerContactRepository partnerContactRepository, FollowRepository followRepository, MessageRepository messageRepository) {
        this.studentRepository = studentRepository;
        this.internshipRepository = internshipRepository;
        this.userRepository = userRepository;
        this.partnerRepository = partnerRepository;
        this.adminNotificationRepository = adminNotificationRepository;
        this.lecturersRepository = lecturersRepository;
//        this.passInterviewRepository = passInterviewRepository;
        this.internshipTermRepository = internshipTermRepository;
        this.infoBySchoolRepository = infoBySchoolRepository;
        this.partnerContactRepository = partnerContactRepository;
        this.followRepository = followRepository;
        this.messageRepository = messageRepository;
    }

//    private Internship createInternship(Student student, Partner partner, InternshipDTO internshipDTO) {
//        Internship internship = new Internship(partner, student);
//        internship.setEndDate(internshipDTO.getEndDate());
//        internship.setStartDate(internshipDTO.getStartDate());
////        internship.setSupervisor(internshipDTO.getSupervisor());
//        internship.setInternshipType(internshipDTO.getInternshipType());
//        if (partner == null) {
//            internship.setPartnerName(internshipDTO.getPartnerName());
//        } else {
//            internship.setPartnerName(partner.getPartnerName());
//        }
////        internship.setLecturers(student.getLecturers());
////        InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
////            internshipTerm.get().add(post);
////        int internshipCount = internshipTerm.getInternshipCount() + 1;
////        internshipTerm.setInternshipCount(internshipCount);
////        internship.setInternshipTerm(internshipTerm);
////        internshipTermRepository.save(internshipTerm);
//        return internshipRepository.save(internship);
//    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    //show all Internships
    public List<Internship> getAllIntern(String token) {
        return (List<Internship>) internshipRepository.findAll();
    }

    //show all internships of a partner
    public List<Internship> getAllInPartner(int partnerId, String token) {
        User user = userRepository.findByToken(token);
        Partner partner = partnerRepository.findById(partnerId);
        if (user.getRole().equals(String.valueOf(Role.ADMIN)) || user.getRole().equals(String.valueOf(Role.VIP_PARTNER))) {
            return partner.getInternships();
        } else {
            throw new NullPointerException("You don's have permission");
        }

    }

    //find a internship
//    public Internship findInternById(int id, String token) {
//        User user = userRepository.findByToken(token);
//        Internship internship = internshipRepository.findById(id);
//        if (user.getRole().equals(String.valueOf(Role.STUDENT))) {
//            return user.getStudent().getInternship();
//        } else {
//            return internship;
//        }
//    }

    //Create a internship
//    public Internship createIntern(InternshipDTO internshipDTO, String token) {
//        User user = userRepository.findByToken(token);
//        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
//            InfoBySchool infoBySchool = infoBySchoolRepository.findByEmailvnu(internshipDTO.getEmailVNU());
//            if (infoBySchool != null) {
//                Student student = infoBySchool.getStudent();
//                if (student.getInternship() == null) {
//                    String internshipType = internshipDTO.getInternshipType();
//                    Lecturers lecturers = student.getLecturers();
//                    if (internshipType.equals("company")) {
//                        if (internshipDTO.getPartnerId() != 0) {
//                            Partner partner = partnerRepository.findById(internshipDTO.getPartnerId());
//                            return this.createInternship(student, partner, internshipDTO);
//
//                        } else {
//                            Partner partner = internshipDTO.getPartner();
//                            PartnerContactDTO partnerContactDTO = internshipDTO.getPartnerContactDTO();
////                            partner.setPartnerName(internshipDTO.getPartnerName());
////                            partner.setAddress(internshipDTO.getBirthday());
////                            partner.setWebsite(internshipDTO.get);
//                            partnerRepository.save(partner);
//                            PartnerContact partnerContact = new PartnerContact();
//                            partnerContact.setPhone(partnerContactDTO.getPhone());
//                            partnerContact.setEmail(partnerContactDTO.getEmail());
//                            partnerContact.setContactName(partnerContactDTO.getContactName());
//                            partnerContact.setPartner(partner);
//                            partnerContactRepository.save(partnerContact);
//                            return this.createInternship(student, partner, internshipDTO);
//
////                            return internshipRepository.save(internship);
//                        }
//                    } else if (internshipType.equals("university") || internshipType.equals("NCKH")) {
//                        Internship internship = new Internship(null, student);
////                        internship.setLecturers(lecturers);
//                        internship.setPartnerName(null);
//                        internship.setInternshipType(internshipType);
////                        InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
////            internshipTerm.get().add(post);
////                        int internshipCount = internshipTerm.getInternshipCount() + 1;
////                        internshipTerm.setInternshipCount(internshipCount);
////                        internship.setInternshipTerm(internshipTerm);
////                        internshipTermRepository.save(internshipTerm);
//                        return internshipRepository.save(internship);
//                    } else {
//                        throw new NullPointerException("InternshipType not valid!");
//                    }
////                Student student = studentRepository.findById(internshipDTO.getStudentId());
//
////
//                } else {
//                    throw new NullPointerException("This student had internship ");
//                }
//            } else {
//                throw new NullPointerException("Student not found!");
//            }
//        } else {
//            throw new NullPointerException("You don't have permission");
//        }
//    }

    //Lecturers adds final score
    public void addScore(int internId, float score, String token) throws Exception {
        User user = userRepository.findByToken(token);
        Internship internship = internshipRepository.findById(internId);
        if (internship.getLecturers().equals(user.getLecturers()) || user.getRole().equals(String.valueOf(Role.ADMIN))) {
            internship.setScore(score);
            internshipRepository.save(internship);
//            MessageDTO messageDTO = new MessageDTO();
//            messageDTO.setUserId(internship.getStudent().getUser().getId());
//            messageDTO.setTitle("Điểm thực tập chuyên ngành");
//            messageDTO.setMessageType(MessageType.Normal);
//            messageDTO.setContent("Điểm thực tập chuyên ngành của bạn là " + score + ".");
//            Message message = new Message(messageDTO.getTitle(), messageDTO.getContent(), "NEW", user.getUserName(),
//                    MessageType.Normal, internship.getStudent().getUser().getUserName());
//            message.setUser(internship.getStudent().getUser());
//            message.setLastUpdated(new Date());
//            ApplicationContext context =
//                    new ClassPathXmlApplicationContext("Spring-Mail.xml");
//            SendMail mm = (SendMail) context.getBean("sendMail");
//
//            mm.sendMail("carbc@vnu.edu.vn",
//                    internship.getStudent().getEmail(),
//                    messageDTO.getTitle(),
//                    "Điểm thực tập chuyên ngành của bạn là " + score + "." +
//                            "");
//            messageRepository.save(message);
        }
    }


    public List<Integer> addMultiScore(List<InternshipDTO> internshipDTOS, String token) {
        User user = userRepository.findByToken(token);
        List<Integer> list = new ArrayList<>();
        for (InternshipDTO internshipDTO : internshipDTOS) {

            if (internshipDTO.getId() != 0 && internshipDTO.getScore() != 0) {
                Internship internship = internshipRepository.findById(internshipDTO.getId());
                if (internship.getLecturers().equals(user.getLecturers()) || user.getRole().equals(String.valueOf(Role.ADMIN))) {
                    internship.setScore(internshipDTO.getScore());
                    internshipRepository.save(internship);
                }
            }


        }
        return list;
    }

    private String attachFileReport(InternshipDTO internshipDTO) throws IOException {
        String fileFolder = UUID.randomUUID().toString();
        String pathname = GlobalConfig.sourceAddress + "/app/users_data/" + internshipDTO.getStudentName() + "/report/" +
                fileFolder + "/";
        File directory = new File(pathname);
        if (!directory.exists()) {
            directory.mkdir();
        }
        String directoryName = "/users_data/" + internshipDTO.getStudentName() + "/report/" + fileFolder + "/";
        if (internshipDTO.getMessageDTO().getFileType().equals("doc") || internshipDTO.getMessageDTO().getFileType().equals("docx")) {
            String fileName = internshipDTO.getMessageDTO().getFileName();
            byte[] btDataFile = DatatypeConverter.parseBase64Binary(internshipDTO.getMessageDTO().getAttachFile());
            File of = new File(pathname + fileName);
            FileOutputStream osf = new FileOutputStream(of);
            osf.write(btDataFile);
            osf.flush();
        }
        return directoryName + internshipDTO.getMessageDTO().getFileName();
    }

    //Students adds final report
    public void addFinalReport(InternshipDTO internshipDTO, String token) throws Exception {
        User user = userRepository.findByToken(token);
        Student student = user.getStudent();
        Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(student.getId(), internshipTermRepository.findTopByOrderByIdDesc());
        if (internship != null) {
            Lecturers lecturers = internship.getLecturers();
            internshipDTO.setStudentName(user.getUserName());
            internship.setAttachFileAdd(attachFileReport(internshipDTO));
            Message message = new Message(internshipDTO.getMessageDTO().getTitle(), internshipDTO.getMessageDTO().getContent(), "NEW", student.getUser().getUserName(),
                    MessageType.Normal, lecturers.getUser().getUserName());
            message.setUser(lecturers.getUser());
            message.setAttachFileAdd(attachFileReport(internshipDTO));
            message.setLastUpdated(new Date());

//            ApplicationContext context =
//                    new ClassPathXmlApplicationContext("Spring-Mail.xml");
//            SendMail mm = (SendMail) context.getBean("sendMail");

//            mm.sendMail("carbc@vnu.edu.vn",
//                    lecturers.getEmailVNU(),
//                    message.getTitle(),
//                    "Sinh viên " + student.getFullName() + " vừa gửi báo cáo toàn văn thực tập chuyên ngành." + "\n" + "Kiểm tra và cho điểm thực tập tại trang thực tập.");
            internshipRepository.save(internship);
            messageRepository.save(message);
        } else {
            throw new Exception("You don't have permission");
        }
    }

    //Edit a internship
    public Internship changeById(int internId, InternshipDTO internshipDTO, String token) {
        User user = userRepository.findByToken(token);

        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            Internship internship = internshipRepository.findById(internId);
            //neu partnerId = 0 tuc la ko chinh sua partner
            //neu lecturersId = 0 tuc la ko chin chua lecturers
            if (internshipDTO.getPartnerId() != 0) {
                Partner partner = partnerRepository.findById(internshipDTO.getPartnerId());
                internship.setPartner(partner);
            }
//            if (internshipDTO.getLecturersId() != 0) {
//                Lecturers lecturers = lecturersRepository.findById(internshipDTO.getLecturersId());
//                internship.setLecturers(lecturers);
//            }
            internship.setStartDate(internshipDTO.getStartDate());
            internship.setEndDate(internshipDTO.getEndDate());
//            internship.setSupervisor(internshipDTO.getSupervisor());
            return internshipRepository.save(internship);
        } else {
            throw new NullPointerException("You don't have permission");
        }
    }

    //Delete by Id
    public void deleteById(int internId) {
        Internship internship = internshipRepository.findById(internId);
        if (internship != null) {
            InternshipTerm internshipTerm = internship.getInternshipTerm();
//            internshipTerm.getInternships().remove(internship);
            Student student = internship.getStudent();
            student.setInternship(null);
            studentRepository.save(student);
            Partner partner = internship.getPartner();
            if (partner != null) {
                partner.getInternships().remove(internship);
                partnerRepository.save(partner);
            }
//            Lecturers lecturers = internship.getLecturers();
//            lecturers.getInternships().remove(internship);
//            lecturersRepository.save(lecturers);
            internshipRepository.delete(internship);
            if (internshipTerm.getInternshipCount() > 0) {
                internshipTerm.setInternshipCount(internshipTerm.getInternshipCount() - 1);
                internshipTermRepository.save(internshipTerm);
            }
        } else {
            throw new NullPointerException("Internship not found!");
        }
    }

    //create internship from excel
    public void createMultiInternship(List<InternshipDTO> list, String token) {
//        User user = userRepository.findByToken(token);
//        Partner partner = user.getPartner();
//        for (InternshipDTO internshipDTO : list) {
//            User user_ = userRepository.findByUserName(internshipDTO.getEmailVNU());
//            if (user_ == null) {
//                AdminNotification adminNotification = new AdminNotification();
//                adminNotification.setIssue("Kiểm tra lại sinh viên: " + internshipDTO.getStudentName() + "-"
//                        + internshipDTO.getBirthday() + "-" + internshipDTO.getGrade() + "-" + internshipDTO.getStudentClass()
//                        + "-" + " không tìm thấy sinh viên có MSSV: " + internshipDTO.getStudentCode()
//                        + "-" + " không tìm thấy sinh viên có email: " + internshipDTO.getEmailVNU());
//                adminNotification.setPartnetId(partner.getId());
//                adminNotification.setStatus("NEW");
//                adminNotification.setNotificationType("internship");
//                adminNotificationRepository.save(adminNotification);
//            } else {
//                Student student = user_.getStudent();
//                if (student.getInternship() == null) {
//                    Internship internship = this.createInternship(student, partner, internshipDTO);
////                    Lecturers lecturers = lecturersRepository.findById(internshipDTO.getLecturersId());
////                    if (lecturers != null) {
////                        lecturers.getInternships().add(internship);
////                    }
////                    return internshipRepository.save(internship);
//                    internshipRepository.save(internship);
//                } else {
//                    AdminNotification adminNotification = new AdminNotification();
//                    adminNotification.setIssue("Kiểm tra lại sinh viên: " + internshipDTO.getStudentName() + "-"
//                            + internshipDTO.getBirthday() + "-" + internshipDTO.getGrade() + "-" + internshipDTO.getStudentClass()
//                            + "-" + internshipDTO.getStudentCode()
//                            + "-" + internshipDTO.getEmailVNU()
//                            + ", sinh viên đã có internship");
//                    adminNotification.setPartnetId(partner.getId());
//                    adminNotification.setUserName(user.getUserName());
//                    adminNotification.setStudentId(student.getId());
//                    adminNotification.setStatus("NEW");
//                    adminNotification.setNotificationType("internship");
//                    adminNotificationRepository.save(adminNotification);
//                }
//            }
//        }
    }

    //create lecturers for internship
    public void createLecturersForInternship(int internId, int lecturersId) {

    }

//    public PassInterview createPassinterviewLink(int studentId, int partnerId) {
//        PassInterview passInterview = passInterviewRepository.findByPartnerIdAndStudentId(partnerId, studentId);
//        if (passInterview == null) {
//            Partner partner = partnerRepository.findById(partnerId);
//            Student student = studentRepository.findById(studentId);
//            String comfirmationLink = UUID.randomUUID().toString();
//            PassInterview passInterview1 = new PassInterview(partner, student, comfirmationLink);
//            return passInterviewRepository.save(passInterview1);
//        } else {
//            return passInterview;
//        }
//    }

//    public PassInterview passInterview(int studentId, String token) {
//        Partner partner = userRepository.findByToken(token).getPartner();
//        int partnerId = partner.getId();
//        return this.createPassinterviewLink(studentId, partnerId);
////        PassInterview passInterview = passInterviewRepository.findByPartnerIdAndStudentId(partnerId, studentId);
////        if(passInterview == null){
////            Student student = studentRepository.findById(studentId);
////            String comfirmationLink = UUID.randomUUID().toString();
////            PassInterview passInterview1 = new PassInterview(partner, student, comfirmationLink);
////            return passInterviewRepository.save(passInterview1);
////        } else {
////            return passInterview;
////        }
//    }

//    public Internship selectIntern(String comfirmationLink) {
//        PassInterview passInterview = passInterviewRepository.findByComfirmationLink(comfirmationLink);
//        if (passInterview != null) {
//            Student student = passInterview.getStudent();
//            String checkInfo = student.getFullName() + student.getEmail() + student.getPhoneNumber();
//            if (!isBlank(student.getFullName()) && !isBlank(student.getEmail()) && !isBlank(student.getPhoneNumber())) {
//                InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
//                Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(student.getId(), internshipTerm);
//                if (internship != null) {
////                    Internship internship = new Internship(passInterview.getPartner(), passInterview.getStudent());
////                    InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
////            internshipTerm.get().add(post);
////                    int internshipCount = internshipTerm.getInternshipCount() + 1;
////                    internshipTerm.setInternshipCount(internshipCount);
////                    internship.setInternshipTerm(internshipTerm);
//                    Follow follow = followRepository.findByInternshipIdAndPartnerId(internship.getId(),
//                            passInterview.getPartner().getId());
//                    internship.setInternshipType(follow.getPostTitle());
//                    internship.setPartnerName(passInterview.getPartner().getPartnerName());
////                    internship.setInternshipType("company");
//                    internship.setPartnerName(passInterview.getPartner().getPartnerName());
////                    internship.setLecturers(passInterview.getStudent().getLecturers());
////                    internshipTermRepository.save(internshipTerm);
//                    return internshipRepository.save(internship);
//                } else {
//                    throw new NullPointerException("Internship not found!");
//                }
//            } else {
//                throw new NullPointerException("Please fill all your information in profile!");
//            }
//        } else {
//            throw new NullPointerException("Not found!");
//        }
//    }

//    public List<PassInterview> getComfirmationLinkOfStudent(int studentId, String token) {
//        User user = userRepository.findByToken(token);
//        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
//            List<PassInterview> passInterviewList = passInterviewRepository.findByStudentId(studentId);
//            if (!passInterviewList.isEmpty()) {
//                return passInterviewList;
//            } else {
//                throw new NullPointerException("Not found!");
//            }
//        } else if (user.getRole().equals(String.valueOf(Role.STUDENT))) {
//            Student student = user.getStudent();
//            if (student.getId() == studentId) {
//                return student.getPassInterviews();
//            } else {
//                throw new NullPointerException("You dont have permission!");
//            }
//        } else {
//            throw new NullPointerException("You dont have permission!");
//        }
//    }

//    public List<Internship> getAllInternshipByInternshipTerm(int internshipTermId) {
//        InternshipTerm internshipTerm = internshipTermRepository.findById(internshipTermId);
//        if (internshipTerm != null) {
//            return internshipTerm.getInternships();
//        } else {
//            throw new NullPointerException("Internship Term not found!");
//        }
//    }

//    public void addLecturersForInternship(int internId, int lecturersId) {
//        Lecturers lecturers = lecturersRepository.findById(lecturersId);
//        if (lecturers != null) {
//            Internship internship = internshipRepository.findById(internId);
//            if (internship != null) {
//
//                internship.setLecturers(lecturers);
//                internshipRepository.save(internship);
//            } else {
//                throw new NullPointerException("internship not found!");
//            }
//        } else {
//            throw new NullPointerException("Lecturers not found!");
//        }
//    }

//    public List<Internship> getInternByLecturers(int lecturersId, String token) {
//        User user = userRepository.findByToken(token);
//        if (user.getRole().equals(Role.LECTURERS)) {
//            return user.getLecturers().getInternships();
//        } else {
//            return lecturersRepository.findById(lecturersId).getInternships();
//        }
//    }

    public List<StudentDTO> checkExcelAddScore(List<StudentDTO> studentDTOList) throws Exception {
        if (!studentDTOList.isEmpty()) {
//            List<StudentDTO>
            InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
            for (StudentDTO studentDTO : studentDTOList) {
                Boolean check = true;
                if (studentDTO.getStudentCode() == 0) {
                    check = false;
                    studentDTO.setMessage("Mã SV không đúng");
                } else {
                    InfoBySchool infoBySchool = infoBySchoolRepository.findByStudentCode(studentDTO.getStudentCode());
                    if (infoBySchool != null) {
                        Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(infoBySchool.getStudent().getId(), internshipTerm);
                        if (internship == null) {
                            check = false;
                            studentDTO.setMessage("Sinh viên chưa đăng kí thực tập tại kì hiện tại");
                        } else {
                            if (studentDTO.getScore() <= 0) {
                                check = false;
                                studentDTO.setMessage("Điểm không đúng");
                            }
                        }
                    } else {
                        check = false;
                        studentDTO.setMessage("Sinh viên không tồn tại");
                    }
                }
//                if (studentDTO.getSTT() == null) {
//                    check = false;
//                }
//                if (studentDTO.getScore() <= 0) {
//                    check = false;
//                }
                studentDTO.setCheck(check);
//                if (check) {
//                    studentDTOList.remove(studentDTO);
//                }
            }
            return studentDTOList;
        } else {
            throw new Exception("Danh sách điểm rỗng!");
        }
    }

    public List<StudentDTO> AddScoreByExcel(List<StudentDTO> studentDTOList, String token) {
        User user = userRepository.findByToken(token);
        if (user != null) {
            if (!studentDTOList.isEmpty()) {
                InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
                for (StudentDTO studentDTO : studentDTOList) {
//                Boolean check = true;
                    if (studentDTO.getStudentCode() != 0) {
                        System.out.print("\n" + studentDTO.getStudentCode() + "\n" + studentDTO.getScore());
                        InfoBySchool infoBySchool = infoBySchoolRepository.findByStudentCode(studentDTO.getStudentCode());
                        if (infoBySchool != null) {
//                        if(studentDTO.getScore() < 0){
//
//                        }
                            Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(infoBySchool.getStudent().getId(), internshipTerm);
                            if (internship != null) {
                                internship.setScore(studentDTO.getScore());
                                internshipRepository.save(internship);
//                                MessageDTO messageDTO = new MessageDTO();
//                                messageDTO.setUserId(internship.getStudent().getUser().getId());
//                                messageDTO.setTitle("Điểm thực tập chuyên ngành");
//                                messageDTO.setMessageType(MessageType.Normal);
//                                messageDTO.setContent("Điểm thực tập chuyên ngành của bạn là " + studentDTO.getScore() + ".");
//                                Message message = new Message(messageDTO.getTitle(), messageDTO.getContent(), "NEW", user.getUserName(),
//                                        MessageType.Normal, internship.getStudent().getUser().getUserName());
//                                message.setUser(internship.getStudent().getUser());
//                                message.setLastUpdated(new Date());
//                                messageRepository.save(message);
                            }
                        }

                    }

                }
            }
        }
        return studentDTOList;
    }

    public Internship selectInternPartnerId(int followId, String token) throws Exception {
        User user = userRepository.findByToken(token);
        Student student = user.getStudent();
        if (student != null) {
//            String checkInfo = student.getFullName() + student.getEmail() + student.getPhoneNumber();
            if (!isBlank(student.getFullName()) && !isBlank(student.getEmail()) && !isBlank(student.getPhoneNumber())) {
//                PassInterview passInterview = passInterviewRepository.findByPartnerIdAndStudentId(partnerId, student.getId());
//                if (passInterview != null) {
                InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
                Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(student.getId(), internshipTerm);
                if (internship != null) {
//                        Internship internship = new Internship(passInterview.getPartner(), passInterview.getStudent());
                        Follow follow = followRepository.findById(followId);
                        if (follow != null) {
                            if (follow.getStatus().equals("PASS")) {
                                internship.setInternshipType(follow.getPostTitle());
                                internship.setPartnerName(follow.getPartner().getPartnerName());
                                internship.setPartner(follow.getPartner());
                                follow.setStatus("SELECTED");
                                followRepository.save(follow);
                                internshipRepository.save(internship);
//                        internship.setLecturers(student.getLecturers());
//                        internshipTermRepository.save(internshipTerm);
                                return internshipRepository.save(internship);
                            } else {
                                throw new Exception("Không thể lựa chọn");
                            }
                        } else {
                            throw new Exception("Có lỗi xảy ra!");
                        }
                } else {
                    throw new NullPointerException("Cannot choose this partner!");
                }
            } else {
                throw new NullPointerException("Please fill all your information in profile!");
            }
        } else {
            throw new Exception("Student not found!");
        }
    }

    public List<Internship> getAllInternshipOfStudent(String token) throws Exception {
        User user = userRepository.findByToken(token);
        if (user != null) {
            Student student = user.getStudent();
            List<Internship> internships = student.getInternship();
            for (Internship internship : internships) {
                if (!internship.getInternshipTerm().isNotiLecturers()) {
                    internship.setScore(null);
                }
//                List<Follow> follows = followRepository.findByInternshipTermAndStudent(internship.getInternshipTerm().getId(), internship.getStudent());
//                internship.setFollows(internship.getFollows());
                if (!internship.getInternshipTerm().isNotiLecturers()) {
                    internship.setLecturers(null);
                }
            }
            return internships;
        } else {
            throw new Exception("Permission denied!");
        }
    }

    public void submitScore(String token) throws Exception {
        User user = userRepository.findByToken(token);
        if (user != null) {
            InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
            internshipTerm.setNotiScore(true);
            internshipTermRepository.save(internshipTerm);
            for (Internship internship : internshipTerm.getInternships()) {
//                Student student = internship.getStudent();
//                MessageDTO messageDTO = new MessageDTO();
//                messageDTO.setUserId(internship.getStudent().getUser().getId());
//                messageDTO.setTitle("Điểm thực tập chuyên ngành");
//                messageDTO.setMessageType(MessageType.Normal);
//                messageDTO.setContent("Điểm thực tập chuyên ngành của bạn là " + internship.getScore() + ".");
                Message message = new Message("Điểm thực tập chuyên ngành", "Điểm thực tập chuyên ngành của bạn là " +
                        internship.getScore() + ".", "NEW", user.getUserName(),
                        MessageType.Normal, internship.getStudent().getUser().getUserName());
                message.setUser(internship.getStudent().getUser());
                message.setLastUpdated(new Date());
                messageRepository.save(message);
            }
        } else {
            throw new Exception("Permission denied!");
        }
    }

    // phan tao dữ liệu internship cho sinh viên là cần phải là get all sinh viên,
    // nếu như kì != null thi tao, check intẻn theo kì và student id xem là đã có chưa, nếu chưa có thì tạo
    // và gán lecturers cho thằng đấy theo như thông tin ở trong bảng student
    // nêu smà làm như vậy thì sẽ tạo dc
    //nếu thằng nào mà cái intern term là kì 2 thì kiểm tra xem thằng đấy có follow trong kì 1 ko, nếu mà có thì
    //tìm intern kì 1, nếu mà chưa có thì tạo
    public void updateData() {
        InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
//        System.out.print(internshipTerm.getId());
        int termId = internshipTerm.getId();
        List<Student> students = (List<Student>) studentRepository.findAll();
        for (Student student : students) {
//            System.out.print("\n" + student.getId() + "\n");
            InternshipTerm internshipTerm2 = student.getInternshipTerm();
            if (internshipTerm2 != null) {
                Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(student.getId(), internshipTerm2);
                if (internship == null) {
//                    if(student.getId() == 435){
//                        System.out.print(student.getFullName());
//                    }
                    internship = new Internship(null, student);
                    internship.setInternshipTerm(internshipTerm2);
                    internshipRepository.save(internship);
                }
                internship.setFollows(followRepository.findByInternshipTermAndStudent(internshipTerm2.getId(), student));
                internship.setLecturers(student.getLecturers());
                internshipRepository.save(internship);
//                if(student.getId() == 435){
//                    System.out.print("--" + internshipTerm2.getId());
//                }

                List<Follow> follows = followRepository.findByStudentId(student.getId());
                for (Follow follow : follows) {
                    if (internshipTerm2.getId() == termId) {
                        InternshipTerm internshipTerm1 = internshipTermRepository.findById(follow.getInternshipTerm());
                        Internship internship1 = internshipRepository.findByStudentIdAndInternshipTerm(student.getId(), internshipTerm1);

                        if (follow.getInternshipTerm() != termId) {
//                            System.out.print(" -- follow.getInternshipTerm: " + follow.getInternshipTerm());

//                            System.out.print(" -- internshipTerm1: " + internshipTerm1.getId());
                            if (internship1 == null) {
                                internship1 = new Internship(null, student);
                                internship1.setInternshipTerm(internshipTerm1);
                                internshipRepository.save(internship1);
                            }
//                            internship1.setLecturers(student.getLecturers());
                            internship1.setFollows(followRepository.findByInternshipTermAndStudent(internshipTerm1.getId(), student));
                            internshipRepository.save(internship1);
//                            continue;
                        }
                        follow.setInternship(internship1);
                        followRepository.save(follow);
                    } else {
                        follow.setInternship(internship);
                        followRepository.save(follow);
                    }
                }
            }
        }
    }

    public Internship getCurrentInternshipOfInternshipTerm(String token) throws ParseException {
        User user = userRepository.findByToken(token);
        InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();

        Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(user.getStudent().getId(), internshipTerm);
        if (internship == null) {
            if (internshipTerm != null) {
                internship = new Internship();
                internship.setInternshipTerm(internshipTerm);
                internship.setFollows(new ArrayList<Follow>());
            }
        }
        return internship;
    }

    public Internship createInternship(String token) throws Exception {
        User user = userRepository.findByToken(token);
        Student student = user.getStudent();
        InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
        if (internshipTerm != null) {
            String startDate_str = internshipTerm.getStartDate();
//            String[] date = startDate_str.split("T");
//            System.out.print("\n" + date[0] + "\n");
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = (Date) formatter.parse(startDate_str);
            Timestamp startDateStamp = new Timestamp(startDate.getTime());
//            System.out.print("\n startDateStamp: " + startDateStamp + "\n");

            String expiredDate_str = internshipTerm.getExpiredDate();
//            String[] expiredDateS = expiredDate_str.split("T");
//            System.out.print("\n" + expiredDateS[0] + "\n");
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date expiredDate = (Date) formatter.parse(expiredDate_str);

            Calendar c = Calendar.getInstance();
            c.setTime(expiredDate);
            c.add(Calendar.DATE, 1);
            expiredDate = c.getTime();

            Timestamp expiredDateStamp = new Timestamp(expiredDate.getTime());
//            System.out.print("\n expiredDateStamp:" + expiredDateStamp + "\n");
//            expiredDateStamp.after(expiredDateStamp)
            Date currentDate = new Date();
//            Calendar c = Calendar.getInstance();
////            c.setTime(currentDate);
////            c.add(Calendar.DATE, -1);
////            Date currentDatePlusOne = c.getTime();
            Timestamp currentDateStamp = new Timestamp(currentDate.getTime());
//            System.out.print("\n currentDateStamp:" + currentDateStamp + "\n");


            Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(user.getStudent().getId(), internshipTerm);

            if (internship == null) {
                if (currentDateStamp.before(expiredDateStamp) && currentDateStamp.after(startDateStamp)) {
                    internship = new Internship(internshipTerm, student, new Date(System.currentTimeMillis() + 1000 * 60));
                    internshipRepository.save(internship);
                    internshipTerm.setInternshipCount(internshipTerm.getInternshipCount() + 1);
                    internshipTermRepository.save(internshipTerm);
                } else {
                    throw new Exception("Out of date");
                }
            }
            return internship;
        } else {
            throw new Exception("InternshipTerm not found");
        }
    }

    public void deleteInternship(String token) throws Exception {
        User user = userRepository.findByToken(token);
        Student student = user.getStudent();
        InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
        if (internshipTerm != null) {
            String startDate_str = internshipTerm.getStartDate();
//            String[] date = startDate_str.split("T");
//            System.out.print("\n" + date[0] + "\n");
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = (Date) formatter.parse(startDate_str);
            Timestamp startDateStamp = new Timestamp(startDate.getTime());
//            System.out.print("\n startDateStamp: " + startDateStamp + "\n");

            String expiredDate_str = internshipTerm.getExpiredDate();
//            String[] expiredDateS = expiredDate_str.split("T");
//            System.out.print("\n" + expiredDateS[0] + "\n");
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date expiredDate = (Date) formatter.parse(expiredDate_str);

            Calendar c = Calendar.getInstance();
            c.setTime(expiredDate);
            c.add(Calendar.DATE, 1);
            expiredDate = c.getTime();

            Timestamp expiredDateStamp = new Timestamp(expiredDate.getTime());
//            System.out.print("\n expiredDateStamp:" + expiredDateStamp + "\n");
//            expiredDateStamp.after(expiredDateStamp)
            Date currentDate = new Date();
//            Calendar c = Calendar.getInstance();
////            c.setTime(currentDate);
////            c.add(Calendar.DATE, -1);
////            Date currentDatePlusOne = c.getTime();
            Timestamp currentDateStamp = new Timestamp(currentDate.getTime());
//            System.out.print("\n currentDateStamp:" + currentDateStamp + "\n");

            if (currentDateStamp.before(expiredDateStamp) && currentDateStamp.after(startDateStamp)) {
                Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(user.getStudent().getId(), internshipTerm);
                if (internship != null) {
                    if (internship.getFollows().isEmpty()) {
                        internshipRepository.delete(internship);
                        if (internshipTerm.getInternshipCount() > 0) {
                            internshipTerm.setInternshipCount(internshipTerm.getInternshipCount() - 1);
                            internshipTermRepository.save(internshipTerm);
                        }
                    } else {
                        throw new Exception("Cannot unregister");
                    }
                } else {
                    throw new Exception("internship bot found");
                }
            } else {
                throw new Exception("Out of date");
            }
        } else {
            throw new Exception("InternshipTerm not found");
        }
    }

    public void update(){
        List<Follow> follows = followRepository.findByStatusAndInternshipTerm("SELECTED", 3);
        for(Follow follow : follows){
            Internship internship = follow.getInternship();
            internship.setPartner(follow.getPartner());
            internship.setPartnerName(follow.getPartner().getPartnerName());
            internship.setInternshipType(follow.getPostTitle());
            internshipRepository.save(internship);
        }
    }

}
