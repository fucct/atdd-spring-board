package spring.board.demo.domain.user.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.ArticleRef;
import spring.board.demo.domain.user.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String userId;
    private String name;
    private Set<ArticleRef> articles;

    public static UserResponse of(User user) {
        return new UserResponse(user.getId(), user.getUserId(), user.getName(), user.getArticles());
    }

    public static List<UserResponse> listOf(List<User> users) {
        return users.stream().map(UserResponse::of).collect(Collectors.toList());
    }
}
