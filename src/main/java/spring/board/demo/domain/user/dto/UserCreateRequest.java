package spring.board.demo.domain.user.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.user.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserCreateRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    public User toUser() {
        return User.builder()
            .userId(userId)
            .name(name)
            .password(password)
            .build();
    }
}
