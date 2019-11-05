package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by nhkha on 7/31/2017.
 */
@Entity
@Table(name="AnnualActivity")
public class AnnualActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String activityName;
    private String funding;
    private String date;
    @Column(name = "content", length  = 2800000)
    private String content;
    private String partnerName;

    public AnnualActivity (){}

    public AnnualActivity(
        String activityName,
        String funding,
        String date,
        String content
    ) {
        this.activityName = activityName;
        this.funding = funding;
        this.date = date;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getFunding() {
        return funding;
    }

    public void setFunding(String funding) {
        this.funding = funding;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
