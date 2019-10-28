package uet.repository;

import org.springframework.data.repository.CrudRepository;
import uet.model.CooperateActivityDetail;

public interface CooperateActivityDetailRepository extends CrudRepository<CooperateActivityDetail, Integer> {
    CooperateActivityDetail findById(int id);
}
