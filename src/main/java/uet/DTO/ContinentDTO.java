package uet.DTO;

import uet.model.Nation;

import java.util.Set;

/**
 * Created by nhkha on 25/03/2017.
 */
public class ContinentDTO {
    private int id;
    private String continentName;
    private Set<Nation> nation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public Set<Nation> getNation() {
        return nation;
    }

    public void setNation(Set<Nation> nation) {
        this.nation = nation;
    }
}
