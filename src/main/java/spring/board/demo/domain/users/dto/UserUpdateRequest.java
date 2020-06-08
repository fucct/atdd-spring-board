package spring.board.demo.domain.users.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "기존 패스워드는 필수 입력 항목입니다.")
    private String oldPassword;

    @NotBlank(message = "새 패스워드는 필수 입력 항목입니다.")
    private String newPassword;

    @Builder
    public UserUpdateRequest(String name, String oldPassword, String newPassword) {
        this.name = name;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
