package spring.board.demo.accounts.view;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static spring.board.demo.acceptance.AcceptanceTest.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.BeforeEach;
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
import spring.board.demo.common.prehandler.TokenExtractor;
import spring.board.demo.docs.AccountDocumentation;
import spring.board.demo.accounts.domain.Account;
import spring.board.demo.accounts.view.dto.AccountCreateResponse;
import spring.board.demo.accounts.view.dto.AccountDetailResponse;
import spring.board.demo.infra.TokenProvider;
import spring.board.demo.accounts.service.AccountService;
import spring.board.demo.query.DomainService;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("/truncate.sql")
public class AccountControllerTest {

    @MockBean
    private AccountService accountService;

    @MockBean
    private DomainService domainService;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private TokenExtractor tokenExtractor;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Account account;
    private Cookie cookie;
    private AccountDetailResponse accountResponse;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new ShallowEtagHeaderFilter())
            .apply(documentationConfiguration(restDocumentation))
            .build();

        account = Account.of(ArticleFixture.ID1, AccountFixture.EMAIL1, AccountFixture.NAME1, AccountFixture.PASSWORD1);
        cookie = new Cookie("token", AccountFixture.TOKEN1);
        accountResponse = new AccountDetailResponse(ArticleFixture.ID1, AccountFixture.EMAIL1, AccountFixture.NAME1,
            new ArrayList<>(), new ArrayList<>());
    }

    @Test
    void create() throws Exception {
        when(accountService.create(any())).thenReturn(new AccountCreateResponse(account.getId()));

        Map<String, String> params = new HashMap<>();
        params.put("email", AccountFixture.EMAIL1);
        params.put("name", AccountFixture.NAME1);
        params.put("password", AccountFixture.PASSWORD1);

        mockMvc.perform(post("/accounts")
            .content(objectMapper.writeValueAsString(params))
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").value(1L))
            .andDo(print())
            .andDo(AccountDocumentation.create());
    }


    @Test
    void update() throws Exception {
        // when(accountService.findByUserId(anyString())).thenReturn(Optional.of(account));
        when(tokenExtractor.extract(any())).thenReturn(AccountFixture.TOKEN1);
        when(tokenProvider.getSubject(any())).thenReturn(AccountFixture.EMAIL1);

        Map<String, String> params = new HashMap<>();
        params.put("name", AccountFixture.NAME2);
        params.put("oldPassword", AccountFixture.PASSWORD1);
        params.put("newPassword", AccountFixture.PASSWORD2);

        mockMvc.perform(put("/accounts/" + account.getId())
            .cookie(cookie)
            .contentType(APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(params)))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(AccountDocumentation.update());
    }

    @Test
    void deleteAccount() throws Exception {
        // when(accountService.findByUserId(anyString())).thenReturn(Optional.of(account));
        when(tokenExtractor.extract(any())).thenReturn(AccountFixture.TOKEN1);
        when(tokenProvider.getSubject(any())).thenReturn(AccountFixture.EMAIL1);

        mockMvc.perform(delete("/accounts/" + account.getId())
            .cookie(cookie))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(AccountDocumentation.delete());
    }
}
