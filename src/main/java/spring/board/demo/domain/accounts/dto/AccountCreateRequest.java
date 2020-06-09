package spring.board.demo.domain.accounts.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.accounts.Account;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccountCreateRequest {

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "이메일")
    private String email;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "패스워드는 필수 입력 항목입니다.")
    private String password;

    public Account toAccount() {
        return Account.builder()
            .email(email)
            .name(name)
            .password(password)
            .build();
    }
}
