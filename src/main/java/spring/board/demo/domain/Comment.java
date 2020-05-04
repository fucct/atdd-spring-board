package spring.board.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.board.demo.BaseTimeEntity;

@Getter @Setter
@Entity(name = "comment")
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Column(name = "comment_nickname")
    private String nickName;
    @Column(name = "comment_password")
    private String password;
    @Column(name = "comment_content")
    private String content;

    public void setArticle(Article article){
        this.article = article;
        article.getComments().add(this);
    }

    public static Comment createComment(Article article, String content, String nickName, String password){
        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setNickName(nickName);
        comment.setPassword(password);
        comment.setContent(content);

        return comment;
    }
}
