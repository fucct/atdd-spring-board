package spring.board.demo.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.http.HttpStatus;

import spring.board.demo.domain.error.dto.ErrorResponse;
import spring.board.demo.domain.token.dto.TokenResponse;
import spring.board.demo.domain.user.dto.UserCreateResponse;
import spring.board.demo.domain.user.dto.UserResponse;

public class UserAcceptanceTest extends AcceptanceTest {
    @TestFactory
    Stream<DynamicTest> manageUser() {
        UserCreateResponse user = createUser(TEST_USER_ID, TEST_USER_NAME,
            TEST_USER_PASSWORD);
        TokenResponse token = login(TEST_USER_ID, TEST_USER_PASSWORD);

        return Stream.of(
            DynamicTest.dynamicTest("Create user test", () -> {
                assertThat(user).extracting(UserCreateResponse::getId).isEqualTo(user.getId());
            }),
            DynamicTest.dynamicTest("Login user test", () -> {
                assertThat(token).isNotNull();
            }),
            DynamicTest.dynamicTest("Get user test", () -> {
                UserResponse userResponse = getUser(user.getId(), token);
                assertThat(userResponse)
                    .hasFieldOrPropertyWithValue("id", user.getId())
                    .hasFieldOrPropertyWithValue("userId", TEST_USER_ID)
                    .hasFieldOrPropertyWithValue("name", TEST_USER_NAME);
            }),
            DynamicTest.dynamicTest("Update user info test", () -> {
                updateUser(user.getId(), TEST_OTHER_USER_NAME, TEST_USER_PASSWORD,
                    TEST_OTHER_USER_PASSWORD, token);
                UserResponse userResponse = getUser(user.getId(), token);
                assertThat(userResponse)
                    .hasFieldOrPropertyWithValue("id", user.getId())
                    .hasFieldOrPropertyWithValue("userId", TEST_USER_ID)
                    .hasFieldOrPropertyWithValue("name", TEST_OTHER_USER_NAME);
            }),
            DynamicTest.dynamicTest("Sign out user test", () -> {
                TokenResponse tokenResponse = login(TEST_USER_ID, TEST_OTHER_USER_PASSWORD);
                deleteUser(user.getId(), tokenResponse);
                assertThat(loginNotExistUser(TEST_USER_ID, TEST_OTHER_USER_PASSWORD)).isInstanceOf(
                    ErrorResponse.class);
            })
        );
    }

    private ErrorResponse loginNotExistUser(String userId, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("password", password);
        return given().
            contentType(APPLICATION_JSON_VALUE).
            accept(APPLICATION_JSON_VALUE).
            body(params).
            when().
            post("/users/login").
            then().
            log().all().
            statusCode(HttpStatus.NOT_FOUND.value()).
            extract().as(ErrorResponse.class);
    }
}
