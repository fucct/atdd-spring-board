package spring.board.demo.api;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.RestDocumentationExtension;

import spring.board.demo.controller.ArticleController;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = ArticleController.class)
@AutoConfigureMockMvc
public class ArticleControllerTest {
}
