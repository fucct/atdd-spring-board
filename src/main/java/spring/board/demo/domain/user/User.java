package spring.board.demo.domain.user;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.BaseTime;
import spring.board.demo.domain.UserCommentRef;
import spring.board.demo.domain.article.Article;
import spring.board.demo.domain.user.dto.UserUpdateRequest;
import spring.board.demo.exception.NotMatchPasswordException;

@Getter
@NoArgsConstructor
public class User extends BaseTime {
    @Id
    private Long id;
    private String userId;
    private String name;
    private String password;
    private Set<ArticleRef> articles = new LinkedHashSet<>();
    private Set<UserCommentRef> comments = new LinkedHashSet<>();

    public static User of(String userId, String name, String password) {
        return User.builder()
            .userId(userId)
            .name(name)
            .password(password)
            .build();
    }

    @Builder
    public User(Long id, String userId, String name, String password, Set<ArticleRef> articles,
        Set<UserCommentRef> comments) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.articles = articles;
        this.comments = comments;
    }

    public void update(UserUpdateRequest request) {
        this.name = request.getName();
        this.password = request.getNewPassword();
    }

    public void checkPassword(String password) {
        if (!Objects.equals(this.password, password)) {
            throw new NotMatchPasswordException();
        }
    }

    public void addArticle(Article article) {
        this.articles.add(new ArticleRef(article.getId()));
    }
}
