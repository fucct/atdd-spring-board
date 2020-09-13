package spring.board.demo.articles.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import spring.board.demo.articles.view.dto.ArticlePreviewResponse;

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
    List<ArticlePreviewResponse> findAllWithAccountNameByIds(@Param("ids") List<Long> ids);

    @Query("SELECT article.id AS id, article.account_id AS account_id, article.title AS title, "
        + "article.content AS content, account.name AS account_name "
        + "FROM article "
        + "INNER JOIN account "
        + "ON article.account_id = account.id "
        + "WHERE article.id IN (:id)")
    Optional<ArticlePreviewResponse> findByIdWithAccountName(@Param("id") Long id);

    // List<Comment> findByArticleId(Long id);
}
