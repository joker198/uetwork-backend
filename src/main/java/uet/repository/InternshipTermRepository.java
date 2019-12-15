package uet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.model.InternshipTerm;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by nhkha on 14/05/2017.
 */
@Repository
public interface InternshipTermRepository extends CrudRepository<InternshipTerm, Integer> {

    InternshipTerm findByTerm(int term);
    InternshipTerm findById(int id);
    InternshipTerm findTopByOrderByIdDesc();
    InternshipTerm findByTermAndYear(int term, String year);
    List<InternshipTerm> findByYear(String year);
    String FIND_TERM_VALID = "SELECT * FROM internship_term WHERE end_date >= CURRENT_DATE order by year";
    @Query(value = FIND_TERM_VALID, nativeQuery = true)
    List<InternshipTerm> findValids();
}
