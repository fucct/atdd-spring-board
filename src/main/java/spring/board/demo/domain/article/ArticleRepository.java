package spring.board.demo.domain.article;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import spring.board.demo.domain.article.Article;

public interface
ArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findAll();

}
