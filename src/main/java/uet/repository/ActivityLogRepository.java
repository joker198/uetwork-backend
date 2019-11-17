package uet.repository;

import org.springframework.stereotype.Repository;
import uet.model.ActivityLog;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by nhkha on 13/04/2017.
 */
@Repository
public interface ActivityLogRepository extends CrudRepository<ActivityLog, Integer> {
    @Override
    List<ActivityLog> findAll();
}
