package uet.DTO;

import uet.model.GradeLevel;

/**
 * @author joker
 */
public class GradeLevelDTO {
    private int id;
    private String code;
    private String shortName;
    private GradeLevel gradeLevel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public GradeLevel getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public GradeLevelDTO()
    {
        //
    }
}
