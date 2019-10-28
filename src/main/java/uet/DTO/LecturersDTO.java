package uet.DTO;

/**
 * Created by nhkha on 14/05/2017.
 */
public class LecturersDTO {
    private int id;
    private String fullName;
    private String phoneNumber;
    private String emailVNU;
    private String email;
    private String about;
    private String avatar;
    private String subject; // bo mon

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailVNU() {
        return emailVNU;
    }

    public void setEmailVNU(String emailVNU) {
        this.emailVNU = emailVNU;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
