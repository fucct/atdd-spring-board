package spring.board.demo.controller.prehandler;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import spring.board.demo.domain.token.TokenProvider;

@Component
public class UserInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;
    private final TokenExtractor tokenExtractor;

    public UserInterceptor(TokenProvider tokenProvider,
        TokenExtractor tokenExtractor) {
        this.tokenProvider = tokenProvider;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            AuthorizeCheck annotation = getCheckAnnotation((HandlerMethod)handler,
                AuthorizeCheck.class);
            if (Objects.isNull(annotation)) {
                return true;
            }
            String accessToken = tokenExtractor.extract(request);
            String email = tokenProvider.getSubject(accessToken);
            request.setAttribute("loginEmail", email);
        }
        return true;
    }

    private <T extends Annotation> T getCheckAnnotation(HandlerMethod handler,
        Class<T> annotationType) {
        return Optional.ofNullable(handler.getMethodAnnotation(annotationType))
            .orElse(handler.getBeanType().getAnnotation(annotationType));
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) throws Exception {

    }
}
