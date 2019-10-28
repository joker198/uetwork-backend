package uet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.DTO.PartnerContactDTO;
import uet.model.*;
import uet.repository.PartnerContactRepository;
import uet.repository.PartnerRepository;
import uet.repository.UserRepository;

import java.util.List;

/**
 * Created by fgv on 9/2/2016.
 */
@Service
public class PartnerContactService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PartnerRepository partnerRepository;
    @Autowired
    private PartnerContactRepository partnerContactRepository;

    //show all partner contact
    public List<PartnerContact> getpartnerContacts() {
        List<PartnerContact> allPartners = (List<PartnerContact>) partnerContactRepository.findAll();
        return allPartners;
    }

    //show list post of a partner
    public List<PartnerContact> showAllContact(int partnerId) {
        Partner partner = partnerRepository.findById(partnerId);
        return partner.getPartnerContacts();
    }

    //show a partner contact
    public PartnerContact showContact(int partnerContactId) {
        PartnerContact partnerContact = partnerContactRepository.findOne(partnerContactId);
        return partnerContact;
    }

    //create a partner contact
    public PartnerContact createContact(int partnerId, PartnerContactDTO partnerContactDTO, String token) {
        User user = userRepository.findByToken(token);

        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            Partner partner = partnerRepository.findOne(partnerId);
            PartnerContact partnerContact = new PartnerContact();
            partnerContact.setAddress(partnerContactDTO.getAddress());
            partnerContact.setPartner(partner);
            partnerContact.setContactName(partnerContactDTO.getContactName());
            partnerContact.setEmail(partnerContactDTO.getEmail());
            partnerContact.setSkype(partnerContactDTO.getSkype());
            partnerContact.setPhone(partnerContactDTO.getPhone());
            partnerContact.setAbout(partnerContactDTO.getAbout());
//            partner.getPartnerContacts().add(partnerContact);
            return partnerContactRepository.save(partnerContact);
        } else {
            Partner partner = user.getPartner();
            PartnerContact partnerContact = new PartnerContact();
            partnerContact.setAddress(partnerContactDTO.getAddress());
            partnerContact.setPartner(partner);
            partnerContact.setContactName(partnerContactDTO.getContactName());
            partnerContact.setEmail(partnerContactDTO.getEmail());
            partnerContact.setSkype(partnerContactDTO.getSkype());
            partnerContact.setPhone(partnerContactDTO.getPhone());
            partnerContact.setAbout(partnerContactDTO.getAbout());
//            partner.getPartnerContacts().add(partnerContact);
            return partnerContactRepository.save(partnerContact);
        }

    }

    //edit contact of a partner
    public PartnerContact editContact(int partnerContactId, PartnerContactDTO partnerContactDTO, String token) {
        User user = userRepository.findByToken(token);
        PartnerContact partnerContact = partnerContactRepository.findOne(partnerContactId);
        if(user.getRole().equals(Role.ADMIN)){
            partnerContact.setAddress(partnerContactDTO.getAddress());
            partnerContact.setContactName(partnerContactDTO.getContactName());
            partnerContact.setEmail(partnerContactDTO.getEmail());
            partnerContact.setSkype(partnerContactDTO.getSkype());
            partnerContact.setAbout(partnerContactDTO.getAbout());
            partnerContact.setPhone(partnerContactDTO.getPhone());
            return partnerContactRepository.save(partnerContact);
        } else {
            Partner partner1 = partnerRepository.findByPartnerContactsId(partnerContactId);
            if (user.getPartner().equals(partner1)) {
                partnerContact.setAddress(partnerContactDTO.getAddress());
                partnerContact.setContactName(partnerContactDTO.getContactName());
                partnerContact.setEmail(partnerContactDTO.getEmail());
                partnerContact.setSkype(partnerContactDTO.getSkype());
                partnerContact.setAbout(partnerContactDTO.getAbout());
                partnerContact.setPhone(partnerContactDTO.getPhone());
                return partnerContactRepository.save(partnerContact);
            } else {
                throw new NullPointerException("User doesn't match with Partner");
            }
        }

    }

    //delete contact of a partner
    public void deleteContact(int partnerContactId, String token) {
        User user = userRepository.findByToken(token);
        PartnerContact partnerContact = partnerContactRepository.findOne(partnerContactId);
        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            if(partnerContact.getPost().isEmpty()){
                partnerContactRepository.delete(partnerContact);
            } else {
                throw new NullPointerException("Liên hệ có 1 số bài post nên không thể xóa!");
            }

        } else {
            Partner partner = partnerRepository.findByPartnerContactsId(partnerContactId);
            if (user.getPartner().equals(partner)) {
                partnerContactRepository.delete(partnerContact);
            } else {
                throw new NullPointerException("User doesn't match with Partner");
            }
        }
    }
}
