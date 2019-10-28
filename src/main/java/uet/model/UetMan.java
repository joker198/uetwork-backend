package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by nhkha on 25/03/2017.
 */
@Entity
@Table(name = "uet_man")
public class UetMan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String uetManName;
    private String about;

//    @OneToMany(mappedBy = "uetMan")
//    @JsonIgnore
//    private List<Contract> contract;


    @ManyToOne
    @JoinColumn(name="roles_signing_level_id")
//    @JsonIgnore
    private RolesAndSigningLevel rolesSigningLevel;

    @ManyToMany(mappedBy = "uetMan")
    @JsonIgnore
    private List<Contract> contract;

    public UetMan(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getUetManName() {
        return uetManName;
    }

    public void setUetManName(String uetManName) {
        this.uetManName = uetManName;
    }


    public List<Contract> getContract() {
        return contract;
    }

    public void setContract(List<Contract> contract) {
        this.contract = contract;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public RolesAndSigningLevel getRolesAndSigningLevel() {
        return rolesSigningLevel;
    }

    public void setRolesAndSigningLevel(RolesAndSigningLevel rolesAndSigningLevel) {
        this.rolesSigningLevel = rolesAndSigningLevel;
    }
}
