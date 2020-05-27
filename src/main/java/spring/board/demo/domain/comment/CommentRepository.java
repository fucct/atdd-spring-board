package spring.board.demo.domain.comment;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import spring.board.demo.domain.comment.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Override
    List<Comment> findAllById(Iterable<Long> longs);
}
