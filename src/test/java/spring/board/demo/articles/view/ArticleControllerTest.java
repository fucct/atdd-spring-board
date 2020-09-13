package spring.board.demo.articles.view;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static spring.board.demo.acceptance.AcceptanceTest.*;

import javax.servlet.http.Cookie;

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
import spring.board.demo.accounts.AccountFixture;
import spring.board.demo.articles.ArticleFixture;
import spring.board.demo.docs.ArticleDocumentation;
import spring.board.demo.accounts.domain.Account;
import spring.board.demo.articles.domain.Article;
import spring.board.demo.articles.view.dto.ArticleDetailResponse;
import spring.board.demo.articles.view.dto.ArticleRequest;
import spring.board.demo.infra.TokenProvider;
import spring.board.demo.accounts.service.AccountService;
import spring.board.demo.articles.service.ArticleService;

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
    private ArticleDetailResponse articleResponse;
    private Cookie cookie;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new ShallowEtagHeaderFilter())
            .apply(documentationConfiguration(restDocumentation))
            .build();

        account = Account.of(ArticleFixture.ID1, AccountFixture.EMAIL1, AccountFixture.NAME1, AccountFixture.PASSWORD1);
        cookie = new Cookie("token", AccountFixture.TOKEN1);
    }

    @Test
    @DisplayName("게시글 작성")
    void createTest() throws Exception {
        given(tokenProvider.getSubject(anyString())).willReturn(AccountFixture.EMAIL1);
        given(accountService.findByEmail(anyString())).willReturn(account);
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
    @DisplayName("게시글 수정")
    void update() throws Exception {
        ArticleRequest request = new ArticleRequest(ArticleFixture.TITLE1,
            ArticleFixture.CONTENT1);

        mockMvc.perform(put("/articles/" + ArticleFixture.ID1)
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
        mockMvc.perform(delete("/articles/" + ArticleFixture.ID1)
            .cookie(cookie))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(ArticleDocumentation.delete());
    }
}
