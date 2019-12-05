package uet.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * @author joker
 */
@Entity
@Table(name = "partner_internshipterm")
public class PartnerInternshipterm implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    private byte status;

    @ManyToOne
    @JoinColumn(name = "internshipterm_id")
    private InternshipTerm internshipTerm;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    public PartnerInternshipterm() {
    }

    public PartnerInternshipterm(InternshipTerm internshipTerm, Partner partner)
    {
        this.internshipTerm = internshipTerm;
        this.partner = partner;
    }

    public Partner getPartner()
    {
        return this.partner;
    }

    public InternshipTerm getInternshipTerm()
    {
        return this.internshipTerm;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}