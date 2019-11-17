package uet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.model.AnnualActivity;

import java.util.List;

/**
 * Created by nhkha on 7/31/2017.
 */
@Repository
public interface AnnualActivityRepository extends CrudRepository<AnnualActivity, Integer> {
    AnnualActivity findById(int id);
    @Override
    List<AnnualActivity> findAll();
}
