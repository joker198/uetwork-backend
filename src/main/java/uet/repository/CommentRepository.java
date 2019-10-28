package uet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uet.model.Comment;

import java.util.List;

/**
 * Created by Tu on 16-Feb-17.
 */
@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer>, PagingAndSortingRepository<Comment, Integer> {
    List<Comment> findByPartnerId(int partnerId);
    List<Comment> findByFilterNotLike(int filter);
}
