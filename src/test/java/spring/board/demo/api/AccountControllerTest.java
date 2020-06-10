package spring.board.demo.api;

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

import org.hamcrest.Matchers;
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
import spring.board.demo.controller.prehandler.TokenExtractor;
import spring.board.demo.docs.UserDocumentation;
import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.accounts.dto.AccountCreateResponse;
import spring.board.demo.domain.accounts.dto.AccountDetailResponse;
import spring.board.demo.domain.token.TokenProvider;
import spring.board.demo.service.AccountService;
import spring.board.demo.service.DomainService;

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

        account = Account.of(TEST_ID, TEST_ACCOUNT_EMAIL, TEST_ACCOUNT_NAME, TEST_ACCOUNT_PASSWORD);
        cookie = new Cookie("token", TEST_ACCOUNT_TOKEN);
        accountResponse = new AccountDetailResponse(TEST_ID, TEST_ACCOUNT_EMAIL, TEST_ACCOUNT_NAME,
            new ArrayList<>(), new ArrayList<>());
    }

    @Test
    void create() throws Exception {
        when(accountService.create(any())).thenReturn(new AccountCreateResponse(account.getId()));

        Map<String, String> params = new HashMap<>();
        params.put("email", TEST_ACCOUNT_EMAIL);
        params.put("name", TEST_ACCOUNT_NAME);
        params.put("password", TEST_ACCOUNT_PASSWORD);

        mockMvc.perform(post("/accounts")
            .content(objectMapper.writeValueAsString(params))
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").value(1L))
            .andDo(print())
            .andDo(UserDocumentation.create());
    }

    @Test
    void getAccount() throws Exception {
        when(domainService.getAccount(any())).thenReturn(accountResponse);
        when(tokenExtractor.extract(any())).thenReturn(TEST_ACCOUNT_TOKEN);
        when(tokenProvider.getSubject(any())).thenReturn(TEST_ACCOUNT_EMAIL);

        mockMvc.perform(get("/accounts/" + account.getId())
            .cookie(cookie)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("id", Matchers.is(1)))
            .andExpect(jsonPath("email", Matchers.is(TEST_ACCOUNT_EMAIL)))
            .andExpect(jsonPath("name", Matchers.is(TEST_ACCOUNT_NAME)))
            .andDo(print())
            .andDo(UserDocumentation.get());
    }

    @Test
    void update() throws Exception {
        // when(accountService.findByUserId(anyString())).thenReturn(Optional.of(account));
        when(tokenExtractor.extract(any())).thenReturn(TEST_ACCOUNT_TOKEN);
        when(tokenProvider.getSubject(any())).thenReturn(TEST_ACCOUNT_EMAIL);

        Map<String, String> params = new HashMap<>();
        params.put("name", TEST_OTHER_ACCOUNT_NAME);
        params.put("oldPassword", TEST_ACCOUNT_PASSWORD);
        params.put("newPassword", TEST_OTHER_ACCOUNT_PASSWORD);

        mockMvc.perform(put("/accounts/" + account.getId())
            .cookie(cookie)
            .contentType(APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(params)))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(UserDocumentation.update());
    }

    @Test
    void deleteAccount() throws Exception {
        // when(accountService.findByUserId(anyString())).thenReturn(Optional.of(account));
        when(tokenExtractor.extract(any())).thenReturn(TEST_ACCOUNT_TOKEN);
        when(tokenProvider.getSubject(any())).thenReturn(TEST_ACCOUNT_EMAIL);

        mockMvc.perform(delete("/accounts/" + account.getId())
            .cookie(cookie))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(UserDocumentation.delete());
    }
}
