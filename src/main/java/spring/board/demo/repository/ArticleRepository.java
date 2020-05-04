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

@Repository
@RequiredArgsConstructor
public class ArticleRepository {

    private final EntityManager em;

    public Long save(Article article) {
        if (Objects.isNull(article.getId())) {
            em.persist(article);
        } else {
            em.merge(article);
        }
        return article.getId();
    }

    public Article findById(Long id) {
        return em.find(Article.class, id);
    }

    public List<ArticleResponseDto> findAll(){
        return em.createQuery("select a from Article a", Article.class)
            .getResultList()
            .stream()
            .map(ArticleResponseDto::of)
            .collect(Collectors.toList());
    }

    public void addComment(Long articleId, Comment comment) {
        Article article = em.find(Article.class, articleId);
        article.getComments().add(comment);
    }
}
