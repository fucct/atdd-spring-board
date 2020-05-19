package spring.board.demo.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class IndexControllerTest extends AcceptanceTest {

    @Test
    void index() {
        given().when().
            get("/").
            then().
            statusCode(HttpStatus.OK.value());
    }
}