package uet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.model.GradeLevel;
import java.util.List;
/**
 * @author joker
 */
@Repository
public interface GradeLevelRepository extends CrudRepository<GradeLevel, Integer> {
    GradeLevel findById(int id);

    List<GradeLevel> findByCode(String token);

    @Override
    List<GradeLevel> findAll();
}
