package spring.board.demo.controller.prehandler;

import static org.springframework.web.context.request.RequestAttributes.*;

import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import spring.board.demo.domain.user.User;
import spring.board.demo.exception.AuthenticationException;
import spring.board.demo.exception.NotFoundUserException;
import spring.board.demo.service.UserService;


@Component
public class LoginUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    public LoginUserMethodArgumentResolver(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String userId = (String) webRequest.getAttribute("loginUserId", SCOPE_REQUEST);
        if (Objects.isNull(userId)) {
            return new User();
        }
        try {
            return userService.findByUserId(userId);
        } catch (NotFoundUserException e) {
            throw new AuthenticationException("비정상적인 접근입니다. 다시 로그인해주세요");
        }
    }
}
