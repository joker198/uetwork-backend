package uet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.DTO.AdminNotificationDTO;
import uet.DTO.MessageDTO;
import uet.model.AdminNotification;
import uet.model.Role;
import uet.service.AdminService;
import uet.stereotype.RequiredRoles;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by nhkha on 19/02/2017.
 */
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    //get all notification
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value="/notification/all/admin", method = RequestMethod.GET)
    public List<AdminNotification> getNotification() {
        return adminService.getNotification();
    }

    //get new notification
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value="/notification/{status}/admin", method = RequestMethod.GET)
    public List<AdminNotification> getNewNotification(@PathVariable("status") String status) {
        return adminService.getNewNotification(status);
    }

    //remove notification
    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value="/notification/{id}", method = RequestMethod.POST)
    public void removeNotification(@PathVariable("id") int id) {
        adminService.removeNotification(id);
    }

    //write report
    @RequestMapping(value="/report", method = RequestMethod.PUT)
    public void removeNotification(@RequestBody AdminNotificationDTO adminNotificationDTO, HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        adminService.writeReport(adminNotificationDTO, token);
    }

    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value="data/export", method = RequestMethod.GET)
    public MessageDTO exportSqlData(HttpServletRequest request) throws IOException, InterruptedException {
        String token = request.getHeader("auth-token");
        return adminService.exportSqlData(token);
    }

    @RequiredRoles({Role.ADMIN})
    @RequestMapping(value="get/backupFile", method = RequestMethod.GET)
    public File[] getAllFileInFolder(HttpServletRequest request) throws IOException, InterruptedException {
        String token = request.getHeader("auth-token");
        return adminService.getAllFileInFolder();
    }

    //get class
//    @RequiredRoles(Role.ADMIN)
//    @RequestMapping(value = )
}
