package spring.board.demo.domain.comment;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import spring.board.demo.domain.comment.dto.CommentDetailResponse;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query("SELECT comments.id, comments.user_id, user.name AS user_name, comments.content, FROM comments INNER JOIN user ON comments.user_id = user.id WHERE comments.id IN (:ids)")
    List<CommentDetailResponse> findCommentsByIds(@Param("ids") List<Long> ids);

    List<Comment> findAllByUserId(Long id);
}