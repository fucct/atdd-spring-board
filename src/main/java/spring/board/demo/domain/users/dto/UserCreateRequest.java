package spring.board.demo.domain.users.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.users.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserCreateRequest {

    @NotBlank(message = "아이디는 필수 입력 항목입니다.")
    private String userId;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "패스워드는 필수 입력 항목입니다.")
    private String password;

    public User toUser() {
        return User.builder()
            .userId(userId)
            .name(name)
            .password(password)
            .build();
    }
}
