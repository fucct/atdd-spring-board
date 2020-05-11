package spring.board.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.board.demo.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
