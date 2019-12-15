package uet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import uet.model.GradeLevel;

/**
 *
 * @author joker
 */
@Repository
public interface GradeLevelRepository extends CrudRepository<GradeLevel, Integer>
{
    GradeLevel findById(int id);
    @Override
    List<GradeLevel> findAll();
    String FIND_TERM_VALID = "SELECT * FROM grade_level ORDER BY short_name DESC";
    @Query(value = FIND_TERM_VALID, nativeQuery = true)
    List<GradeLevel> findAllOrderByShortNameDesc();
}