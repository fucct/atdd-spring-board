package spring.board.demo.domain.article;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.ArticleCommentRef;
import spring.board.demo.domain.BaseTime;
import spring.board.demo.domain.article.dto.ArticleRequest;
import spring.board.demo.domain.user.User;

@NoArgsConstructor
@Getter
public class Article extends BaseTime {

    @Id
    private Long id;
    private String title;
    private String content;
    private UserRef userRef;
    private Set<ArticleCommentRef> comments = new LinkedHashSet<>();

    @Builder
    public Article(Long id, String title, UserRef userRef, String content,
        Set<ArticleCommentRef> comments) {
        this.id = id;
        this.title = title;
        this.userRef = userRef;
        this.content = content;
        this.comments = comments;
    }

    public static Article of(Long id, String title, User user, String content) {
        return Article.builder()
            .id(id)
            .title(title)
            .userRef(UserRef.of(user))
            .content(content)
            .comments(new LinkedHashSet<>())
            .build();
    }

    public static Article of(String title, User user, String content) {
        return Article.builder()
            .id(null)
            .title(title)
            .userRef(UserRef.of(user))
            .content(content)
            .comments(new LinkedHashSet<>())
            .build();
    }

    public void update(ArticleRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

    public String getUserName() {
        return userRef.getUserName();
    }
}
