package uet.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uet.model.Follow;
import uet.model.Internship;
import uet.model.Partner;
import uet.model.Student;

import java.util.List;

/**
 * Created by nhkha on 16/02/2017.
 */
@Repository
public interface FollowRepository extends CrudRepository<Follow,Integer>, PagingAndSortingRepository<Follow, Integer> {
    List<Follow> findByPostId(int postId);
    List<Follow> findByInternshipId(int internId);
    List<Follow> findByStudentId(int id);
    Follow findByStudentIdAndPostId(int studentId, int postId);
    Follow findByStudentIdAndPostIdAndInternshipTerm(int studentId, int postId, int internshipTerm);
    Follow findByStudentIdAndPostTitleAndInternshipTermAndPostId(int studentId, String postTitle, int internshipTerm, int postId);
    List<Follow> findByPartnerId(int partnerId);
    List<Follow> findByStatus (String status);
    List<Follow> findByPostTitle(String string);
    Follow findByStudentAndPartnerId(Student student, int partnerId);
    Follow findByPartnerName(String name);
    List<Follow> findByInternshipTermAndPostIdAndPartner(int internshipTerm, int postId, Partner partner);
    Follow findByStudentAndPostId(Student student, int postId);
    List<Follow> findByInternshipTerm(int id);
    List<Follow> findByInternshipTermAndStudent(int internshipTerm, Student student);
    Follow findByStatusAndStudentAndInternshipTerm(String s, Student student, int i);
    Follow findByStudentAndPartnerIdAndInternshipTerm(Student student, int partnerId, int i);
    Follow findByInternshipIdAndPostId(int internship, int postId);
    Follow findByInternshipIdAndPartnerIdAndStudent(int internId, int partnerId, Student student);
    List<Follow> findByPartnerIdAndInternshipTerm(int partnerId, int internshipTerm);
    Follow findByInternshipIdAndPostIdAndPostTitle(int internship, int postId, String postTitle);
    List<Follow> findByStatusAndInternshipTerm(String s, int i);
    Follow findById(int id);
}
