package uet.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import uet.model.Message;
import uet.model.MessageType;

import java.util.Date;
import java.util.List;

/**
 * Created by nhkha on 17/05/2017.
 */
public interface MessageRepository extends CrudRepository<Message, Integer>, PagingAndSortingRepository<Message, Integer> {
    Message findById (int id);
    List<Message> findByMessageId(int id);
    List<Message> findByMessageType(MessageType messageType);
    List<Message> findByUserIdAndStatusOrderByIdDesc(int id, String status);
    Page<Message> findByUserIdAndMessageTypeNotLikeOrderByLastUpdatedDesc(int id, MessageType messageType, Pageable pageable);
    Page<Message> findByUserIdAndMessageTypeNotLikeAndLastUpdatedLessThanOrderByLastUpdatedDesc(int id, MessageType messageType, Date date, Pageable pageable);
    Page<Message> findByUserIdAndStatusAndMessageTypeNotLikeOrderByIdDesc(int id, String status, MessageType messageType, Pageable pageable);
    Message findByUserIdAndId(int userId, int id);
    List<Message> findByUserIdAndMessageType(int userId, MessageType messageType);
    Message findByIdAndMessageType(int id, MessageType messageType);
    Page<Message> findByUserIdOrderByIdDesc(int userId, Pageable pageable);
    Message findByMessageTypeAndMessageId (MessageType messageType, int id);
    String updateUserId = "update message m set m.user_id = null where m.user_id = :userId";

    @Modifying
    @Query(value = updateUserId, nativeQuery = true)
    void updateUserId(@Param("userId") int id);
}
