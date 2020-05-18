package spring.board.demo.dto;

import spring.board.demo.domain.Article;

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
