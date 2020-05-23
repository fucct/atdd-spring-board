package spring.board.demo.api;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import spring.board.demo.controller.ArticleController;
import spring.board.demo.docs.ArticleDocumentation;
import spring.board.demo.domain.Article;
import spring.board.demo.service.ArticleService;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = ArticleController.class)
@AutoConfigureMockMvc
public class ArticleControllerTest {

    private static final String TEST_ARTICLE_TITLE = "안녕하세요";
    private static final String TEST_USER_NAME = "디디";
    private static final String TEST_ARTICLE_CONTENT = "좋은 하루 되세요";

    @MockBean
    private ArticleService articleService;

    @Autowired
    private MockMvc mockMvc;

    private Article article;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new ShallowEtagHeaderFilter())
            .apply(documentationConfiguration(restDocumentation))
            .build();

        article = Article.builder()
            .id(1L)
            .title(TEST_ARTICLE_TITLE)
            .userName(TEST_USER_NAME)
            .content(TEST_ARTICLE_CONTENT)
            .build();
    }

    @Test
    void create() throws Exception {
        given(articleService.save(any())).willReturn(article.getId());

        String inputJson = "{\"title\":\"" + TEST_ARTICLE_TITLE + "\"," +
            "\"userName\":\"" + TEST_USER_NAME + "\"," +
            "\"content\":\"" + TEST_ARTICLE_CONTENT + "\"}";

        mockMvc.perform(post("/articles")
            .content(inputJson)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(header().exists("location"))
            .andExpect(content().string("1"))
            .andDo(print())
            .andDo(ArticleDocumentation.createArticle());
    }
}
