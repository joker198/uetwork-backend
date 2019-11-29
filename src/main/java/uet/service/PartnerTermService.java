package uet.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import uet.repository.InternshipTermRepository;
import uet.repository.PartnerRepository;
import uet.model.PartnerInternshipterm;
import uet.model.Status;
import uet.model.InternshipTerm;
import uet.model.Partner;
import static uet.repository.PartnerRepository.FIND_PARTNER_ID;
/**
 *
 * @author joker
 */
@Service
public class PartnerTermService {
    private PartnerRepository partnerRepository;
    private InternshipTermRepository internshipTermRepository;
    private PartnerInternshipterm partnerInternshipterm;
    @Autowired
    private JdbcTemplate jdbcTemp;
    private InternshipTerm internshipTerm;
    private Partner partner;

    public PartnerTermService()
    {
        //
    }

    public PartnerTermService(
        PartnerRepository partnerRepository,
        InternshipTermRepository internshipTermRepository,
        PartnerInternshipterm partnerInternshipterm,
        InternshipTerm internshipTerm,
        Partner partner
    ) {
        this.partnerRepository = partnerRepository;
        this.internshipTermRepository = internshipTermRepository;
        this.partnerInternshipterm = partnerInternshipterm;
        this.internshipTerm = internshipTerm;
        this.partner = partner;
    }
    
    /**
     * Create PartnerInternshipterm
     * @param internshipTermId
     * @param partnerIds
     */
    public void create(int internshipTermId, JSONArray partnerIds)
    {
        System.out.println(internshipTermId);
        Partner chosenPartner = this.findPartner(1);
        System.out.println(chosenPartner.getId());
        InternshipTerm chosenTerm = internshipTermRepository.findById(internshipTermId);
//        int id; 
//        for (int i = 0; i < partnerIds.length(); i++) {
//            id = partnerIds.getInt(i);
//            Partner chosenPartner = this.partnerRepository.findById(id);
//            PartnerInternshipterm partnerTerm = new PartnerInternshipterm(chosenTerm, chosenPartner);
//            partnerTerm.setStatus((byte)Status.PIT_WAIT.getValue());
//            this.insert(partnerTerm);
//        }
    }
    
    private int insert(PartnerInternshipterm partnerTerm)
    {
        String query = "insert into partner_internshipterm (internshipterm_id, partner_id, status) value(?, ?)";
        return jdbcTemp.update(
                query,
                partnerTerm.getInternshipTerm().getId(),
                partnerTerm.getPartner().getId(),
                partnerTerm.getStatus()
        );
    }
    
    // @Query(value = "SELECT p.id, p.partner_name FROM partner p", nativeQuery = true)
    private Partner findPartner(int id)
    {
        String query = "select * from partner where id = ?";
        return (Partner) jdbcTemp.queryForObject(query, new Object[] { id }, new BeanPropertyRowMapper(Partner.class));
    }
}
