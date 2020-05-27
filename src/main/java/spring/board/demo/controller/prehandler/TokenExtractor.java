package spring.board.demo.controller.prehandler;

import java.util.Arrays;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import spring.board.demo.exception.NotFoundTokenException;

@Component
public class TokenExtractor {

    public String extract(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
            .filter(cookie -> Objects.equals(cookie.getName(), "token"))
            .findFirst()
            .map(Cookie::getValue)
            .orElseThrow(NotFoundTokenException::new);
    }
}
