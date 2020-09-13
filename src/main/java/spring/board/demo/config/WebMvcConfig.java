package spring.board.demo.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import spring.board.demo.common.prehandler.LoginUserMethodArgumentResolver;
import spring.board.demo.common.prehandler.UserInterceptor;

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
            .addPathPatterns("/**")
            .excludePathPatterns("/");
    }

    @Override
    public void addArgumentResolvers(List resolvers) {
        resolvers.add(loginUserMethodArgumentResolver);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("service/index");
        registry.addViewController("/board").setViewName("service/board");
        registry.addViewController("/write").setViewName("service/write");
        registry.addViewController("/join").setViewName("service/join");
        registry.addViewController("/login").setViewName("service/login");
        registry.addViewController("/mypage").setViewName("service/mypage");
        registry.addViewController("/mypage-edit").setViewName("service/mypage-edit");

    }
}
