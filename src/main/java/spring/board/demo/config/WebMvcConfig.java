package spring.board.demo.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import spring.board.demo.controller.prehandler.LoginUserMethodArgumentResolver;
import spring.board.demo.controller.prehandler.UserInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserInterceptor userInterceptor;
    private final LoginUserMethodArgumentResolver loginUserMethodArgumentResolver;

    public WebMvcConfig(UserInterceptor userInterceptor,
        LoginUserMethodArgumentResolver loginUserMethodArgumentResolver) {
        this.userInterceptor = userInterceptor;
        this.loginUserMethodArgumentResolver = loginUserMethodArgumentResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
            .addPathPatterns("/users/**", "/articles");
    }

    @Override
    public void addArgumentResolvers(List resolvers) {
        resolvers.add(loginUserMethodArgumentResolver);
    }
}
