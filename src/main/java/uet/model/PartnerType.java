package uet.model;

import org.springframework.stereotype.Service;

@Service
public class PartnerType {

    public String getOtherType() {
        return "OTHER";
    }

    public String getFitType() {
        return "FIT";
    }

}
