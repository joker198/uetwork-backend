package uet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.model.Lecturers;

/**
 * Created by nhkha on 14/05/2017.
 */
@Repository
public interface LecturersRepository extends CrudRepository<Lecturers, Integer> {
    Lecturers findById(int id);
    Lecturers findByEmailVNU(String email);
}
