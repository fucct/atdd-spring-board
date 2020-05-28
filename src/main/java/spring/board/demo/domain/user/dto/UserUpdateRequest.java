package spring.board.demo.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {
    private String name;
    private String oldPassword;
    private String newPassword;

    @Builder
    public UserUpdateRequest(String name, String oldPassword, String newPassword) {
        this.name = name;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
