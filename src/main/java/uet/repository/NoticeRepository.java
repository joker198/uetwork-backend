package uet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.model.Notice;

import java.util.Date;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Integer> {
    Notice findTopByContractIdOrderByCreatedDesc(int id);
}
