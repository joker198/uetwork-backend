package uet.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uet.DTO.ChangePasswordDTO;
import uet.DTO.CreateStudentDTO;
import uet.DTO.InfoBySchoolDTO;
import uet.DTO.UserDTO;
import uet.model.ActivityLog;
import uet.model.Role;
import uet.model.User;
import uet.service.UserService;
import uet.stereotype.NoAuthentication;
import uet.stereotype.RequiredRoles;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tu on 02-May-16.
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Get all users
     *
     * @param pageable
     * @return 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value="/user",method = RequestMethod.GET)
    public Page<User> getAllUser(org.springframework.data.domain.Pageable pageable){
        return userService.getUsers(pageable);
    }

    /**
     * Sign up
     * 
     * @param userDTO
     * @throws NoSuchAlgorithmException
     * @throws uet.service.UserService.ErrorMessage 
     */
    @NoAuthentication
    @RequestMapping(value="/signup",method = RequestMethod.PUT)
    public void createUser(@RequestBody UserDTO userDTO) throws NoSuchAlgorithmException, UserService.ErrorMessage {
        userService.createUser(userDTO);
    }

    /**
     * Reset Password
     *
     * @param infoBySchoolDTO
     * @throws NoSuchAlgorithmException
     * @throws uet.service.UserService.ErrorMessage 
     */
    @NoAuthentication
    @RequestMapping(value="/resetPass",method = RequestMethod.PUT)
    public void resetPass(@RequestBody InfoBySchoolDTO infoBySchoolDTO) throws NoSuchAlgorithmException, UserService.ErrorMessage {
        userService.resetPass(infoBySchoolDTO);
    }

    /**
     * Create folder
     */
    @NoAuthentication
    @RequestMapping(value="/createFolder", method = RequestMethod.POST)
    public void createFolder() {
        userService.createFolder();
    }

    /**
     * Create students
     *
     * @param List
     * @return
     * @throws NoSuchAlgorithmException 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value="/createStudent", method = RequestMethod.POST)
    public List<CreateStudentDTO> createStudent(@RequestBody List<CreateStudentDTO> List) throws NoSuchAlgorithmException {
        return userService.createStudent(List);
    }

    /**
     * Change Password and send mail
     *
     * @param startId
     * @throws NoSuchAlgorithmException 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "changePass/email/{startId}", method = RequestMethod.POST)
    public void changePassAndSendMail(@PathVariable("startId") int startId) throws NoSuchAlgorithmException {
        userService.changePassAndSendMail(startId);
    }

    /**
     * 
     * @param infoBySchoolDTO 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "delete/loop", method = RequestMethod.POST)
    public void deleteLoop(@RequestBody InfoBySchoolDTO infoBySchoolDTO){
        userService.deleteLoop(infoBySchoolDTO);
    }

    /**
     * Create Lecturers
     *
     * @param list
     * @return
     * @throws NoSuchAlgorithmException 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value = "createLecturers", method = RequestMethod.POST)
    public List<UserDTO> createLecturers(@RequestBody List<UserDTO> list) throws NoSuchAlgorithmException {
        return userService.createLecturers(list);
    }

    /**
     * Create Account
     *
     * @param userDTO
     * @return 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value="/createAccount",method = RequestMethod.POST)
    public User createAccount(@RequestBody UserDTO userDTO) {
        return userService.createAccount(userDTO);
    }

    /**
     * Login
     *
     * @param userDTO
     * @return
     * @throws Exception 
     */
    @NoAuthentication
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public Map<String, Object> Login(@RequestBody UserDTO userDTO) throws Exception {
        return userService.Login(userDTO);
    }

    /**
     * Administrator Login
     *
     * @param userDTO
     * @return
     * @throws Exception 
     */
    @NoAuthentication
    @RequestMapping(value="admin/login", method = RequestMethod.POST)
    public Map<String, Object> adminLogin(@RequestBody UserDTO userDTO) throws Exception {
        return userService.adminLogin(userDTO);
    }

    /**
     * Logout
     *
     * @param request
     * @return 
     */
    @NoAuthentication
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public User Logout(HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return userService.Logout(token);
    }

    /**
     * Edit User
     *
     * @param id
     * @param userDTO
     * @param request
     * @return 
     */
    @RequiredRoles({Role.ADMIN,Role.STUDENT, Role.VIP_PARTNER})
    @RequestMapping(value="user/{id}", method = RequestMethod.PUT)
    public User editUser(@PathVariable("id") int id, @RequestBody UserDTO userDTO, HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        return userService.editUser(id, userDTO, token);
    }

    /**
     * Change Password
     *
     * @param changePasswordDTO
     * @param request
     * @return
     * @throws Exception 
     */
    @RequestMapping(value="changePassword", method = RequestMethod.PUT)
    public User changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        return userService.changePassword(changePasswordDTO, token);
    }

    /**
     * Change User Status
     *
     * @param id
     * @return 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value="user/{id}/status", method = RequestMethod.PUT)
    public User changeUserStatus(@PathVariable("id") int id) {
        return userService.changeUserStatus(id);
    }

    /**
     * Delete User
     *
     * @param id 
     */
    @RequiredRoles(Role.ADMIN)
    @RequestMapping(value="user/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") int id){
        userService.deleteUser(id);
    }

    /**
     * Create Unit Account
     *
     * @param userDTO
     * @param unitNameId
     * @param request
     * @throws Exception 
     */ 
    @RequiredRoles({Role.ADMIN_UNIT, Role.ADMIN_VNU})
    @RequestMapping(value = "unit/{unitNameId}/account/create", method = RequestMethod.POST)
    public void createUnitAccount(@RequestBody UserDTO userDTO, @PathVariable("unitNameId") int unitNameId,
                                  HttpServletRequest request) throws Exception {
        String token = request.getHeader("auth-token");
        userService.createUnitAccount(userDTO, unitNameId, token);
    }

    /**
     * Check All Activity Log
     *
     * @return 
     */
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "activityLog", method = RequestMethod.GET)
    public List<ActivityLog> getAllActiviYyLog(){
        return userService.getAllActiviYyLog();
    }

    /**
     * Get Activity Log One User
     * 
     * @param userId
     * @return 
     */
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "activityLog/{userId}", method = RequestMethod.GET)
    public List<ActivityLog> getActivityLogOfUser(@PathVariable("userId") int userId){
        return userService.getActivityLogOfUser(userId);
    }

    /**
     * Search User
     *
     * @param userName
     * @return
     * @throws Exception 
     */
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value = "user/search/{userName}", method = RequestMethod.GET)
    public List<User> findUserByUserNameContaining(@PathVariable("userName") String userName) throws Exception {
        return userService.findUserByUserNameContaining(userName);
    }

}
