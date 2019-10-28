package uet.repository;

import org.springframework.data.repository.CrudRepository;
import uet.model.RolesAndSigningLevel;

import java.util.List;

public interface RolesAndSigningLevelRepository extends CrudRepository<RolesAndSigningLevel, Integer> {
    RolesAndSigningLevel findByName(String name);
    List<RolesAndSigningLevel> findByRole(String role);
    RolesAndSigningLevel findById(int id);
}
