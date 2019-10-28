package uet.repository;

import uet.model.UetMan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nhkha on 25/03/2017.
 */
@Repository
public interface UetManRepository extends CrudRepository<UetMan, Integer>{
    UetMan findByUetManName (String uetManName);
    UetMan findByUetManNameContaining(String s);
    UetMan findById (int id);
    List<UetMan> findByRolesSigningLevelId (int id);
    UetMan findByUetManNameAndRolesSigningLevelId(String name, int id);
}
