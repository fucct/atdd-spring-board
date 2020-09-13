package spring.board.demo.accounts.domain;

import static org.assertj.core.api.Assertions.*;
import static spring.board.demo.acceptance.AcceptanceTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import spring.board.demo.accounts.domain.Account;
import spring.board.demo.accounts.view.dto.AccountUpdateRequest;
import spring.board.demo.exception.AccessDeniedException;
import spring.board.demo.exception.NotMatchPasswordException;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = Account.of(TEST_ID, TEST_ACCOUNT_EMAIL, TEST_ACCOUNT_NAME, TEST_ACCOUNT_PASSWORD);
    }

    @Test
    @DisplayName("회원 가입")
    void create() {
        assertThat(account).isInstanceOf(Account.class);
    }

    @Test
    @DisplayName("회원 정보 수정")
    void update() {
        account.update(TEST_ID, AccountUpdateRequest.builder()
            .name(TEST_OTHER_ACCOUNT_NAME)
            .oldPassword(TEST_ACCOUNT_PASSWORD)
            .newPassword(TEST_OTHER_ACCOUNT_PASSWORD)
            .build());
        assertThat(account)
            .hasFieldOrPropertyWithValue("id", TEST_ID)
            .hasFieldOrPropertyWithValue("email", TEST_ACCOUNT_EMAIL)
            .hasFieldOrPropertyWithValue("name", TEST_OTHER_ACCOUNT_NAME)
            .hasFieldOrPropertyWithValue("password", TEST_OTHER_ACCOUNT_PASSWORD);
    }

    @Test
    @DisplayName("비밀번호 확인")
    void checkPassword() {
        assertThatThrownBy(() -> account.checkPassword(TEST_OTHER_ACCOUNT_PASSWORD))
            .isInstanceOf(NotMatchPasswordException.class);
    }

    @Test
    @DisplayName("Id 확인")
    void validateId() {
        assertThatThrownBy(() -> account.validateId(TEST_OTHER_ID))
            .isInstanceOf(AccessDeniedException.class);
    }
}
