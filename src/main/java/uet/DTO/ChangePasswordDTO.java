package uet.DTO;

/**
 * Created by nhkha on 11/03/2017.
 */
public class ChangePasswordDTO {
    private String oldPassword;
    private  String newPassword;
    private int userId;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
