package spring.board.demo.accounts.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.http.HttpStatus;

import spring.board.demo.acceptance.AcceptanceTest;
import spring.board.demo.accounts.view.dto.AccountCreateResponse;
import spring.board.demo.accounts.view.dto.AccountDetailResponse;
import spring.board.demo.exception.dto.ErrorResponse;
import spring.board.demo.infra.dto.TokenResponse;

public class AccountAcceptanceTest extends AcceptanceTest {
    @TestFactory
    Stream<DynamicTest> manageUser() {
        AccountCreateResponse account = createUser(TEST_ACCOUNT_EMAIL, TEST_ACCOUNT_NAME,
            TEST_ACCOUNT_PASSWORD);
        TokenResponse token = login(TEST_ACCOUNT_EMAIL, TEST_ACCOUNT_PASSWORD);

        return Stream.of(
            DynamicTest.dynamicTest("Create account test", () -> {
                assertThat(account).extracting(AccountCreateResponse::getId)
                    .isEqualTo(account.getId());
            }),
            DynamicTest.dynamicTest("Login account test", () -> {
                assertThat(token).isNotNull();
            }),
            DynamicTest.dynamicTest("Get account test", () -> {
                AccountDetailResponse response = getAccount(account.getId());
                assertThat(response)
                    .hasFieldOrPropertyWithValue("id", account.getId())
                    .hasFieldOrPropertyWithValue("email", TEST_ACCOUNT_EMAIL)
                    .hasFieldOrPropertyWithValue("name", TEST_ACCOUNT_NAME);
            }),
            DynamicTest.dynamicTest("Update account info test", () -> {
                updateAccount(account.getId(), TEST_OTHER_ACCOUNT_NAME, TEST_ACCOUNT_PASSWORD,
                    TEST_OTHER_ACCOUNT_PASSWORD, token);
                AccountDetailResponse userResponse = getAccount(account.getId());
                assertThat(userResponse)
                    .hasFieldOrPropertyWithValue("id", account.getId())
                    .hasFieldOrPropertyWithValue("email", TEST_ACCOUNT_EMAIL)
                    .hasFieldOrPropertyWithValue("name", TEST_OTHER_ACCOUNT_NAME);
            }),
            DynamicTest.dynamicTest("Sign out account test", () -> {
                TokenResponse tokenResponse = login(TEST_ACCOUNT_EMAIL,
                    TEST_OTHER_ACCOUNT_PASSWORD);
                deleteUser(account.getId(), tokenResponse);
                assertThat(loginNotExistUser(TEST_ACCOUNT_EMAIL, TEST_OTHER_ACCOUNT_PASSWORD))
                    .isInstanceOf(ErrorResponse.class);
            })
        );
    }

    private ErrorResponse loginNotExistUser(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        return given().
            contentType(APPLICATION_JSON_VALUE).
            accept(APPLICATION_JSON_VALUE).
            body(params).
            when().
            post("/accounts/login").
            then().
            log().all().
            statusCode(HttpStatus.NOT_FOUND.value()).
            extract().as(ErrorResponse.class);
    }
}
