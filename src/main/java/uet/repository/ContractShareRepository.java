package uet.repository;

import org.springframework.data.repository.CrudRepository;
import uet.model.Contract;
import uet.model.ContractShare;

import java.util.List;

public interface ContractShareRepository extends CrudRepository<ContractShare, Integer> {
    List<ContractShare> findByUnitNameId(int id);
    ContractShare findById(int id);
    List<ContractShare> findByContractId(int id);
    ContractShare findByUnitNameIdAndContractId(int UnitNameId, int ContractId);
}
