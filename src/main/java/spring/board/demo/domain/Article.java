package spring.board.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.dto.ArticleCreateRequest;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String userName;
    private String content;

    @Builder
    public Article(String title, String userName, String content) {
        this.title = title;
        this.userName = userName;
        this.content = content;
    }

    public void update(ArticleCreateRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
