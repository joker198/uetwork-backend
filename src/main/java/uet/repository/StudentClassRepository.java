package uet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.model.StudentClass;

/**
 * Created by nhkha on 6/16/2017.
 */
@Repository
public interface StudentClassRepository  extends CrudRepository<StudentClass, Integer> {
    StudentClass findByStudentClass(String s);
    StudentClass findById(int id);
}
