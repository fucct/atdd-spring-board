package spring.board.demo.controller.prehandler;

import static org.springframework.web.context.request.RequestAttributes.*;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import spring.board.demo.domain.accounts.Account;
import spring.board.demo.exception.AccessDeniedException;
import spring.board.demo.exception.UserNotFoundException;
import spring.board.demo.service.AccountService;

@Component
@Qualifier("loginUser")
public class LoginUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final AccountService accountService;

    public LoginUserMethodArgumentResolver(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String email = (String)webRequest.getAttribute("loginEmail", SCOPE_REQUEST);
        if (Objects.isNull(email)) {
            return new Account();
        }
        try {
            return accountService.findByEmail(email);
        } catch (UserNotFoundException e) {
            throw new AccessDeniedException("비정상적인 접근입니다. 다시 로그인해주세요");
        }
    }
}
