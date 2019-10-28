package uet.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.model.Role;
import uet.model.User;

import java.util.List;


/**
 * Created by Tu on 02-May-16.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    User findByUserName(String userName);
    User findByUserNameAndPassword(String userName, String password);
    User findByToken(String token);
    User findByPartnerId(int id);
    User findByStudentId(int id);
    User findByStudentIdAndStatus(int id, String status);
    List<User> findByRole(String role);
    List<User> findByRoleAndStatus(String role, String status);
    User findById(int id);
    List<User> findByIdGreaterThanAndRole(int id, String role);
    Page<User> findAllByOrderByIdDesc(Pageable pageable);
    List<User> findByUserNameContaining(String userName);
    List<User> findAll();
}
