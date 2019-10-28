package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="ContractShare")
public class ContractShare {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="contract_id")
    @JsonIgnore
    private Contract contract;

    @ManyToOne
    @JoinColumn(name="unit_name_id")
//    @JsonIgnore
    private UnitName unitName;

    private Date created;
//    private
    public ContractShare (){};

    public ContractShare(Contract contract, UnitName unitName){
        this.contract = contract;
        this.unitName = unitName;
        this.created = new Date();
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public UnitName getUnitName() {
        return unitName;
    }

    public void setUnitName(UnitName unitName) {
        this.unitName = unitName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
