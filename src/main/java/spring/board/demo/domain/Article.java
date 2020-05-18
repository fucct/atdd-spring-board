package spring.board.demo.domain;

import java.util.Objects;

import org.springframework.data.annotation.Id;

import spring.board.demo.dto.ArticleUpdateRequest;

public class Article extends BaseTime {

    @Id
    private Long id;
    private String title;
    private String userName;
    private String content;

    public Article() {
    }

    public Article(Long id, String title, String userName, String content) {
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.content = content;
    }

    public static Article of(String title, String userName, String content) {
        return new Article(null, title, userName, content);
    }

    public void update(ArticleUpdateRequest request) {
        if (Objects.nonNull(request.getTitle())) {
            this.title = request.getTitle();
        }
        if (Objects.nonNull(request.getContent())) {
            this.content = request.getContent();
        }
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
