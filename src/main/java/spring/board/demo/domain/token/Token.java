package spring.board.demo.domain.token;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Token {

    private final String accessToken;
    private final String refreshToken;
    private final String tokenType;

    @Builder
    public Token(String accessToken, String refreshToken, String tokenType) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
    }
}
