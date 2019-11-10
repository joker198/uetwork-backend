package uet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.DTO.JobSkillDTO;
import uet.model.*;
import uet.repository.*;

import java.util.List;

/**
 * Created by fgv on 7/8/2016.
 */
@Service
public class JobSkillService
{
    private final JobSkillRepository jobSkillRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @Autowired
    public JobSkillService(
        JobSkillRepository jobSkillRepository,
        StudentRepository studentRepository,
        UserRepository userRepository
    ) {
        this.jobSkillRepository = jobSkillRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    public List<JobSkill> getJobSkills()
    {
        List<JobSkill> getAll = (List<JobSkill>) jobSkillRepository.findAll();
        return getAll;
    }

    public List<JobSkill> getallInStudent(int studentId)
    {
        Student student = studentRepository.findById(studentId);
        return student.getJobSkills();
    }

    public JobSkill createJs(int studentId,JobSkillDTO jobSkillDTO,String token)
    {
        User user = userRepository.findByToken(token);
        Student student = studentRepository.findById(studentId);
        if(user.getStudent().equals(student)) {
            JobSkill jobSkill = new JobSkill();
            jobSkill.setCompany(jobSkillDTO.getCompany());
            jobSkill.setSkill(jobSkillDTO.getSkill());
            jobSkill.setUpdateTime(jobSkillDTO.getUpdateTime());
            jobSkill.setStudent(student);
            jobSkill.setStartDate(jobSkillDTO.getStartDate());
            jobSkill.setEndDate(jobSkillDTO.getEndDate());
            jobSkill.setAbout(jobSkillDTO.getAbout());
            jobSkill.setRating(jobSkillDTO.getRating());
            jobSkill.setStudent(student);
            student.getJobSkills().add(jobSkill);
            return jobSkillRepository.save(jobSkill);
        }
        else {
            throw new NullPointerException("User doesn't match with Student");
        }
    }

    public JobSkill showJobSkill(int id)
    {
        JobSkill  jobSkill = jobSkillRepository.findById(id);
        return jobSkill;
    }

    public JobSkill ChangeJsById(int id, JobSkillDTO jobSkillDTO, String token)
    {
        User user = userRepository.findByToken(token);
        JobSkill jobSkill = jobSkillRepository.findById(id);
        Student student = studentRepository.findByJobSkillsId(id);
        if (user.getStudent().equals(student)) {
            jobSkill.setCompany(jobSkillDTO.getCompany());
            jobSkill.setUpdateTime(jobSkillDTO.getUpdateTime());
            jobSkill.setSkill(jobSkillDTO.getSkill());
            jobSkill.setStartDate(jobSkillDTO.getStartDate());
            jobSkill.setEndDate(jobSkillDTO.getEndDate());
            jobSkill.setAbout(jobSkillDTO.getAbout());
            jobSkill.setRating(jobSkillDTO.getRating());
            return jobSkillRepository.save(jobSkill);
        }
        else {
            throw new NullPointerException("User doesn't match with Student");
        }
    }

    public void deleteJobSkill(int id,String token)
    {
        User user = userRepository.findByToken(token);
        JobSkill jobSkill = jobSkillRepository.findById(id);
        Student student = studentRepository.findByJobSkillsId(id);
        if(user.getRole().equals(String.valueOf(Role.STUDENT))) {
            if (user.getStudent().equals(student)) {
                jobSkillRepository.delete(jobSkill);
            } else {
                throw new NullPointerException("User doesn't match with Student");
            }
        }
        else if (user.getRole().equals(String.valueOf(Role.ADMIN))){
            jobSkillRepository.delete(jobSkill);
        }
        else {
            throw new NullPointerException("You don's have permission");
        }
    }
}