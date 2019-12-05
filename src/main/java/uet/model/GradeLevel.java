package uet.model;

import javax.persistence.*;

/**
 * @author joker
 */

@Entity
@Table(name="GradeLevel")
public class GradeLevel {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String shortName;
    @Column(nullable = false)
    private String code;

    public GradeLevel()
    {
        //
    };

    public GradeLevel(String shortName, String code)
    {
        this.shortName = shortName;
        this.code = code;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }
}
