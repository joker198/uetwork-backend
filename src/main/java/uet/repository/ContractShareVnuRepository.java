package uet.repository;

import org.springframework.data.repository.CrudRepository;
import uet.model.ContractShareVnu;

import java.util.List;

public interface ContractShareVnuRepository extends CrudRepository<ContractShareVnu, Integer> {
    List<ContractShareVnu> findByRolesSigningLevelId(int id);
    ContractShareVnu findById(int id);
    List<ContractShareVnu> findByContractId(int id);
    ContractShareVnu findByRolesSigningLevelIdAndContractId(int RolesSigningLevelId, int ContractId);
}
