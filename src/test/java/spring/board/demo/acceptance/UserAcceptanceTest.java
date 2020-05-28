package spring.board.demo.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import spring.board.demo.domain.token.dto.TokenResponse;
import spring.board.demo.domain.user.User;
import spring.board.demo.domain.user.dto.UserCreateResponse;

public class UserAcceptanceTest extends AcceptanceTest {
    @TestFactory
    Stream<DynamicTest> manageUser() {
        return Stream.of(
            DynamicTest.dynamicTest("Create user test", () -> {
                UserCreateResponse user = createUser(TEST_USER_ID, TEST_USER_NAME, TEST_USER_PASSWORD);
                assertThat(user).extracting(UserCreateResponse::getId).isEqualTo(1L);
            }),
            DynamicTest.dynamicTest("Login user test", () -> {
                TokenResponse tokenResponse = login(TEST_USER_ID, TEST_USER_PASSWORD);
                assertThat(tokenResponse).isNotNull();
            }),
            DynamicTest.dynamicTest("Get user info test", () -> {
                TokenResponse token = login(TEST_USER_ID, TEST_USER_PASSWORD);
                User user = getUser(token);
                assertThat(user).isNotNull();
            }),
            DynamicTest.dynamicTest("User Info update test", () -> {
                TokenResponse token = login(TEST_USER_ID, TEST_USER_PASSWORD);
                updateUser(1L, TEST_OTHER_USER_NAME, TEST_USER_PASSWORD, TEST_OTHER_USER_PASSWORD, token);
                User user = getUser(token);
                assertThat(user)
                    .hasFieldOrPropertyWithValue("name", TEST_OTHER_USER_NAME)
                    .hasFieldOrPropertyWithValue("password", TEST_OTHER_USER_PASSWORD);
            })
        );
    }
}
