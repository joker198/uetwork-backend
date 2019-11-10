package uet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.model.StudentClass;
import uet.repository.StudentClassRepository;

import java.util.List;

/**
 * Created by nhkha on 6/16/2017.
 */
@Service
public class StudentClassService {
    @Autowired
    private StudentClassRepository studentClassRepository;

    public List<StudentClass> getAllStudentClass()
    {
        return (List<StudentClass>) studentClassRepository.findAll();
    }

    public void createStudentClass(StudentClass studentClass) throws Exception
    {
        StudentClass studentClass1 = studentClassRepository.findByStudentClass(studentClass.getStudentClass());
        if (studentClass1 == null) {
            StudentClass studentClass2 = new StudentClass(studentClass.getStudentClass(), studentClass.getClassName());
            studentClassRepository.save(studentClass2);
        } else {
            throw new Exception("Class existed!");
        }
    }

    public void editStudentClass(StudentClass studentClass) throws Exception
    {
        StudentClass studentClass1 = studentClassRepository.findById(studentClass.getId());
        if (studentClass1 != null) {
            studentClass1.setClassName(studentClass.getClassName());
            studentClass1.setStudentClass(studentClass.getStudentClass());
            studentClassRepository.save(studentClass1);

        } else {
            throw new Exception("Class not found!");
        }
    }

    public void deleteStudentClass(int studentClassId) throws Exception
    {
        StudentClass studentClass = studentClassRepository.findById(studentClassId);
        if (studentClass != null) {
            studentClassRepository.delete(studentClassId);
        } else {
            throw new Exception("Class not found!");
        }
    }
}
