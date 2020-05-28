package spring.board.demo.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import spring.board.demo.controller.prehandler.LoginUserMethodArgumentResolver;
import spring.board.demo.controller.prehandler.UserInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserInterceptor userInterceptor;
    private final LoginUserMethodArgumentResolver argumentResolver;

    public WebMvcConfig(UserInterceptor userInterceptor,
        LoginUserMethodArgumentResolver argumentResolver) {
        this.userInterceptor = userInterceptor;
        this.argumentResolver = argumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(argumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
            .addPathPatterns("/users/mypage", "/users/mypage/**");
    }
}
