package uet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.DTO.InternshipTermDTO;
import uet.model.InternshipTerm;
import uet.repository.InternshipTermRepository;

import java.util.List;

/**
 * Created by nhkha on 14/05/2017.
 */
@Service
public class InternshipTermService {
    private final InternshipTermRepository internshipTermRepository;
    private final PartnerService partnerService;

    @Autowired
    public InternshipTermService(
        InternshipTermRepository internshipTermRepository,
        PartnerService partnerService
    ) {
        this.internshipTermRepository = internshipTermRepository;
        this.partnerService = partnerService;
    }

    public void createInternshipTerm(InternshipTermDTO internshipTermDTO)
    {
        List<InternshipTerm> internshipTerm2 = internshipTermRepository.findByYear(internshipTermDTO.getYear());
        if(internshipTerm2.isEmpty()){
            InternshipTerm internshipTerm = new InternshipTerm(internshipTermDTO.getYear(), 1,
                    0, 0, internshipTermDTO.getStartDate(), internshipTermDTO.getEndDate(), internshipTermDTO.getExpiredDate());
            internshipTermRepository.save(internshipTerm);
            partnerService.resetStatusPartnerType();
        } else {
            InternshipTerm internshipTerm1 = internshipTermRepository.findTopByOrderByIdDesc();
            int term = internshipTerm1.getTerm();
            InternshipTerm internshipTerm = new InternshipTerm(internshipTermDTO.getYear(), term + 1,
                    0, 0, internshipTermDTO.getStartDate(), internshipTermDTO.getEndDate(), internshipTermDTO.getExpiredDate());
            partnerService.resetStatusPartnerType();
            internshipTermRepository.save(internshipTerm);
        }
    }

    public void deleteInternshipTerm(int internshipTermId)
    {
        InternshipTerm internshipTerm = internshipTermRepository.findById(internshipTermId);
        if (internshipTerm.getPosts().isEmpty()) {
            internshipTermRepository.delete(internshipTerm);
        } else {
            throw new NullPointerException("Cannot delete this internship Term!");
        }
    }

    public void editInternshipTerm(InternshipTermDTO internshipTermDTO)
    {
        InternshipTerm internshipTerm = internshipTermRepository.findById(internshipTermDTO.getId());
        if (internshipTerm != null) {
            internshipTerm.setStartDate(internshipTermDTO.getStartDate());
            internshipTerm.setEndDate(internshipTermDTO.getEndDate());
            internshipTerm.setExpiredDate(internshipTermDTO.getExpiredDate());
            internshipTermRepository.save(internshipTerm);
        } else {
            throw new NullPointerException("Internship not found!");
        }
    }

    public List<InternshipTerm> getAllInternshipTerm()
    {
        return (List<InternshipTerm>) internshipTermRepository.findAll();
    }
}
