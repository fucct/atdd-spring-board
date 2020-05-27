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

import spring.board.demo.controller.AuthorizeCheck;
import spring.board.demo.domain.token.BearerTokenProvider;

@Component
public class UserInterceptor implements HandlerInterceptor {

    private BearerTokenProvider tokenProvider;
    private TokenExtractor extractor;

    public UserInterceptor() {
    }

    public UserInterceptor(BearerTokenProvider tokenProvider,
        TokenExtractor extractor) {
        this.tokenProvider = tokenProvider;
        this.extractor = extractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        AuthorizeCheck annotation = getCheckAnnotation((HandlerMethod)handler,
            AuthorizeCheck.class);
        if (Objects.isNull(annotation) || !annotation.check()) {
            request.setAttribute("loginUserId", null);
            return true;
        }
        if (annotation.check()) {
            String accessToken = extractor.extract(request);
            String userId = tokenProvider.getSubject(accessToken);
            request.setAttribute("loginUserId", userId);
            return true;
        }
        return false;
    }

    private <T extends Annotation> T getCheckAnnotation(HandlerMethod handler, Class<T> annotationType) {
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
