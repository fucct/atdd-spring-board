package spring.board.demo.infra.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.infra.Token;

@Getter
@NoArgsConstructor
public class TokenResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType;

    @Builder
    public TokenResponse(String accessToken, String refreshToken, String tokenType) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
    }

    public static TokenResponse of(Token token) {
        return TokenResponse.builder()
            .accessToken(token.getAccessToken())
            .refreshToken(token.getRefreshToken())
            .tokenType(token.getTokenType())
            .build();
    }
}
