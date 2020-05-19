package spring.board.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "comments")
public class Comment extends BaseTime{

    @Id
    private Long id;

    private Long article;

    @Column(value = "comment_user_name")
    private String userName;

    @Column(value = "comment_content")
    private String content;

    public static Comment of(String userName, String content) {
        return new Comment(null, null, userName, content);
    }

    public static Comment of(Long id, String userName, String content) {
        return new Comment(id, null, userName, content);
    }

    public Comment() {
    }

    public Comment(Long id, Long article, String userName, String content) {
        this.id = id;
        this.article = article;
        this.userName = userName;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public Long getArticle() {
        return article;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    public void setArticle(Long article){
        this.article = article;
    }
}
