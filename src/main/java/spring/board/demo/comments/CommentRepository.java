package spring.board.demo.comments;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import spring.board.demo.comments.view.dto.CommentDetailResponse;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query("SELECT comments.id AS id, comments.account_id AS account_id, "
        + "account.name AS account_name, comments.content AS content "
        + "FROM comments LEFT JOIN account "
        + "ON comments.account_id = account.id "
        + "WHERE comments.id = :id")
    Optional<CommentDetailResponse> findCommentById(@Param("id") Long id);

    @Override
    List<Comment> findAllById(Iterable<Long> longs);
}
