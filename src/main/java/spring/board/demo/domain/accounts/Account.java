package spring.board.demo.domain.accounts;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.BaseTime;
import spring.board.demo.domain.accounts.dto.AccountUpdateRequest;
import spring.board.demo.exception.AccessDeniedException;
import spring.board.demo.exception.NotMatchPasswordException;

@Getter
@NoArgsConstructor
public class Account extends BaseTime {
    @Id
    private Long id;
    private String email;
    private String name;
    private String password;

    @Builder
    public Account(Long id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public static Account of(Long id, String email, String name, String password) {
        return Account.builder()
            .id(id)
            .email(email)
            .name(name)
            .password(password)
            .build();
    }

    public static Account of(String email, String name, String password) {
        return Account.builder()
            .email(email)
            .name(name)
            .password(password)
            .build();
    }

    public void update(Long id, AccountUpdateRequest request) {
        validateId(id);
        checkPassword(request.getOldPassword());
        this.name = request.getName();
        this.password = request.getNewPassword();
    }

    public void checkPassword(String password) {
        if (!this.password.equals(password)) {
            throw new NotMatchPasswordException();
        }
    }

    public void validateId(Long id) {
        if (!this.id.equals(id)) {
            throw new AccessDeniedException();
        }
    }

    public boolean isSameId(Long id) {
        return this.id.equals(id);
    }
}
