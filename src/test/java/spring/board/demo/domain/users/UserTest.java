package spring.board.demo.domain.users;

import static org.assertj.core.api.Assertions.*;
import static spring.board.demo.acceptance.AcceptanceTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import spring.board.demo.domain.users.dto.UserUpdateRequest;
import spring.board.demo.exception.AccessDeniedException;
import spring.board.demo.exception.NotMatchPasswordException;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = User.of(TEST_ID, TEST_USER_ID, TEST_USER_NAME, TEST_USER_PASSWORD);
    }

    @Test
    @DisplayName("회원 가입")
    void create() {
        assertThat(user).isInstanceOf(User.class);
    }

    @Test
    @DisplayName("회원 정보 수정")
    void update() {
        user.update(TEST_ID, UserUpdateRequest.builder()
            .name(TEST_OTHER_USER_NAME)
            .oldPassword(TEST_USER_PASSWORD)
            .newPassword(TEST_OTHER_USER_PASSWORD)
            .build());
        assertThat(user)
            .hasFieldOrPropertyWithValue("id", TEST_ID)
            .hasFieldOrPropertyWithValue("userId", TEST_USER_ID)
            .hasFieldOrPropertyWithValue("name", TEST_OTHER_USER_NAME)
            .hasFieldOrPropertyWithValue("password", TEST_OTHER_USER_PASSWORD);
    }

    @Test
    @DisplayName("비밀번호 확인")
    void checkPassword() {
        assertThatThrownBy(() -> user.checkPassword(TEST_OTHER_USER_PASSWORD))
            .isInstanceOf(NotMatchPasswordException.class);
    }

    @Test
    @DisplayName("Id 확인")
    void validateId() {
        assertThatThrownBy(() -> user.validateId(TEST_OTHER_ID))
            .isInstanceOf(AccessDeniedException.class);
    }
}