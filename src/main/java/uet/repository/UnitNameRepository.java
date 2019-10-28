package uet.repository;

import uet.model.UnitName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nhkha on 25/03/2017.
 */
@Repository
public interface UnitNameRepository extends CrudRepository<UnitName, Integer>{
    UnitName findByUnitName (String unitName);
    UnitName findByUnitNameContaining(String s);
    UnitName findById (int id);
    List<UnitName> findByRolesSigningLevelId(int id);
    UnitName findByUnitNameAndRolesSigningLevelId(String name, int id);
    List<UnitName> findByRoles(String roles);

}
