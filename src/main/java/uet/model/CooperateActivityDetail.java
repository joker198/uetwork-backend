package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="CooperateActivityDetail")
public class CooperateActivityDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String funding;
    private String date;

    @Column(name = "content", length  = 2800000)
    private String content;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cooperate_activity")
    @JsonIgnore
    private CooperateActivity cooperateActivity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public CooperateActivity getCooperateActivity() {
        return cooperateActivity;
    }

    public void setCooperateActivity(CooperateActivity cooperateActivity) {
        this.cooperateActivity = cooperateActivity;
    }

}
