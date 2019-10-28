package uet.model;

import javax.persistence.*;

/**
 * Created by nhkha on 6/16/2017.
 */

@Entity
@Table(name="StudentClass")
public class StudentClass {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String studentClass;
    private String className;

    public StudentClass(){};

    public StudentClass(String studentClass, String className){
        this.studentClass = studentClass;
        this.className = className;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
