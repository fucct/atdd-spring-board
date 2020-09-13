package spring.board.demo.domain.accounts;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import spring.board.demo.domain.BaseTime;
import spring.board.demo.domain.accounts.dto.AccountUpdateRequest;
import spring.board.demo.domain.articles.CommentRef;
import spring.board.demo.exception.AccessDeniedException;
import spring.board.demo.exception.NotMatchPasswordException;

@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
public class Account extends BaseTime {

    @Id @With
    private final Long id;
    private final String email;
    private final String name;
    private final String password;

    private final Set<ArticleRef> articles = new LinkedHashSet<>();
    private final Set<CommentRef> comments = new LinkedHashSet<>();

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

    public Account update(Long id, AccountUpdateRequest request) {
        validateId(id);
        checkPassword(request.getOldPassword());
        return Account.builder()
            .id(id)
            .email(request.getEmail())
            .name(request.getName())
            .password(request.getNewPassword())
            .build();
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
}
