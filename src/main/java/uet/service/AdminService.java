package uet.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.DTO.AdminNotificationDTO;
import uet.DTO.MessageDTO;
import uet.config.GlobalConfig;
import uet.model.AdminNotification;
import uet.model.User;
import uet.repository.AdminNotificationRepository;
import uet.repository.UserRepository;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by nhkha on 19/02/2017.
 */
@Service
public class AdminService {
    @Autowired
    AdminNotificationRepository adminNotificationRepository;
    @Autowired
    UserRepository userRepository;

    //get all
    public List<AdminNotification> getNotification() {
        List<AdminNotification> listNotification = (List<AdminNotification>) adminNotificationRepository.findAll();
        return listNotification;
    }

    public void removeNotification(int id) {
        AdminNotification adminNotification = adminNotificationRepository.findById(id);
        adminNotification.setStatus("SEEN");
        adminNotificationRepository.save(adminNotification);
    }

    //get new noti
    public List<AdminNotification> getNewNotification(String status) {
        List<AdminNotification> newNotification = (List<AdminNotification>) adminNotificationRepository.findAll();
        return newNotification;
    }

    public void writeReport(AdminNotificationDTO adminNotificationDTO, String token) {
        User user = userRepository.findByToken(token);
        AdminNotification adminNotification = new AdminNotification();
        adminNotification.setStatus("NEW");
        if(user.getStudent() == null){
            adminNotification.setPartnetId(user.getPartner().getId());
        } else{
            adminNotification.setStudentId(user.getStudent().getId());
        }
        adminNotification.setIssue(adminNotificationDTO.getIssue() + " from " + user.getUserName());
        adminNotificationRepository.save(adminNotification);
    }

    public MessageDTO exportSqlData(String token) throws IOException, InterruptedException {
        String uid = UUID.randomUUID().toString();
        SimpleDateFormat dMy = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss");
        String date = dMy.format(new Date());
        File directory = new File("./backup-sql/");
        if (!directory.exists()) {
            directory.mkdir();
        }
        String backupPath = directory + "/qly_sv_backup_" + date + "_" + uid + ".sql";
        File path = new File(GlobalConfig.sourceAddress + "/app/users_data/" + token + "/");
        if (!path.exists()) {
            path.mkdir();
        }
        String clientPath = path + "/qly_sv_backup_" + date + "_" + uid + ".sql";
        System.out.print("\n clientPath: " + clientPath + "\n");
        String url = "/users_data/" + token + "/qly_sv_backup_" + date + "_" + uid + ".sql";

        Process p = new ProcessBuilder("mysqldump", "-u", "root", "-p", GlobalConfig.dbName).start();
        ByteArrayOutputStream dmp = new ByteArrayOutputStream();
        InputStream inputStream = p.getInputStream();
        Thread t1 = copyStreamsInBackground(inputStream, dmp);
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        Thread t2 = copyStreamsInBackground(inputStream, err);
        t1.join();
        t2.join();
        int exitCode = p.waitFor();
        if (exitCode != 0) {
            System.err.println("Exit code: " + exitCode);
            String errors = new String(err.toByteArray(), Charset.forName("utf-8"));
            System.err.println(errors);
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setContent("Không thể export dữ liệu");
            p.destroy();
            return messageDTO;
        } else {
            System.out.println("Exit code: " + exitCode);
            String dumps = new String(dmp.toByteArray(), Charset.forName("utf-8"));
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(backupPath))) {
                bw.write(dumps);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(clientPath))) {
                bw.write(dumps);
            } catch (IOException e) {
                e.printStackTrace();
            }
            deleteFile(clientPath);
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setAttachFileAdd(url);
            p.destroy();
            return messageDTO;
        }
    }

    public File[] getAllFileInFolder(){
        File folder = new File("./backup-sql/");
        if (!folder.exists()) {
            folder.mkdir();
        }
        File[] listOfFiles = folder.listFiles();
        return listOfFiles;
    }

    private static Thread deleteFile(final String filePath){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Path path = Paths.get(filePath);
                    TimeUnit.SECONDS.sleep(20);
                    Files.delete(path);
                } catch (IOException | InterruptedException e) {
                }
            }
        });
        thread.start();
        return thread;
    }

    private static Thread copyStreamsInBackground(final InputStream is, final OutputStream os) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    IOUtils.copy(is, os);
                    os.close();
                } catch (IOException ex) {
                    throw new IllegalStateException(ex);
                }
            }
        });
        t.start();
        return t;
    }
}
