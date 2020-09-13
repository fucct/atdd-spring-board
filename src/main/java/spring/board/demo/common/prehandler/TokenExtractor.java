package spring.board.demo.common.prehandler;

import java.util.Arrays;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import spring.board.demo.exception.InvalidAccessTokenException;
import spring.board.demo.exception.NotFoundTokenException;

@Component
@Qualifier
public class TokenExtractor {

    public String extract(HttpServletRequest request) {
        try {
            return Arrays.stream(request.getCookies())
                .filter(cookie -> Objects.equals(cookie.getName(), "token"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(NotFoundTokenException::new);
        } catch (Exception e) {
            throw new InvalidAccessTokenException();
        }
    }
}
