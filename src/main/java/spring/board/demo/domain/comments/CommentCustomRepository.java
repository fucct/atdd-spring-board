package spring.board.demo.domain.comments;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

import spring.board.demo.domain.comments.dto.CommentDetailResponse;

public interface CommentCustomRepository {
    @Query("SELECT comments.id AS id, comments.account_id AS account_id, "
        + "account.name AS account_name, comments.content AS content "
        + "FROM comments "
        + "LEFT JOIN account "
        + "ON comments.account_id = account.id "
        + "WHERE comments.id IN (:ids)")
    List<CommentDetailResponse> findCommentsByIds(@Param("ids") List<Long> ids);
}
