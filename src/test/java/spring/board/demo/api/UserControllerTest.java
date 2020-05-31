package spring.board.demo.api;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static spring.board.demo.acceptance.AcceptanceTest.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import spring.board.demo.controller.prehandler.TokenExtractor;
import spring.board.demo.docs.UserDocumentation;
import spring.board.demo.domain.token.TokenProvider;
import spring.board.demo.domain.user.User;
import spring.board.demo.domain.user.dto.UserCreateResponse;
import spring.board.demo.service.UserService;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql("/truncate.sql")
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private TokenExtractor tokenExtractor;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private Cookie cookie;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new ShallowEtagHeaderFilter())
            .apply(documentationConfiguration(restDocumentation))
            .build();

        user = User.of(TEST_ID, TEST_USER_ID, TEST_USER_NAME, TEST_USER_PASSWORD);
        cookie = new Cookie("token", TEST_USER_TOKEN);
    }

    @Test
    void create() throws Exception {
        when(userService.create(any())).thenReturn(new UserCreateResponse(user.getId()));

        Map<String, String> params = new HashMap<>();
        params.put("userId", TEST_USER_ID);
        params.put("name", TEST_USER_NAME);
        params.put("password", TEST_USER_PASSWORD);

        mockMvc.perform(post("/users")
            .content(objectMapper.writeValueAsString(params))
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", Matchers.is(1)))
            .andDo(print())
            .andDo(UserDocumentation.create());
    }

    @Test
    void getUser() throws Exception {
        when(userService.findByUserId(anyString())).thenReturn(Optional.of(user));
        when(tokenExtractor.extract(any())).thenReturn(TEST_USER_TOKEN);
        when(tokenProvider.getSubject(any())).thenReturn(TEST_USER_ID);

        mockMvc.perform(get("/users/" + user.getId())
            .cookie(cookie)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", Matchers.is(1)))
            .andExpect(jsonPath("$.userId", Matchers.is(TEST_USER_ID)))
            .andExpect(jsonPath("$.name", Matchers.is(TEST_USER_NAME)))
            .andDo(print())
            .andDo(UserDocumentation.get());
    }

    @Test
    void update() throws Exception{
        when(userService.findByUserId(anyString())).thenReturn(Optional.of(user));
        when(tokenExtractor.extract(any())).thenReturn(TEST_USER_TOKEN);
        when(tokenProvider.getSubject(any())).thenReturn(TEST_USER_ID);

        Map<String, String> params = new HashMap<>();
        params.put("name", TEST_OTHER_USER_NAME);
        params.put("oldPassword", TEST_USER_PASSWORD);
        params.put("newPassword", TEST_OTHER_USER_PASSWORD);

        mockMvc.perform(put("/users/"+ user.getId())
            .cookie(cookie)
            .contentType(APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(params)))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(UserDocumentation.update());
    }

    @Test
    void deleteUser() throws Exception {
        when(userService.findByUserId(anyString())).thenReturn(Optional.of(user));
        when(tokenExtractor.extract(any())).thenReturn(TEST_USER_TOKEN);
        when(tokenProvider.getSubject(any())).thenReturn(TEST_USER_ID);

        mockMvc.perform(delete("/users/" + user.getId())
            .cookie(cookie))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(UserDocumentation.delete());
    }
}
