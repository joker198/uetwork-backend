package uet.DTO;

import uet.model.Continent;
import uet.model.Partner;

/**
 * Created by nhkha on 25/03/2017.
 */
public class NationDTO {
    private int id;
    private String nationName;
    private Partner partner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
}
