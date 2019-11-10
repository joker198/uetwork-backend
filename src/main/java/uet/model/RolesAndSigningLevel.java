package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rolesAndSigningLevel")
public class RolesAndSigningLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String role;
    private String universityName;

    @ManyToOne
    @JoinColumn(name="parent_id")
    @JsonIgnore
    private RolesAndSigningLevel parent_id;

    @OneToMany(mappedBy = "parent_id", cascade = CascadeType.ALL)
    private List<RolesAndSigningLevel> child;

    @OneToOne(mappedBy = "rolesSigningLevel", cascade = CascadeType.ALL)
    @JsonIgnore
    private UnitName unitName;

    @OneToMany(mappedBy = "rolesSigningLevel", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UetMan> uetManList;

    @OneToMany(mappedBy = "rolesSigningLevel", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Contract> contractList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "rolesSigningLevel")
    @JsonIgnore
    private List<ContractShareVnu> contractShareVnus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UnitName getUnitName() {
        return unitName;
    }

    public void setUnitName(UnitName unitName) {
        this.unitName = unitName;
    }

    public List<RolesAndSigningLevel> getChild() {
        return child;
    }

    public void setChild(List<RolesAndSigningLevel> child) {
        this.child = child;
    }

    public RolesAndSigningLevel getParent_id() {
        return parent_id;
    }

    public void setParent_id(RolesAndSigningLevel parent_id) {
        this.parent_id = parent_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UetMan> getUetManList() {
        return uetManList;
    }

    public void setUetManList(List<UetMan> uetManList) {
        this.uetManList = uetManList;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public List<ContractShareVnu> getContractShareVnus() {
        return contractShareVnus;
    }

    public void setContractShareVnus(List<ContractShareVnu> contractShareVnus) {
        this.contractShareVnus = contractShareVnus;
    }
}
