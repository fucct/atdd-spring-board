package spring.board.demo.domain.users.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.users.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String userId;
    private String userName;

    public static UserResponse of(User user) {
        return new UserResponse(user.getId(), user.getUserId(), user.getName());
    }

    public static List<UserResponse> listOf(List<User> users) {
        return users.stream().map(UserResponse::of).collect(Collectors.toList());
    }
}
