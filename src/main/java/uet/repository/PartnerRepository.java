package uet.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uet.DTO.PartnerDTO;
import org.springframework.data.jpa.repository.Query;
import uet.model.Partner;

import java.util.List;

/**
 * Created by Tu on 03-May-16.
 */
@Repository
public interface PartnerRepository extends JpaRepository<Partner,Integer>, PagingAndSortingRepository<Partner, Integer> {
    String FIND_PARTNER_ID = "SELECT p.id, p.partner_name FROM partner p";
    String FIND_PARTNER_ID_OTHER = "SELECT p.id, p.partner_name FROM partner p where p.partner_type='OTHER'";

    String FIND_PARTNER_ID_FIT = "SELECT p.id, p.partner_name FROM partner p where p.partner_type='FIT'";

    @Query(value = FIND_PARTNER_ID, nativeQuery = true)
    List<PartnerDTO> findPartnerNameAndId();

    @Query(value = FIND_PARTNER_ID_OTHER, nativeQuery = true)
    List<PartnerDTO> findPartnerNameAndIdOfOther();

    @Query(value = FIND_PARTNER_ID_FIT, nativeQuery = true)
    List<PartnerDTO> findPartnerNameAndIdOfFit();

    Partner findById(int id);
    Partner findByPartnerContactsId(int partnerContactId);
    List<Partner> findByPartnerNameContaining(String partnerName);
    List<Partner> findByUserIsNull();
    List<Partner> findByUserIsNotNull();
    List<Partner> findByStatus(String s);
    List<Partner> findByPartnerType(String s);
    Partner findByPartnerName (String string);
}



