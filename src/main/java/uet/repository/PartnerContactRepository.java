package uet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uet.model.Partner;
import uet.model.PartnerContact;

import java.util.List;

/**
 * Created by fgv on 9/2/2016.
 */
@Repository
public interface PartnerContactRepository extends CrudRepository<PartnerContact,Integer>, PagingAndSortingRepository<PartnerContact, Integer> {
    PartnerContact findById(int id);
    List<PartnerContact> findByPartnerId (int id);
}
