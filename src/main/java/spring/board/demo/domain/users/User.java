package spring.board.demo.domain.users;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.BaseTime;
import spring.board.demo.domain.article.Article;
import spring.board.demo.domain.users.dto.UserUpdateRequest;
import spring.board.demo.exception.AccessDeniedException;
import spring.board.demo.exception.NotMatchPasswordException;

@Getter
@NoArgsConstructor
public class User extends BaseTime {
    @Id
    private Long id;
    private String userId;
    private String name;
    private String password;
    private Set<ArticleRef> articles;

    public static User of(Long id, String userId, String name, String password) {
        return User.builder()
            .id(id)
            .userId(userId)
            .name(name)
            .password(password)
            .articles(new LinkedHashSet<>())
            .build();
    }

    public static User of(String userId, String name, String password) {
        return User.builder()
            .userId(userId)
            .name(name)
            .password(password)
            .articles(new LinkedHashSet<>())
            .build();
    }

    @Builder
    public User(Long id, String userId, String name, String password, Set<ArticleRef> articles) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.articles = articles;
    }

    public void update(Long id, UserUpdateRequest request) {
        validateId(id);
        checkPassword(request.getOldPassword());
        this.name = request.getName();
        this.password = request.getNewPassword();
    }

    public void checkPassword(String password) {
        if (!Objects.equals(this.password, password)) {
            throw new NotMatchPasswordException();
        }
    }

    public void addArticle(Article article) {
        this.articles.add(ArticleRef.of(article));
    }

    public void validateArticle(Long id) {
        articles.stream()
            .filter(article -> article.hasSameId(id))
            .findFirst()
            .orElseThrow(AccessDeniedException::new);
    }

    public void deleteArticle(Long id) {
        ArticleRef articleRef = articles.stream()
            .filter(article -> article.hasSameId(id))
            .findFirst()
            .orElseThrow(AccessDeniedException::new);
        articles.remove(articleRef);
    }

    public void validateId(Long id) {
        if (!this.id.equals(id)) {
            throw new AccessDeniedException();
        }
    }

    public boolean isSameId(Long userId) {
        return this.id.equals(userId);
    }
}
