package spring.board.demo.accounts.view.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Builder
    public LoginRequest(@NotBlank String email,
        @NotBlank String password) {
        this.email = email;
        this.password = password;
    }
}
