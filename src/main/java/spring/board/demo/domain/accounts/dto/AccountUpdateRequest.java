package spring.board.demo.domain.accounts.dto;

import java.beans.ConstructorProperties;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(onConstructor_ = @ConstructorProperties({"name", "email", "oldPassword", "newPassword"}))
@Builder
@Getter
public class AccountUpdateRequest {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private final String name;

    private final String email;

    @NotBlank(message = "기존 패스워드는 필수 입력 항목입니다.")
    private final String oldPassword;

    @NotBlank(message = "새 패스워드는 필수 입력 항목입니다.")
    private final String newPassword;
}
