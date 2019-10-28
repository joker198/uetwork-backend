package uet.DTO;

import uet.model.Post;

import java.util.List;

/**
 * Created by nhkha on 14/05/2017.
 */
public class InternshipTermDTO {
    private int id;
    private String year;
    private int term;
    private String startDate;
    private String endDate;
    private int internshipCount;
    private int postCount;
    private String expiredDate;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInternshipCount() {
        return internshipCount;
    }

    public void setInternshipCount(int internshipCount) {
        this.internshipCount = internshipCount;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }
}
