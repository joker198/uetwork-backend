package uet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uet.model.AdminNotification;

import java.util.List;

/**
 * Created by nhkha on 19/02/2017.
 */
@Repository
public interface AdminNotificationRepository extends CrudRepository<AdminNotification, Integer>, PagingAndSortingRepository<AdminNotification, Integer> {
    List<AdminNotification> findByStatus(String status);
    AdminNotification findById(int id);

}
