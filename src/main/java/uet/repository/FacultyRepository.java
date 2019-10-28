package uet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.model.Faculty;

/**
 * Created by nhkha on 14/05/2017.
 */
@Repository
public interface FacultyRepository extends CrudRepository<Faculty, Integer> {
    Faculty findById(int id);
    Faculty findByFacultyName(String facultyName);
}
