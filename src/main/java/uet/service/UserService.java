package uet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uet.DTO.ChangePasswordDTO;
import uet.DTO.CreateStudentDTO;
import uet.DTO.InfoBySchoolDTO;
import uet.DTO.UserDTO;
import uet.config.GlobalConfig;
import uet.model.*;
import uet.repository.*;
import uet.stereotype.SendMail;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Tu on 02-May-16.
 */
@Service
public class UserService {
    private final
    StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final
    InfoBySchoolRepository infoBySchoolRepository;
    private final
    PartnerRepository partnerRepository;
    private final
    LecturersRepository lecturersRepository;
    private final UnitNameRepository unitNameRepository;
    private final ActivityLogRepository activityLogRepository;
    private final PartnerType partnerType;
    private final NationRepository nationRepository;

    @Autowired

    public UserService(StudentRepository studentRepository, UserRepository userRepository,
                       InfoBySchoolRepository infoBySchoolRepository, PartnerRepository partnerRepository,
                       LecturersRepository lecturersRepository, UnitNameRepository unitNameRepository,
                       ActivityLogRepository activityLogRepository, PartnerType partnerType, NationRepository nationRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.infoBySchoolRepository = infoBySchoolRepository;
        this.partnerRepository = partnerRepository;
        this.lecturersRepository = lecturersRepository;
        this.unitNameRepository = unitNameRepository;
        this.activityLogRepository = activityLogRepository;
        this.partnerType = partnerType;
        this.nationRepository = nationRepository;
    }

    public void createFolder() {
        List<User> userList = (List<User>) userRepository.findAll();
        for(User user : userList){
                String pathname = GlobalConfig.sourceAddress + "/app/users_data/" + user.getUserName() + "/";
                File directory = new File(pathname);
                if (!directory.exists()) {
                    directory.mkdir();
                }
                pathname = GlobalConfig.sourceAddress + "/app/users_data/" + user.getUserName() + "/report";
                directory = new File(pathname);
                if (!directory.exists()) {
                    directory.mkdir();
                }
        }
    }

    public List<User> findUserByUserNameContaining(String userName) throws Exception {
        if(userName != null){
            return userRepository.findByUserNameContaining(userName);
        } else {
            throw new Exception("UNF");
        }
    }


    public class ErrorMessage extends Exception {

        public ErrorMessage(String message) {
            super(message);
        }

    }


    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    private Map<String, Object> login(UserDTO userDTO, User user) throws Exception {
        if (userDTO.getPassword().equals(user.getPassword())) {
            if (user.getToken() == null) {
                user.setToken(UUID.randomUUID().toString());
                user.setExpiryTime(new Date(System.currentTimeMillis() + 1000 * 60 * 15));
            } else {
                user.setExpiryTime(new Date(System.currentTimeMillis() + 1000 * 60 * 15));
            }
            user.setLastLogin(new Date(System.currentTimeMillis() + 1000 * 60 * 15));
            userRepository.save(user);
            Map<String, Object> response = new HashMap<>();
            response.put("token", user.getToken());
            if(user.getRole().equals(String.valueOf(Role.UNIT))){
                user.setId(user.getUnitName().getId());
                user.setRolesAndSigningLevel(user.getUnitName().getRolesAndSigningLevel());
                response.put("rolesAndSigningLevel", user.getUnitName().getRolesAndSigningLevel());
                response.put("id", user.getId());
            }
            if(user.getRole().equals(String.valueOf(Role.ADMIN_UNIT)) || user.getRole().equals(String.valueOf(Role.ADMIN_VNU))){
                user.setId(0);
                response.put("id", 0);
                user.setRolesAndSigningLevel(user.getRolesAndSigningLevel());
                response.put("rolesAndSigningLevel", user.getRolesAndSigningLevel());
            }

            response.put("role", user.getRole());
            response.put("userName", user.getUserName());
            return response;
        } else {
            throw new Exception("Wrong password.");
        }
    }

    //Show all user
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAllByOrderByIdDesc(pageable);
//        return userRepository.findAll();
    }

    //signup
    public void createUser(UserDTO userDTO) throws NoSuchAlgorithmException, ErrorMessage {
        if (userDTO.getFullName() != null) {
            if (userDTO.getEmailvnu() != null) {
                if (userDTO.getEmailvnu().contains("@vnu.edu.vn")) {
                    String userName = userDTO.getEmailvnu().replace("@vnu.edu.vn", "");
//                    if (isInteger(studentCode)) {
                    InfoBySchool infoBySchool1 = infoBySchoolRepository.findByEmailvnu(userDTO.getEmailvnu());
                    if (infoBySchool1 == null) {
//                                    studentCode = Integer.valueOf(studentCode);


//                            String userName = userDTO.getUserName();
                        String vnuEmail = userDTO.getEmailvnu();
                        String[] parts = UUID.randomUUID().toString().split("-");
                        String password = parts[0];
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        md.update(password.getBytes());
                        byte[] digest = md.digest();
                        String password_md5 = DatatypeConverter
                                .printHexBinary(digest).toLowerCase();
                        User user = new User();
                        user.setUserName(userName);
                        user.setPassword(password_md5);
                        user.setStatus("A");
                        user.setRole(String.valueOf(Role.STUDENT));
                        Student student = new Student();
                        user.setStudent(student);
                        student.setUser(user);
                        student.setFullName(userDTO.getFullName());
                        //create InfoByschool
                        InfoBySchool infoBySchool = new InfoBySchool();
                        infoBySchool.setStudentName(userDTO.getFullName());
                        if (isInteger(userName)) {
                            int mssv = Integer.valueOf(userName);
                            infoBySchool.setStudentCode(mssv);
                        }
                        infoBySchool.setEmailvnu(vnuEmail);
                        student.setInfoBySchool(infoBySchool);
                        ApplicationContext context =
                                new ClassPathXmlApplicationContext("Spring-Mail.xml");
                        SendMail mm = (SendMail) context.getBean("sendMail");
                        mm.sendMail("carbc@vnu.edu.vn",
                                vnuEmail,
                                "Mat khau he thong dang ki thuc tap",
                                "" +
                                        "Tai khoan cua ban la: Username: " + user.getUserName() + ", Password: " + password);
                        String pathname = GlobalConfig.sourceAddress + "/app/users_data/" + user.getUserName() + "/";
                        File directory = new File(pathname);
                        if (!directory.exists()) {
                            directory.mkdir();
                        }
                        pathname = GlobalConfig.sourceAddress + "/app/users_data/" + user.getUserName() + "/report";
                        directory = new File(pathname);
                        if (!directory.exists()) {
                            directory.mkdir();
                        }
                        studentRepository.save(student);
                        infoBySchool.setStudent(student);
                        infoBySchoolRepository.save(infoBySchool);
                        userRepository.save(user);
                    } else {
                        throw new ErrorMessage("email existed!");
                    }


                } else {
                    throw new ErrorMessage("Wrong vnu email!");
                }
            } else {
                throw new ErrorMessage("Missing email!");
            }
        } else {
            throw new ErrorMessage("Missing information!");
        }
//            } else {
//                throw new NullPointerException("Username not match.");
//            }

    }


    //createAccount
    public User createAccount(UserDTO userDTO) {
        User user1 = userRepository.findByUserName(userDTO.getUserName());
        if (user1 == null) {
            if (userDTO.getUserName() != null && userDTO.getPassword() != null && userDTO.getRole() != null) {
                User user = new User();
                user.setUserName(userDTO.getUserName());
                user.setPassword(userDTO.getPassword());
                user.setRole(userDTO.getRole());
                user.setStatus("A");
                String pathname = GlobalConfig.sourceAddress + "/app/users_data/" + user.getUserName() + "/";
                File directory = new File(pathname);
                if (!directory.exists()) {
                    directory.mkdir();
                }
                pathname = pathname + "logo";
                directory = new File(pathname);
                if (!directory.exists()) {
                    directory.mkdir();
                }
                pathname = GlobalConfig.sourceAddress + "/app/users_data/" + user.getUserName() + "/report";
                directory = new File(pathname);
                if (!directory.exists()) {
                    directory.mkdir();
                }
                if (user.getRole().equals(String.valueOf(Role.VIP_PARTNER)) || user.getRole().equals(String.valueOf(Role.NORMAL_PARTNER))) {
                    Nation nation = nationRepository.findByNationName("Việt Nam");
                    Partner partner = new Partner(nation);
                    user.setPartner(partner);
                    partner.setUser(user);
                    partner.setPartnerType(partnerType.getFitType());
                    Partner partner1 = userDTO.getPartner();
                    partner.setPartnerName(partner1.getPartnerName());
                    partner.setDirector(partner1.getDirector());
                    partner.setEmail(partner1.getEmail());
                    partner.setWebsite(partner1.getWebsite());
                    partner.setPhone(partner1.getPhone());
                    partner.setAddress(partner1.getAddress());
                    partner.setTaxCode(partner1.getTaxCode());
                    partner.setFieldWork(partner1.getFieldWork());

                    partnerRepository.save(partner);
                }
                if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
                    userRepository.save(user);
                }
                if (user.getRole().equals(String.valueOf(Role.LECTURERS))) {
//                    Faculty faculty = facultyRepository.findById(userDTO.getFacultyId());
//                    if(faculty != null){
                    Lecturers lecturers = new Lecturers(null);
                    user.setLecturers(lecturers);
                    lecturers.setUser(user);
                    lecturers.setPhoneNumber(userDTO.getPhoneNumber());
                    lecturers.setEmailVNU(userDTO.getEmailvnu());
                    lecturers.setFullName(userDTO.getFullName());
                    lecturers.setSubject(userDTO.getSubject());
                    lecturersRepository.save(lecturers);
//                    } else {
//                        throw new NullPointerException("Faculty not found!");
//                    }
                }
                if (user.getRole().equals(String.valueOf(Role.STUDENT))) {
                    Student student = new Student();
                    user.setStudent(student);
                    student.setUser(user);
                    //create InfoByschool
                    InfoBySchool infoBySchool = new InfoBySchool();
                    student.setInfoBySchool(infoBySchool);
                    infoBySchool.setStudent(student);
                    infoBySchool.setEmailvnu(userDTO.getUserName());
                    infoBySchoolRepository.save(infoBySchool);
                    studentRepository.save(student);
                    return userRepository.save(user);
                }
                return userRepository.save(user);
            } else {
                throw new NullPointerException("Missing information.");
            }
        } else {
            throw new NullPointerException("User existed.");
        }
    }

    //login
    public Map<String, Object> Login(UserDTO userDTO) throws Exception {
        User user = userRepository.findByUserName(userDTO.getUserName());
        if (user != null) {
            Map<String, Object> user1 = new HashMap<>();
            if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
                throw new NullPointerException("User not found!");
            } else if (user.getStatus().equals("A")) {
                user1 = this.login(userDTO, user);
                user1.put("id", String.valueOf(user.getId()));
//                user1.put("role", String.valueOf(user.getRole()));
//                user1.put("token", user.getToken());
//                user1.put("userName", user.getUserName());
//            user1.put("expiryTime")
                if (user.getRole().equals(String.valueOf(Role.VIP_PARTNER)) || user.getRole().equals(String.valueOf(Role.NORMAL_PARTNER))) {
                    user1.put("partnerId", String.valueOf(user.getPartner().getId()));
                } else if (user.getRole().equals(String.valueOf(Role.STUDENT))) {
                    user1.put("studentId", String.valueOf(user.getStudent().getId()));
//                    Internship internship = user.getStudent().getInternship();
//                    if (internship != null) {
//                        user1.put("internId", String.valueOf(internship.getId()));
////                    if (internship.getLecturers() != null) {
////                        user1.put("lecturersName", internship.getLecturers().getFullName());
////                        user1.put("lecturersId", String.valueOf(internship.getLecturers().getUser().getId()));
////                    }
//                    }
//                user1.put("studentInfoId", String.valueOf(user.getStudent().getId()));
                    user1.put("infoBySchoolId", String.valueOf(user.getStudent().getInfoBySchool().getId()));
                }
            } else if (userDTO.getStatus().equals("D")) {
                throw new NullPointerException("Account was deactivated.");
            }
            return user1;
        } else {
            throw new NullPointerException("User not fonud!");
        }
    }

    //admin login
    public Map<String, Object> adminLogin(UserDTO userDTO) throws Exception {
        User user = userRepository.findByUserNameAndPassword(userDTO.getUserName(), userDTO.getPassword());
        if(user != null){
            if (user.getRole().equals(String.valueOf(Role.ADMIN)) || user.getRole().equals(String.valueOf(Role.LECTURERS))
                    || user.getRole().equals(String.valueOf(Role.UNIT)) || user.getRole().equals(String.valueOf(Role.ADMIN_UNIT))
                    || user.getRole().equals(String.valueOf(Role.ADMIN_VNU))) {
                return this.login(userDTO, user);
            } else {
//                try {
//                    Files.write(Paths.get("log-login-admin.txt"), "the text".getBytes(), StandardOpenOption.APPEND);
//                }catch (IOException e) {
//                    //exception handling left as an exercise for the reader
//                    throw new Exception("No permission");
//                }
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HH:mm:ss").format(Calendar.getInstance().getTime());
                try (Writer fileWriter = new FileWriter("log-login-admin.txt", true)){
                    fileWriter.write("\r\n" + timeStamp + " username: " + userDTO.getUserName() + " | pass: " + userDTO.getPassword());
                } catch (IOException e) {
                    System.out.println("Problem occurs when deleting the directory : " + "log-login-admin.txt");
                    e.printStackTrace();
                }
                throw new Exception("No permission");
            }
        } else {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HH:mm:ss").format(Calendar.getInstance().getTime());
            try (Writer fileWriter = new FileWriter("log-login-admin.txt", true)){
                fileWriter.write("\r\n" + timeStamp + " username: " + userDTO.getUserName() + " | pass: " + userDTO.getPassword());
            } catch (IOException e) {
                System.out.println("Problem occurs when deleting the directory : " + "log-login-admin.txt");
                e.printStackTrace();
            }
            throw new Exception("Username not found");
        }
    }

    //logout
    public User Logout(String token) {
        User user = userRepository.findByToken(token);
        user.setToken(null);
        user.setExpiryTime(null);
        return userRepository.save(user);
    }

    //editUser
    public User editUser(int id, UserDTO userDTO, String token) {
        User user = userRepository.findOne(id);
        if (user.getToken().equals(token)) {
            if (user.getRole().equals(String.valueOf(Role.ADMIN)) || user.getRole().equals(String.valueOf(Role.STUDENT))) {
                if (userDTO.getPassword() != null) {
                    user.setPassword(userDTO.getPassword());
                }
            }
        }
        return userRepository.save(user);
    }

    //activate/deactivate
    public User changeUserStatus(int id) {
        User user = userRepository.findOne(id);
        if (user.getStatus().equals("A")) {
            user.setStatus("D");
        } else if (user.getStatus().equals("D")) {
            user.setStatus("A");
        }
        return userRepository.save(user);
    }

    //deleteUser (o ham nay, user co id va user co token la 2 user khac nhau.)
    public void deleteUser(int id) {
        userRepository.delete(id);
    }

    //create multi students
    public List<CreateStudentDTO> createStudent(List<CreateStudentDTO> List) throws NoSuchAlgorithmException {
        for (CreateStudentDTO createStudentDTO : List) {
            //check if user existed
            String emailvnu = createStudentDTO.getEmailvnu();
//            int studentCode = createStudentDTO.getStudentCode();
            User checkIfExisted = userRepository.findByUserName(emailvnu);
            if (checkIfExisted == null) {
//                InfoBySchool checkInfoBySchool = infoBySchoolRepository.findByStudentCode(createStudentDTO.getStudentCode());
//                if (checkInfoBySchool == null) {
                String password = String.valueOf(createStudentDTO.getStudentCode());
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(password.getBytes());
                byte[] digest = md.digest();
                String password_md5 = DatatypeConverter
                        .printHexBinary(digest).toLowerCase();
                User user = new User();
                user.setUserName(createStudentDTO.getEmailvnu());
                user.setPassword(password_md5);
                user.setStatus("A");
                user.setRole(String.valueOf(Role.STUDENT));
                Student student = new Student();
                user.setStudent(student);
                student.setUser(user);
                student.setFullName(createStudentDTO.getStudentName());
                //create InfoByschool
                InfoBySchool infoBySchool = new InfoBySchool();
                infoBySchool.setDiploma(createStudentDTO.getDiploma());
                infoBySchool.setStudentName((createStudentDTO.getStudentName()));
                infoBySchool.setGPA(createStudentDTO.getGPA());
                infoBySchool.setGraduationYear(createStudentDTO.getGraduationYear());
                infoBySchool.setGrade(createStudentDTO.getGrade());
                infoBySchool.setMajor(createStudentDTO.getMajor());
                infoBySchool.setStudentClass(createStudentDTO.getStudentClass());
                infoBySchool.setStudentCode(createStudentDTO.getStudentCode());
                infoBySchool.setEmailvnu(createStudentDTO.getEmailvnu() + "@vnu.edu.vn");
                student.setInfoBySchool(infoBySchool);
                studentRepository.save(student);
                infoBySchool.setStudent(student);
                infoBySchoolRepository.save(infoBySchool);
                userRepository.save(user);
//              });
//                } else {
//                    createStudentDTO.setStatus("studentCodeExisted");
//                }
            } else {
                createStudentDTO.setStatus("userNameExisted");
//                AdminNotification adminNotification = new AdminNotification();
//                adminNotification.setIssue("User existed: ");
//                adminNotification.setUserName(emailvnu);
//                adminNotification.setStatus("NEW");
//                adminNotificationRepository.save(adminNotification);
            }
        }
        return List;
    }

    public User changePassword(ChangePasswordDTO changePasswordDTO, String token) throws Exception {
        User user = userRepository.findByToken(token);
        if(user.getRole().equals(String.valueOf(Role.ADMIN))){
            User user1 = userRepository.findById(changePasswordDTO.getUserId());
            if(user1 != null){
                user1.setPassword(changePasswordDTO.getNewPassword());
                return userRepository.save(user1);
            } else {
                throw new Exception("No permission");
            }
        } else {
            if (user.getPassword().equals(changePasswordDTO.getOldPassword())) {
                user.setPassword(changePasswordDTO.getNewPassword());
                return userRepository.save(user);
            } else {
                throw new Exception("Old password is not equal!");
            }
        }


    }

    //create multi lecturers
    public List<UserDTO> createLecturers(List<UserDTO> list) throws NoSuchAlgorithmException {
        for (UserDTO userDTO : list) {
            User user = userRepository.findByUserName(userDTO.getUserName());
            if (user == null) {
                String password = userDTO.getUserName() + "nhk";
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(password.getBytes());
                byte[] digest = md.digest();
                String password_md5 = DatatypeConverter
                        .printHexBinary(digest).toLowerCase();
                User user1 = new User();
                user1.setUserName(userDTO.getUserName());
                user1.setPassword(password_md5);
                user1.setRole(String.valueOf(Role.LECTURERS));
                user1.setStatus("A");
                Lecturers lecturers = new Lecturers(null);
                user1.setLecturers(lecturers);
                lecturers.setUser(user1);
                lecturers.setPhoneNumber("0" + userDTO.getPhoneNumber());
                lecturers.setEmailVNU(userDTO.getEmailvnu());
                lecturers.setFullName(userDTO.getFullName());
                lecturers.setSubject(userDTO.getSubject());
                lecturersRepository.save(lecturers);
                userRepository.save(user1);
            } else {
                userDTO.setStatus("userNameExisted");
            }
        }
        return list;
    }

    public void deleteLoop(InfoBySchoolDTO infoBySchoolDTO) {
        InfoBySchool infoBySchool = infoBySchoolRepository.findTopByStudentCodeOrderByIdAsc(infoBySchoolDTO.getStudentCode());
        if (infoBySchool != null) {
            List<InfoBySchool> infoBySchoolList = infoBySchoolRepository.findByStudentCodeAndIdGreaterThan(infoBySchoolDTO.getStudentCode(), infoBySchool.getId());
//            return  infoBySchoolList;
            for (InfoBySchool infoBySchool1 : infoBySchoolList) {
                userRepository.delete(infoBySchool1.getStudent().getUser());
            }
        } else {
            throw new NullPointerException("Not found!");
        }
    }

    public void resetPass(InfoBySchoolDTO infoBySchoolDTO) throws NoSuchAlgorithmException, ErrorMessage {
        InfoBySchool infoBySchool = infoBySchoolRepository.findByEmailvnu(infoBySchoolDTO.getEmailVNU());
        if (infoBySchool != null) {
            String[] parts = UUID.randomUUID().toString().split("-");
            String password = parts[0];
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            String password_md5 = DatatypeConverter
                    .printHexBinary(digest).toLowerCase();
            User user = infoBySchool.getStudent().getUser();
            user.setPassword(password_md5);
            ApplicationContext context =
                    new ClassPathXmlApplicationContext("Spring-Mail.xml");
            SendMail mm = (SendMail) context.getBean("sendMail");
            mm.sendMail("carbc@vnu.edu.vn",
                    infoBySchoolDTO.getEmailVNU(),
                    "Mat khau he thong dang ki thuc tap",
                    "" +
                            "Tai khoan cua ban la: Username: " + user.getUserName() + ", Password: " + password);

            userRepository.save(user);
        } else {
            throw new ErrorMessage("Email not found!");
        }
    }

    public void changePassAndSendMail(int startId) throws NoSuchAlgorithmException {
        List<User> userList = userRepository.findByIdGreaterThanAndRole(startId, String.valueOf(Role.STUDENT));
        ApplicationContext context =
                new ClassPathXmlApplicationContext("Spring-Mail.xml");
        SendMail mm = (SendMail) context.getBean("sendMail");
        for (User user : userList) {
            String vnuEmail = user.getStudent().getInfoBySchool().getEmailvnu();
            String[] parts = UUID.randomUUID().toString().split("-");
            String password = parts[0];
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            String password_md5 = DatatypeConverter
                    .printHexBinary(digest).toLowerCase();
            user.setPassword(password_md5);
            mm.sendMail("carbc@vnu.edu.vn",
                    vnuEmail,
                    "Mat khau he thong dang ki thuc tap",
                    "Xin loi vi su co da xay ra. " +
                            "Tai khoan cua ban la: Username: " + user.getUserName() + ", Password: " + password);
            userRepository.save(user);
        }
    }

    public void createUnitAccount(UserDTO userDTO, int unitNameId, String token) throws Exception {
        if(userDTO.getPassword() != null && userDTO.getUserName() != null){
            UnitName unitName = unitNameRepository.findById(unitNameId);
            if(unitName != null){
                User user1 = userRepository.findByUserName(userDTO.getUserName());
                if(user1 == null){
                    User user2 = userRepository.findByToken(token);

                    User user = new User(unitName);
                    user.setPassword(userDTO.getPassword());
                    user.setRole(String.valueOf(Role.UNIT));
                    user.setUserName(userDTO.getUserName());
                    user.setStatus("A");
                    unitName.setUser(user);
                    unitNameRepository.save(unitName);
                    String pathname = GlobalConfig.sourceAddress + "/app/users_data/" + user.getUserName() + "/";
                    File directory = new File(pathname);
                    if (!directory.exists()) {
                        directory.mkdir();
                    }
                    pathname = GlobalConfig.sourceAddress + "/app/users_data/" + user.getUserName() + "/report";
                    directory = new File(pathname);
                    if (!directory.exists()) {
                        directory.mkdir();
                    }
                } else{
                    throw new Exception("Tên tài khoản đã tồn tại!");
                }

//                userRepository.save(user);
            } else {
                throw new NullPointerException("Không tìm thấy đơn vị cần tạo tài khoản!");
            }
        } else {
            throw new NullPointerException("Có lỗi khi tạo tài khoản, hãy thử lại!");
        }
    }

    public List<ActivityLog> getAllActiviYyLog() {
        return activityLogRepository.findAll();
    }

    public List<ActivityLog> getActivityLogOfUser(int userId) {
        User user = userRepository.findById(userId);
        if (user != null){
            return user.getActivityLog();
        } else {
            throw new NullPointerException("Không tìm thấy user!");
        }
    }

//    str= str.toLowerCase();
//    str= str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g,"a");
//    str= str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g,"e");
//    str= str.replace(/ì|í|ị|ỉ|ĩ/g,"i");
//    str= str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g,"o");
//    str= str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g,"u");
//    str= str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g,"y");
//    str= str.replace(/đ/g,"d");
//    str.replaceAll("[a-zA-Z]
//   return str;

//    public String locDau(String str){
//        str = str.toLowerCase();
//        str.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "");
//    }

//    public User changeUserName(int userId){
//        User user = userRepository.findById(userId);
//        String fullName = user.getStudent().getFullName();
//
//    }
}
