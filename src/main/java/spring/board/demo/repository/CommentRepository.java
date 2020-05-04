package spring.board.demo.repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import spring.board.demo.domain.Article;
import spring.board.demo.domain.Comment;
import spring.board.demo.dto.ArticleResponseDto;
import spring.board.demo.dto.CommentResponseDto;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        if (Objects.isNull(comment.getId())) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
    }

    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }

    public List<CommentResponseDto> findAll(){
        return em.createQuery("select a from Comment a", Comment.class)
            .getResultList()
            .stream()
            .map(CommentResponseDto::new)
            .collect(Collectors.toList());
    }
}
