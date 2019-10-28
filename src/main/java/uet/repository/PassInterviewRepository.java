package uet.repository;

import org.springframework.data.repository.CrudRepository;
import uet.model.PassInterview;

import java.util.List;

/**
 * Created by nhkha on 17/05/2017.
 */
public interface PassInterviewRepository  extends CrudRepository<PassInterview, Integer> {
    PassInterview findByPartnerIdAndStudentId(int partnerId, int studentId);
    PassInterview findByComfirmationLink(String comfirmationLink);
    List<PassInterview> findByStudentId(int id);
}
