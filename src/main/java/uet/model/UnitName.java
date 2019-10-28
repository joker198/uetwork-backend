package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by nhkha on 25/03/2017.
 */
@Entity
@Table(name = "unit_name")
public class UnitName {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String unitName;
    private String roles;
    private String abbreviation;
//    @OneToMany(mappedBy = "unitName")
//    @JsonIgnore
//    private List<Contract> contract;

    @OneToMany(mappedBy = "unitName")
    @JsonIgnore
    private List<ContractShare> contractShares;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name="roles_signing_level_id")
//    @JsonIgnore
    private RolesAndSigningLevel rolesSigningLevel;

    public UnitName(){

    }

    public UnitName(User user){
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

//    public List<Contract> getContract() {
//        return contract;
//    }
//
//    public void setContract(List<Contract> contract) {
//        this.contract = contract;
//    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ContractShare> getContractShares() {
        return contractShares;
    }

    public void setContractShares(List<ContractShare> contractShares) {
        this.contractShares = contractShares;
    }

    public RolesAndSigningLevel getRolesAndSigningLevel() {
        return rolesSigningLevel;
    }

    public void setRolesAndSigningLevel(RolesAndSigningLevel rolesAndSigningLevel) {
        this.rolesSigningLevel = rolesAndSigningLevel;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
