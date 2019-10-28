package uet.repository;

import uet.model.Nation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by nhkha on 25/03/2017.
 */
@Repository
public interface NationRepository extends CrudRepository<Nation, Integer>{
    Nation findByNationName(String nationName);
//    Iterable<Nation> findAll();
    Nation findById (int id);
}
