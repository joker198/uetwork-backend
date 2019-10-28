package uet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uet.model.InfoBySchool;

import java.util.List;

/**
 * Created by Tu on 07-Jul-16.
 */
@Repository
public interface InfoBySchoolRepository extends CrudRepository<InfoBySchool, Integer>, PagingAndSortingRepository<InfoBySchool, Integer> {
    InfoBySchool findById(int id);
    InfoBySchool findByStudentId(int id);
    InfoBySchool findByStudentCode(int id);
    List<InfoBySchool> findByStudentCodeContaining(int studentCode);
    InfoBySchool findTopByStudentCodeOrderByIdAsc(int studentCode);
    List<InfoBySchool> findByStudentCodeAndIdGreaterThan(int studentCode, int id);
    InfoBySchool findByEmailvnu(String email);
}
