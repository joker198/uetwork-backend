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
    private final InternshipTermRepository internshipTermRepository;
    private final InfoBySchoolRepository infoBySchoolRepository;
    private PartnerContactRepository partnerContactRepository;
    private FollowRepository followRepository;
    private MessageRepository messageRepository;

    @Autowired
    public InternshipService(
        StudentRepository studentRepository,
        InternshipRepository internshipRepository,
        UserRepository userRepository,
        PartnerRepository partnerRepository,
        AdminNotificationRepository adminNotificationRepository,
        LecturersRepository lecturersRepository,
        InternshipTermRepository internshipTermRepository,
        InfoBySchoolRepository infoBySchoolRepository,
        PartnerContactRepository partnerContactRepository,
        FollowRepository followRepository,
        MessageRepository messageRepository
    ) {
        this.studentRepository = studentRepository;
        this.internshipRepository = internshipRepository;
        this.userRepository = userRepository;
        this.partnerRepository = partnerRepository;
        this.adminNotificationRepository = adminNotificationRepository;
        this.lecturersRepository = lecturersRepository;
        this.internshipTermRepository = internshipTermRepository;
        this.infoBySchoolRepository = infoBySchoolRepository;
        this.partnerContactRepository = partnerContactRepository;
        this.followRepository = followRepository;
        this.messageRepository = messageRepository;
    }

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

    public List<Internship> getAllIntern(String token)
    {
        return (List<Internship>) internshipRepository.findAll();
    }

    public List<Internship> getAllInPartner(int partnerId, String token)
    {
        User user = userRepository.findByToken(token);
        Partner partner = partnerRepository.findById(partnerId);
        if (user.getRole().equals(String.valueOf(Role.ADMIN)) || user.getRole().equals(String.valueOf(Role.VIP_PARTNER))) {
            return partner.getInternships();
        } else {
            throw new NullPointerException("You don's have permission");
        }
    }

    public void addScore(int internId, float score, String token) throws Exception
    {
        User user = userRepository.findByToken(token);
        Internship internship = internshipRepository.findById(internId);
        if (internship.getLecturers().equals(user.getLecturers()) || user.getRole().equals(String.valueOf(Role.ADMIN))) {
            internship.setScore(score);
            internshipRepository.save(internship);
        }
    }

    public List<Integer> addMultiScore(List<InternshipDTO> internshipDTOS, String token)
    {
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

    private String attachFileReport(InternshipDTO internshipDTO) throws IOException
    {
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

    public void addFinalReport(InternshipDTO internshipDTO, String token) throws Exception
    {
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
            internshipRepository.save(internship);
            messageRepository.save(message);
        } else {
            throw new Exception("You don't have permission");
        }
    }

    public Internship changeById(int internId, InternshipDTO internshipDTO, String token) {
        User user = userRepository.findByToken(token);

        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            Internship internship = internshipRepository.findById(internId);
            if (internshipDTO.getPartnerId() != 0) {
                Partner partner = partnerRepository.findById(internshipDTO.getPartnerId());
                internship.setPartner(partner);
            }
            internship.setStartDate(internshipDTO.getStartDate());
            internship.setEndDate(internshipDTO.getEndDate());
            return internshipRepository.save(internship);
        } else {
            throw new NullPointerException("You don't have permission");
        }
    }

    public void deleteById(int internId) {
        Internship internship = internshipRepository.findById(internId);
        if (internship != null) {
            InternshipTerm internshipTerm = internship.getInternshipTerm();
            Student student = internship.getStudent();
            student.setInternship(null);
            studentRepository.save(student);
            Partner partner = internship.getPartner();
            if (partner != null) {
                partner.getInternships().remove(internship);
                partnerRepository.save(partner);
            }
            internshipRepository.delete(internship);
            if (internshipTerm.getInternshipCount() > 0) {
                internshipTerm.setInternshipCount(internshipTerm.getInternshipCount() - 1);
                internshipTermRepository.save(internshipTerm);
            }
        } else {
            throw new NullPointerException("Internship not found!");
        }
    }

    public void createMultiInternship(List<InternshipDTO> list, String token)
    {
        //
    }

    public void createLecturersForInternship(int internId, int lecturersId)
    {
        //
    }

    public List<StudentDTO> checkExcelAddScore(List<StudentDTO> studentDTOList) throws Exception {
        if (!studentDTOList.isEmpty()) {
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
                studentDTO.setCheck(check);
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
                    if (studentDTO.getStudentCode() != 0) {
                        System.out.print("\n" + studentDTO.getStudentCode() + "\n" + studentDTO.getScore());
                        InfoBySchool infoBySchool = infoBySchoolRepository.findByStudentCode(studentDTO.getStudentCode());
                        if (infoBySchool != null) {
                            Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(infoBySchool.getStudent().getId(), internshipTerm);
                            if (internship != null) {
                                internship.setScore(studentDTO.getScore());
                                internshipRepository.save(internship);
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
            if (!isBlank(student.getFullName()) && !isBlank(student.getEmail()) && !isBlank(student.getPhoneNumber())) {
                InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
                Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(student.getId(), internshipTerm);
                if (internship != null) {
                        Follow follow = followRepository.findById(followId);
                        if (follow != null) {
                            if (follow.getStatus().equals("PASS")) {
                                internship.setInternshipType(follow.getPostTitle());
                                internship.setPartnerName(follow.getPartner().getPartnerName());
                                internship.setPartner(follow.getPartner());
                                follow.setStatus("SELECTED");
                                followRepository.save(follow);
                                internshipRepository.save(internship);
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

    public void updateData() {
        InternshipTerm internshipTerm = internshipTermRepository.findTopByOrderByIdDesc();
        int termId = internshipTerm.getId();
        List<Student> students = (List<Student>) studentRepository.findAll();
        for (Student student : students) {
            InternshipTerm internshipTerm2 = student.getInternshipTerm();
            if (internshipTerm2 != null) {
                Internship internship = internshipRepository.findByStudentIdAndInternshipTerm(student.getId(), internshipTerm2);
                if (internship == null) {
                    internship = new Internship(null, student);
                    internship.setInternshipTerm(internshipTerm2);
                    internshipRepository.save(internship);
                }
                internship.setFollows(followRepository.findByInternshipTermAndStudent(internshipTerm2.getId(), student));
                internship.setLecturers(student.getLecturers());
                internshipRepository.save(internship);
                List<Follow> follows = followRepository.findByStudentId(student.getId());
                for (Follow follow : follows) {
                    if (internshipTerm2.getId() == termId) {
                        InternshipTerm internshipTerm1 = internshipTermRepository.findById(follow.getInternshipTerm());
                        Internship internship1 = internshipRepository.findByStudentIdAndInternshipTerm(student.getId(), internshipTerm1);

                        if (follow.getInternshipTerm() != termId) {
                            if (internship1 == null) {
                                internship1 = new Internship(null, student);
                                internship1.setInternshipTerm(internshipTerm1);
                                internshipRepository.save(internship1);
                            }
                            internship1.setFollows(followRepository.findByInternshipTermAndStudent(internshipTerm1.getId(), student));
                            internshipRepository.save(internship1);
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
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = (Date) formatter.parse(startDate_str);
            Timestamp startDateStamp = new Timestamp(startDate.getTime());
            String expiredDate_str = internshipTerm.getExpiredDate();
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date expiredDate = (Date) formatter.parse(expiredDate_str);

            Calendar c = Calendar.getInstance();
            c.setTime(expiredDate);
            c.add(Calendar.DATE, 1);
            expiredDate = c.getTime();

            Timestamp expiredDateStamp = new Timestamp(expiredDate.getTime());
            Date currentDate = new Date();
            Timestamp currentDateStamp = new Timestamp(currentDate.getTime());
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
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = (Date) formatter.parse(startDate_str);
            Timestamp startDateStamp = new Timestamp(startDate.getTime());
            String expiredDate_str = internshipTerm.getExpiredDate();
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date expiredDate = (Date) formatter.parse(expiredDate_str);

            Calendar c = Calendar.getInstance();
            c.setTime(expiredDate);
            c.add(Calendar.DATE, 1);
            expiredDate = c.getTime();

            Timestamp expiredDateStamp = new Timestamp(expiredDate.getTime());
            Date currentDate = new Date();
            Timestamp currentDateStamp = new Timestamp(currentDate.getTime());
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
