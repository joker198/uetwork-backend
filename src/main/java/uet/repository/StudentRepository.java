package uet.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uet.model.Student;

import java.util.List;


/**
 * Created by Tu on 20-May-16.
 */
@Repository
public interface StudentRepository extends CrudRepository<Student, Integer>, PagingAndSortingRepository<Student, Integer> {
    Student findById(int id);

    Student findByJobSkillsId(int jobSkills);

    Student findByInternshipId(int id);

    Student findByInfoBySchoolId(int id);

    List<Student> findByFullNameContaining(String studentName);

    Page<Student> findAllByOrderByIdDesc(Pageable pageable);
//    List<Student> findByLecturersIdIsNull ();
//    List<Student> findByLecturersIdIsNullAndInternshipTermId(int id);
//    List<Student> findByLecturersIdIsNotNullAndInternshipTermId(int id);
//    List<Student> findByInternshipTermIdIsNull();
//    List<Student> findByLecturersIdAndInternshipTermId(int lecturersId, int internshipTermId);
}