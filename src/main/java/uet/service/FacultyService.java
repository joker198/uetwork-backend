package uet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.DTO.FacultyDTO;
import uet.model.Faculty;
import uet.repository.FacultyRepository;

import java.util.List;

/**
 * Created by nhkha on 14/05/2017.
 */
@Service
public class FacultyService {

    private final
    FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public void createFaculty(FacultyDTO facultyDTO) {
        Faculty faculty = facultyRepository.findByFacultyName(facultyDTO.getFacultyName());
        if(faculty == null){
            Faculty faculty1 = new Faculty(facultyDTO.getFacultyName());
            facultyRepository.save(faculty1);
        } else {
            throw new NullPointerException("Faculty existed!");
        }
    }

    public void editFaculty(FacultyDTO facultyDTO){
        Faculty faculty = facultyRepository.findById(facultyDTO.getId());
        if(faculty != null){
            faculty.setFacultyName(facultyDTO.getFacultyName());
            facultyRepository.save(faculty);
        } else {
            throw new NullPointerException("Faculty not found!");
        }
    }

    public void deleteFaculty(int facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId);
        if(faculty.getLecturers().isEmpty()){
            facultyRepository.delete(faculty);
        } else {
            throw new NullPointerException("Cannot delete! Faculty have some lecturers!");
        }
    }

    public List<Faculty> getAllFaculty() {
        return (List<Faculty>) facultyRepository.findAll();
    }
}
