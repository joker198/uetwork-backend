package uet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by nhkha on 14/05/2017.
 */
@Entity
@Table(name = "Faculty")
public class Faculty {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Lecturers> lecturers;

    private String facultyName;

    public Faculty(){

    }

    public Faculty(String facultyName){
        this.facultyName = facultyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Lecturers> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturers> lecturers) {
        this.lecturers = lecturers;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}
