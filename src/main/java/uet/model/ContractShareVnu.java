package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="contractShareVnu")
public class ContractShareVnu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="contract_id")
    @JsonIgnore
    private Contract contract;

    @ManyToOne
    @JoinColumn(name="roles_signing_level_id")
//    @JsonIgnore
    private RolesAndSigningLevel rolesSigningLevel;

    private Date created;

    public ContractShareVnu(){}

    public ContractShareVnu(Contract contract, RolesAndSigningLevel rolesAndSigningLevel) {
        this.contract = contract;
        this.rolesSigningLevel = rolesAndSigningLevel;
        this.created = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public RolesAndSigningLevel getRolesSigningLevel() {
        return rolesSigningLevel;
    }

    public void setRolesSigningLevel(RolesAndSigningLevel rolesSigningLevel) {
        this.rolesSigningLevel = rolesSigningLevel;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
