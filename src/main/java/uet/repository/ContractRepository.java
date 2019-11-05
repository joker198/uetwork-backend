package uet.repository;

import org.springframework.data.jpa.repository.Query;
import uet.model.Contract;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by nhkha on 25/03/2017.
 */
@Repository
public interface ContractRepository extends CrudRepository<Contract, Integer>
{
    String Get_Contract_Of_Unit = "select * from contract LEFT JOIN contract_share on contract.id = " +
        "contract_share.contract_id INNER JOIN unit_name on contract_share.unit_name_id = unit_name.id WHERE unit_name.id= ?1";
    @Query(value = Get_Contract_Of_Unit, nativeQuery = true)
    Map<String, Object> findAllContractOfUnit(int id);
    List<Contract> findByRolesSigningLevelId(int id);
    Contract findById (int id);
}
