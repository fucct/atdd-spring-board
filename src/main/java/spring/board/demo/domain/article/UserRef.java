package spring.board.demo.domain.article;

import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spring.board.demo.domain.user.User;

@Getter
@AllArgsConstructor
@Table("article_user")
public class UserRef {
    private Long user;
    private String userName;

    public static UserRef of(User user) {
        return new UserRef(user.getId(), user.getName());
    }
}
