package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by trung on 2/15/2017.
 */
@Entity
@Table(name="Hashtag")
public class Hashtag
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String tag;


    @ManyToMany(mappedBy = "hashtags")
    @JsonIgnore
    private List<Post> posts = new ArrayList<Post>();

    public List<Post> getPosts()
    {
        return posts;
    }

    public void setPosts(List<Post> posts)
    {
        this.posts = posts;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
