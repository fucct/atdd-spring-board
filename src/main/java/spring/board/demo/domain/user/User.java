package spring.board.demo.domain.user;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.ArticleRef;
import spring.board.demo.domain.BaseTime;
import spring.board.demo.domain.UserCommentRef;

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
}
