package spring.board.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import spring.board.demo.domain.Article;

public interface
ArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findAll();

}
