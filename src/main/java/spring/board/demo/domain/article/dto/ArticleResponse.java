package spring.board.demo.domain.article.dto;

import java.util.List;
import java.util.stream.Collectors;

import spring.board.demo.domain.article.Article;

public class ArticleResponse {

    private Long id;
    private String title;
    private String userName;
    private String content;

    public ArticleResponse() {
    }

    public ArticleResponse(Long id, String title, String userName, String content) {
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.content = content;
    }

    public static List<ArticleResponse> listOf(List<Article> articles) {
        return articles.stream().map(ArticleResponse::of).collect(Collectors.toList());
    }

    public static ArticleResponse of(Article article) {
        return new ArticleResponse(article.getId(), article.getTitle(), article.getUserName(),
            article.getContent());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }
}
