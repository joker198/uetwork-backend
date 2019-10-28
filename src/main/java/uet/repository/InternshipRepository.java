package uet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uet.model.Internship;
import uet.model.InternshipTerm;
import uet.model.Lecturers;
import uet.model.Student;

import java.util.List;

/**
 * Created by fgv on 7/11/2016.
 */
@Repository
public interface InternshipRepository extends CrudRepository<Internship,Integer>, PagingAndSortingRepository<Internship, Integer> {
    Internship findById(int id);
    Internship findByStudentId(int studentId);
//    Internship findByStudentIdAndIn
    Internship findByStudentIdAndInternshipTerm(int student, InternshipTerm internshipTerm);
    List<Internship> findByInternshipTermAndLecturersId(InternshipTerm internshipTerm, int lecturers);
}
