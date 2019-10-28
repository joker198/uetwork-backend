package uet.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uet.model.JobSkill;

@Repository
public interface JobSkillRepository extends CrudRepository<JobSkill,Integer>, PagingAndSortingRepository<JobSkill, Integer> {
    JobSkill findById(int id);
}
