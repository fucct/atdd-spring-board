package spring.board.demo.accounts.domain;

import static org.assertj.core.api.Assertions.*;
import static spring.board.demo.acceptance.AcceptanceTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import spring.board.demo.accounts.AccountFixture;
import spring.board.demo.accounts.domain.Account;
import spring.board.demo.accounts.view.dto.AccountUpdateRequest;
import spring.board.demo.articles.ArticleFixture;
import spring.board.demo.exception.AccessDeniedException;
import spring.board.demo.exception.NotMatchPasswordException;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = Account.of(ArticleFixture.ID1, AccountFixture.EMAIL1, AccountFixture.NAME1, AccountFixture.PASSWORD1);
    }

    @Test
    @DisplayName("회원 가입")
    void create() {
        assertThat(account).isInstanceOf(Account.class);
    }

    @Test
    @DisplayName("회원 정보 수정")
    void update() {
        account.update(ArticleFixture.ID1, AccountUpdateRequest.builder()
            .name(AccountFixture.NAME2)
            .oldPassword(AccountFixture.PASSWORD1)
            .newPassword(AccountFixture.PASSWORD2)
            .build());
        assertThat(account)
            .hasFieldOrPropertyWithValue("id", ArticleFixture.ID1)
            .hasFieldOrPropertyWithValue("email", AccountFixture.EMAIL1)
            .hasFieldOrPropertyWithValue("name", AccountFixture.NAME2)
            .hasFieldOrPropertyWithValue("password", AccountFixture.PASSWORD2);
    }

    @Test
    @DisplayName("비밀번호 확인")
    void checkPassword() {
        assertThatThrownBy(() -> account.checkPassword(AccountFixture.PASSWORD2))
            .isInstanceOf(NotMatchPasswordException.class);
    }

    @Test
    @DisplayName("Id 확인")
    void validateId() {
        assertThatThrownBy(() -> account.validateId(AccountFixture.ID2))
            .isInstanceOf(AccessDeniedException.class);
    }
}
