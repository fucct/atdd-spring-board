package spring.board.demo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.BaseTimeEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments = new ArrayList<>();

    private String nickName;
    private String password;
    private String title;
    private String content;

    @Builder
    public Article(String nickName, String password, String title, String content) {
        this.nickName = nickName;
        this.password = password;
        this.title = title;
        this.content = content;
    }
}
