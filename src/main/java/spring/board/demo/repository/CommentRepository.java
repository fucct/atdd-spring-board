package spring.board.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import spring.board.demo.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Override
    List<Comment> findAllById(Iterable<Long> longs);
}
