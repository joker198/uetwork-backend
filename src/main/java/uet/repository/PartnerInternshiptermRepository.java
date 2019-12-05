package uet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.model.PartnerInternshipterm;

import java.util.List;

/**
 *
 * @author joker
 */
@Repository
public interface PartnerInternshiptermRepository extends CrudRepository<PartnerInternshipterm, Integer>
{
    PartnerInternshipterm findById(int id);
    List<PartnerInternshipterm> findByPartnerId(int id);
    List<PartnerInternshipterm> findByInternshipTermId(int id);
    List<PartnerInternshipterm> findByStatus(String status);
    @Override
    List<PartnerInternshipterm> findAll();
}
