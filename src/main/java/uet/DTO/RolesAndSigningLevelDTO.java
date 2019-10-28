package uet.DTO;

import uet.model.RolesAndSigningLevel;
import uet.model.UnitName;

import java.util.List;

public class RolesAndSigningLevelDTO {
    private int id;
    private String name;
    private RolesAndSigningLevel child;
    private RolesAndSigningLevel parent;
    private List<UnitName> unitNames;

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

    public RolesAndSigningLevel getChild() {
        return child;
    }

    public void setChild(RolesAndSigningLevel child) {
        this.child = child;
    }

    public RolesAndSigningLevel getParent() {
        return parent;
    }

    public void setParent(RolesAndSigningLevel parent) {
        this.parent = parent;
    }

    public List<UnitName> getUnitNames() {
        return unitNames;
    }

    public void setUnitNames(List<UnitName> unitNames) {
        this.unitNames = unitNames;
    }
}
