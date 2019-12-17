package uet.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        // filter fit partner
        List<Partner> partners = this.partnerRepository.findByStatus((byte)Status.ACCEPTED_PARTNER.getValue());
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
    
    public Map<String, Object> getPartnersSelected(int termId)
    {
        HashMap<String, Object> partnerFollow = new HashMap<>();
        List<Partner> partners = new ArrayList<>();
        
        List<PartnerInternshipterm> partnerInternshipterms = partnerInternshiptermRepository.findByInternshipTermId(termId);
        for (PartnerInternshipterm partnerInternshipterm : partnerInternshipterms) {
            partners.add(partnerInternshipterm.getPartner());
        }
        for (Partner partner : partners) {
            List<Follow> follows = new ArrayList<>();
            for (Post post : partner.getPost()) {
                if (post.getInternshipTerm().getId() != termId) {
                    continue;
                }
                List<Follow> postFollows = post.getFollows();
                for(Follow follow : postFollows) {
                    if (follow.getStatus().equals("SELECTED")) {
                        follows.add(follow);
                    }
                }
            }
            if (follows.size() > 0) {
                partnerFollow.put(partner.getId()+"_"+partner.getPartnerName(), follows);
            }
        }
        return partnerFollow;
    }
}
