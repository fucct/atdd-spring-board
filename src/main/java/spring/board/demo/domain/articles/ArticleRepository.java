package spring.board.demo.domain.articles;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import spring.board.demo.domain.articles.dto.ArticlePreviewResponse;
import spring.board.demo.domain.articles.dto.ArticleResponse;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Override
    List<Article> findAll();

    @Override
    List<Article> findAllById(Iterable<Long> ids);

    @Query("SELECT article.id AS id, article.account_id AS account_id, article.title AS title, article.content AS content, account.name AS account_name FROM article INNER JOIN account ON article.account_id = account.id")
    List<ArticlePreviewResponse> findAllWithAccountName();

    @Query("SELECT article.id AS id, article.account_id AS account_id, article.title AS title, "
        + "article.content AS content, account.name AS account_name "
        + "FROM article "
        + "INNER JOIN account "
        + "ON article.account_id = account.id "
        + "WHERE article.id IN (:ids)")
    List<ArticlePreviewResponse> findAllWithAccountNameById(@Param("ids") List<Long> ids);

    @Query("SELECT *, account.name AS account_name FROM article INNER JOIN account ON article.account_id = account.id")
    Optional<ArticleResponse> findByIdWithAccountName(Long id);
}
