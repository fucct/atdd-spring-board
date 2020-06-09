package spring.board.demo.domain.article;

import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spring.board.demo.domain.users.User;

@Getter
@AllArgsConstructor
@Table("ARTICLE_USER")
public class UserRef {
    private Long user;
    private String userName;

    public static UserRef of(User user) {
        return new UserRef(user.getId(), user.getName());
    }
}
