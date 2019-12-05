package uet.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.model.*;
import uet.repository.*;
import org.json.JSONArray;

/**
 * @author joker
 */
@Service
public class PartnerTermService
{
    private final PartnerRepository partnerRepository;
    private final InternshipTermRepository internshipTermRepository;
    private final PartnerInternshiptermRepository partnerInternshiptermRepository;

    @Autowired
    public PartnerTermService (
        PartnerRepository partnerRepository,
        InternshipTermRepository internshipTermRepository,
        PartnerInternshiptermRepository partnerInternshiptermRepository
    ) {
        this.partnerRepository = partnerRepository;
        this.internshipTermRepository = internshipTermRepository;
        this.partnerInternshiptermRepository = partnerInternshiptermRepository;
    }
    
    /**
     * Create PartnerInternshipterm
     * @param internshipTermId
     * @param partnerIds
     * @return 
     */
    public List<Partner> create(int internshipTermId, JSONArray partnerIds)
    {
        InternshipTerm chosenTerm = internshipTermRepository.findById(internshipTermId);
        int id; 
        for (int i = 0; i < partnerIds.length(); i++) {
            id = partnerIds.getInt(i);
            Partner chosenPartner = partnerRepository.findById(id);
            PartnerInternshipterm partnerTerm = new PartnerInternshipterm(chosenTerm, chosenPartner);
            partnerTerm.setStatus((byte)Status.PIT_WAIT.getValue());
            partnerInternshiptermRepository.save(partnerTerm);
        }
        return this.getWaitRecruitPartner(internshipTermId);
    }
    
    /**
     * Get all Partner by termId
     * @param termId
     * @return List Partner
     */
    public List<Partner> getPartnerByTerm(int termId)
    {
        List<Partner> partnerByterm = new ArrayList<>();
        List<PartnerInternshipterm> partnerInternshipterm = partnerInternshiptermRepository.findByInternshipTermId(termId);
        if (partnerInternshipterm != null) {
            for (PartnerInternshipterm pit : partnerInternshipterm) {
                Partner partner = pit.getPartner();
                partnerByterm.add(partner);
            }
        }
        return partnerByterm;
    }
    
    public List<Partner> getWaitRecruitPartner(int termId)
    {
        List<Partner> partners = this.partnerRepository.findAll();
        List<PartnerInternshipterm> termPartners = this.partnerInternshiptermRepository.findByInternshipTermId(termId);
        if (termPartners == null) {
            return partners;
        }
        for(PartnerInternshipterm pit: termPartners) {
            for (int i = 0; i < partners.size(); i++) {
                if (partners.get(i).getId() == pit.getPartner().getId()) {
                    partners.remove(i);
                }
            }
        }
        return partners;
    }
}
