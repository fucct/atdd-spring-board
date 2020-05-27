package spring.board.demo.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import spring.board.demo.domain.token.dto.TokenResponse;

public class UserAcceptanceTest extends AcceptanceTest {
    @TestFactory
    Stream<DynamicTest> manageUser() {
        return Stream.of(
            DynamicTest.dynamicTest("Create User", () -> {
                Long user = createUser(TEST_USER_ID, TEST_USER_NAME, TEST_USER_PASSWORD);
                assertThat(user).isEqualTo(1L);
            }),
            DynamicTest.dynamicTest("Login", () -> {
                TokenResponse tokenResponse = login(TEST_USER_ID, TEST_USER_PASSWORD);
                assertThat(tokenResponse).isNotNull();
            }),
            DynamicTest.dynamicTest("BearerTokenProvider Test", () -> {
                // String jwtString = Jwts.builder()
                //     .setHeaderParam("type", "JWT")
                //     .setHeaderParam("issueDate", System.currentTimeMillis())
                //     .setSubject("디디")
                //     .signWith(SignatureAlgorithm.HS512, "aaaa")
                //     .compact();
            })
        );
    }

    private Long createUser(String userID, String name, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userID);
        params.put("name", name);
        params.put("password", password);

        //@formatter:off
        return given().
                body(params).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.APPLICATION_JSON_VALUE).
            when().
                post("/users").
            then().
                log().all().
                statusCode(HttpStatus.CREATED.value()).
                extract().as(Long.class);
    }

    private TokenResponse login(String userId, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("password", password);

        //@formatter:off
        return given().
            body(params).
            contentType(MediaType.APPLICATION_JSON_VALUE).
            accept(MediaType.APPLICATION_JSON_VALUE).
            when().
            post("/users/login").
            then().
            log().all().
            statusCode(HttpStatus.OK.value()).
            extract().as(TokenResponse.class);
    }



    //
    // @Test
    // void manageUser() {
    //     // Long userId = createUser("디디", "dd");
    //
    // }
    //
    // private Long createUser(String name, String password) {
    //     Map<String, String> params = new HashMap<>();
    //     params.put("name", name);
    //     params.put("password", password);
    //
    //     return given()
    //         .contentType(MediaType.APPLICATION_JSON_VALUE)
    //         .accept(MediaType.APPLICATION_JSON_VALUE)
    //         .body(params)
    //         .when()
    //         .post("/users")
    //         .then()
    //         .log().all()
    //         .statusCode(HttpStatus.CREATED.value())
    //         .;
    // }
}
