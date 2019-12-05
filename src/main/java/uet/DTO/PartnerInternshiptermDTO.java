/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uet.DTO;

import uet.model.InternshipTerm;
import uet.model.Partner;

/**
 *
 * @author joker
 */
public class PartnerInternshiptermDTO {
    private int id;
    private byte status;
    private Partner partner;
    private InternshipTerm internshipTerm;
    public PartnerInternshiptermDTO(){}
    
    public PartnerInternshiptermDTO(int id, byte status){
        this.id = id;
        this.status = status;
    }
    public void setStatus(byte status) {
        this.status = status;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public int getId() {
        return id;
    }

    public byte getStatus() {
        return status;
    }

    public Partner getPartner() {
        return partner;
    }

    public InternshipTerm getInternshipTerm() {
        return internshipTerm;
    }
}
