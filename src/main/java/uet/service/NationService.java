package uet.service;

import uet.DTO.ContinentDTO;
import uet.DTO.NationDTO;
import uet.model.*;
import uet.repository.ActivityLogRepository;
import uet.repository.ContinentRepository;
import uet.repository.NationRepository;
import uet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by nhkha on 25/03/2017.
 */
@Service
public class NationService {
    private final
    ContinentRepository continentRepository;

    private final
    NationRepository nationRepository;

    private final
    UserRepository userRepository;

    private final
    ActivityLogRepository activityLogRepository;

    @Autowired
    public NationService(ContinentRepository continentRepository, NationRepository nationRepository, UserRepository userRepository, ActivityLogRepository activityLogRepository) {
        this.continentRepository = continentRepository;
        this.nationRepository = nationRepository;
        this.userRepository = userRepository;
        this.activityLogRepository = activityLogRepository;
    }
    public Continent createContinent(ContinentDTO continentDTO, String token) {
        Continent continent = continentRepository.findByContinentName(continentDTO.getContinentName());
        if(continent == null){
            Continent newContinent = new Continent();
            newContinent.setContinentName(continentDTO.getContinentName());
            User user = userRepository.findByToken(token);
            if(user.getRole().equals(Role.UNIT)){
                ActivityLog activityLog = new ActivityLog(user);
                userRepository.save(user);
                activityLog.setActivityType("createContinent");
                activityLog.setAcvtivity(user.getUnitName().getUnitName() + " tạo thêm Châu lục " +
                        continentDTO.getContinentName() + " vào lúc " + activityLog.getTimestamp());
                activityLog.setStatus("NEW");
                activityLogRepository.save(activityLog);
            }
            return continentRepository.save(newContinent);
        } else{
            throw new NullPointerException("Châu lục đã tồn tại!");
        }
    }

    public Nation createNation(NationDTO nationDTO, int continentId, String token){
//        if(user.getRole().equals(Role.ADMIN))
        Continent continent = continentRepository.findOne(continentId);
        if(continent == null){
            throw new NullPointerException("Châu lục không tồn tại");
        } else{
//            List<Nation> setNation = new ArrayList<Nation>();
//            add(new Book("Book B1", categoryB));
//            for(NationDTO nationDTO: listNationDTO){
                if(nationRepository.findByNationName(nationDTO.getNationName()) == null){
                    Nation nation = new Nation(nationDTO.getNationName(), continent);
                    User user = userRepository.findByToken(token);
                    if(user.getRole().equals(Role.UNIT)){
                        ActivityLog activityLog = new ActivityLog(user);
                        userRepository.save(user);
                        activityLog.setActivityType("createNation");
                        activityLog.setAcvtivity(user.getUnitName().getUnitName() + " tạo thêm Quốc gia " +
                                nationDTO.getNationName() + " vào lúc " + activityLog.getTimestamp());
                        activityLog.setStatus("NEW");
                        activityLogRepository.save(activityLog);
                    }
                    return nationRepository.save(nation);
                } else{
                    throw new NullPointerException("Quốc gia đã tồn tại!");
                }
        }
    }

    public List<Continent> getAllContinent(){
//        List<User> allUsers = (List<User>)
        return (List<Continent>) continentRepository.findAll();
    }

    public void deleteNation(int nationId, String token) {
        Nation nation = nationRepository.findById(nationId);
        if(nation != null){
            if (nation.getPartner().isEmpty()) {
                nationRepository.delete(nationId);
                User user = userRepository.findByToken(token);
                if(user.getRole().equals(Role.UNIT)){
                    ActivityLog activityLog = new ActivityLog(user);
                    userRepository.save(user);
                    activityLog.setActivityType("deleteNation");
                    activityLog.setAcvtivity(user.getUnitName().getUnitName() + " xóa Quốc gia " +
                            nation.getNationName() + " vào lúc " + activityLog.getTimestamp());
                    activityLog.setStatus("NEW");
                    activityLogRepository.save(activityLog);
                }
            } else {
                throw new NullPointerException("Không thể xóa Quốc gia này vì có 1 số doanh nghiệp thuộc quốc gia này!");
            }
        } else{
            throw new NullPointerException("Không tim thấy quốc gia cần được xóa!");
        }
    }

    public void editNation(NationDTO nationDTO, String token) {
                Nation nation = nationRepository.findOne(nationDTO.getId());
                if (nation != null) {
                    if (nationDTO.getNationName() != null) {
                        nation.setNationName(nationDTO.getNationName());
                        nationRepository.save(nation);
                        User user = userRepository.findByToken(token);
                        if(user.getRole().equals(Role.UNIT)){
                            ActivityLog activityLog = new ActivityLog(user);
                            userRepository.save(user);
                            activityLog.setActivityType("editNation");
                            activityLog.setAcvtivity(user.getUnitName().getUnitName() + " sửa tên Quốc gia: " +
                                    nation.getNationName() + " thành: " + nationDTO.getNationName() + " vào lúc " + activityLog.getTimestamp());
                            activityLog.setStatus("NEW");
                            activityLogRepository.save(activityLog);
                        }
                    }
        } else {
            throw new NullPointerException("Có lỗi khi xảy ra, hãy thử lại reload trang và thử lại!");
        }
    }

    public void editContinent(ContinentDTO continentDTO, String token) {
        Continent continent = continentRepository.findOne(continentDTO.getId());
        if (continent != null) {
            if (continentDTO.getContinentName() != null) {
                continent.setContinentName(continentDTO.getContinentName());
                continentRepository.save(continent);
                User user = userRepository.findByToken(token);
                if(user.getRole().equals(Role.UNIT)){
                    ActivityLog activityLog = new ActivityLog(user);
                    userRepository.save(user);
                    activityLog.setActivityType("editContinent");
                    activityLog.setAcvtivity(user.getUnitName().getUnitName() + " sửa tên Châu lục: " +
                            continent.getContinentName() + " thành: " + continentDTO.getContinentName() + " vào lúc " + activityLog.getTimestamp());
                    activityLog.setStatus("NEW");
                    activityLogRepository.save(activityLog);
                }
            } else {
                throw new NullPointerException("Tên Quốc gia trống!");
            }
        } else {
            throw new NullPointerException("Không tìm thấy quốc gia, hãy reload lại trang và thử lại");
        }
    }

    public void deleteContinent(int continentId, String token) {
        Continent continent = continentRepository.findOne(continentId);
        if(continent != null){
            if (continent.getNation().isEmpty()) {
                continentRepository.delete(continentId);
                User user = userRepository.findByToken(token);
                if(user.getRole().equals(Role.UNIT)){
                    ActivityLog activityLog = new ActivityLog(user);
                    userRepository.save(user);
                    activityLog.setActivityType("deleteContinent");
                    activityLog.setAcvtivity(user.getUnitName().getUnitName() + " xóa Châu lục: " +
                            continent.getContinentName() + " vào lúc " + activityLog.getTimestamp());
                    activityLog.setStatus("NEW");
                    activityLogRepository.save(activityLog);
                }
            } else {
                throw new NullPointerException("Không thể xóa Châu lục này vì có 1 số quốc gia thuộc châu lục này!");
            }
        } else{
            throw new NullPointerException("Không tim thấy Châu lục cần được xóa!");
        }
    }

    public List<Nation> getAllNation() {
        return (List<Nation>) nationRepository.findAll();
    }
}
