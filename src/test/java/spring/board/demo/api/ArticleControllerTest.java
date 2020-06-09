package spring.board.demo.api;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static spring.board.demo.acceptance.AcceptanceTest.*;

import javax.servlet.http.Cookie;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import spring.board.demo.docs.ArticleDocumentation;
import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.articles.Article;
import spring.board.demo.domain.articles.dto.ArticleCreateResponse;
import spring.board.demo.domain.articles.dto.ArticleRequest;
import spring.board.demo.domain.token.TokenProvider;
import spring.board.demo.service.AccountService;
import spring.board.demo.service.ArticleService;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("/truncate.sql")
public class ArticleControllerTest {

    @MockBean
    private ArticleService articleService;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Account account;
    private Article article;
    private Cookie cookie;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new ShallowEtagHeaderFilter())
            .apply(documentationConfiguration(restDocumentation))
            .build();

        account = Account.of(TEST_ID, TEST_ACCOUNT_EMAIL, TEST_ACCOUNT_NAME, TEST_ACCOUNT_PASSWORD);
        cookie = new Cookie("token", TEST_ACCOUNT_TOKEN);
        article = Article.of(TEST_ID, account, TEST_ARTICLE_TITLE, TEST_ARTICLE_CONTENT);
    }

    @Test
    @DisplayName("게시글 작성")
    void createTest() throws Exception {
        given(tokenProvider.getSubject(anyString())).willReturn(TEST_ACCOUNT_EMAIL);
        given(accountService.findByEmail(anyString())).willReturn(account);
        given(articleService.save(any(), any())).willReturn(ArticleCreateResponse.of(article));

        ArticleRequest request = new ArticleRequest(TEST_ARTICLE_TITLE,
            TEST_ARTICLE_CONTENT);
        mockMvc.perform(post("/articles")
            .cookie(cookie)
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id", Matchers.is(article.getId())))
            .andDo(print())
            .andDo(ArticleDocumentation.createArticle());
    }

    @Test
    @DisplayName("전체 게시글 가져오기")
    void getAllArticles() throws Exception {
        given(articleService.getArticles()).willReturn(null);

        mockMvc.perform(get("/articles")
            .accept(APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(ArticleDocumentation.getAllArticles());
    }

    @Test
    @DisplayName("특정 게시글 가져오기")
    void getArticle() throws Exception {
        given(articleService.getArticle(anyLong())).willReturn(null);

        mockMvc.perform(get("/articles/" + TEST_ID)
            .accept(APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            // .andExpect(jsonPath("userName", Matchers.is(article.getUserName())))
            // .andExpect(jsonPath("title", Matchers.is(article.getTitle())))
            // .andExpect(jsonPath("content", Matchers.is(article.getContent())))
            .andDo(print())
            .andDo(ArticleDocumentation.getArticle());
    }

    @Test
    @DisplayName("게시글 수정")
    void update() throws Exception {
        ArticleRequest request = new ArticleRequest(TEST_ARTICLE_TITLE,
            TEST_ARTICLE_CONTENT);

        mockMvc.perform(put("/articles/" + TEST_ID)
            .contentType(APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request))
            .cookie(cookie))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(ArticleDocumentation.update());
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteArticle() throws Exception {
        mockMvc.perform(delete("/articles/" + TEST_ID)
            .cookie(cookie))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(ArticleDocumentation.delete());
    }
}
