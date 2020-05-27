package spring.board.demo.domain.article.dto;

import java.util.List;

import spring.board.demo.domain.article.Article;
import spring.board.demo.domain.comment.Comment;

public class ArticleDetailResponse {

    private Long id;
    private String title;
    private String userName;
    private String content;
    private List<Comment> comments;

    public static ArticleDetailResponse of(Article article, List<Comment> comments) {
        return new ArticleDetailResponse(article.getId(), article.getTitle(), article.getUserName(),
            article.getContent(), comments);
    }

    public ArticleDetailResponse() {
    }

    public ArticleDetailResponse(Long id, String title, String userName, String content,
        List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.content = content;
        this.comments = comments;
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

    public List<Comment> getComments() {
        return comments;
    }
}
