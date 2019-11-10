package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by nhkha on 25/03/2017.
 */
@Entity
@Table(name = "continent")
public class Continent
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String continentName;

    @OneToMany(mappedBy = "continent", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Nation> nation;

    public Continent()
    {
        //
    }

    public Continent(String continentName)
    {
        this.continentName = continentName;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getContinentName()
    {
        return continentName;
    }

    public void setContinentName(String continentName)
    {
        this.continentName = continentName;
    }


    public List<Nation> getNation()
    {
        return nation;
    }

    public void setNation(List<Nation> nation)
    {
        this.nation = nation;
    }
}
