package spring.board.demo.domain.user.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

    @Builder
    public LoginRequest(@NotBlank String userId,
        @NotBlank String password) {
        this.userId = userId;
        this.password = password;
    }
}
