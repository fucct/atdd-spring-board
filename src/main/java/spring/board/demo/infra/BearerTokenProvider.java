package spring.board.demo.infra;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import spring.board.demo.exception.InvalidAccessTokenException;

@Component
public class BearerTokenProvider implements TokenProvider {
    private static final SignatureAlgorithm ACCESS_SIGNATURE = SignatureAlgorithm.HS512;
    private static final SignatureAlgorithm REFRESH_SIGNATURE = SignatureAlgorithm.HS256;
    private static final String TOKEN_TYPE = "bearer";
    private static final String ACCESS_SECRET_KEY = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private static final String REFRESH_SECRET_KEY = "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
    private static final Long ACCESS_EXPIRE_PERIOD = 1000 * 60 * 30L;
    private static final Long REFRESH_EXPIRE_PERIOD = 1000 * 60 * 60 * 24 * 14L;

    public Token createToken(String email) {
        String accessToken = Jwts.builder()
            .setHeaderParam("type", TOKEN_TYPE)
            .setHeaderParam("issueDate", System.currentTimeMillis())
            .setSubject(email)
            .signWith(ACCESS_SIGNATURE, ACCESS_SECRET_KEY)
            .compact();

        String refreshToken = Jwts.builder()
            .setHeaderParam("type", TOKEN_TYPE)
            .setHeaderParam("issueDate", System.currentTimeMillis())
            .setSubject(accessToken)
            .signWith(REFRESH_SIGNATURE, ACCESS_SECRET_KEY)
            .compact();

        return Token.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .tokenType(TOKEN_TYPE)
            .build();
    }

    public String getSubject(String accessToken) {
        if (validateAccessToken(accessToken)) {
            return Jwts.parser()
                .setSigningKey(ACCESS_SECRET_KEY)
                .parseClaimsJws(accessToken)
                .getBody().getSubject();
        }
        throw new InvalidAccessTokenException();
    }

    public boolean validateAccessToken(String token) {
        JwsHeader header = Jwts.parser()
            .setSigningKey(ACCESS_SECRET_KEY)
            .parseClaimsJws(token)
            .getHeader();
        return System.currentTimeMillis() - (Long)header.get("issueDate") < ACCESS_EXPIRE_PERIOD;
    }

    private boolean validateRefreshToken(Token token) {
        JwsHeader header = Jwts.parser()
            .setSigningKey(REFRESH_SECRET_KEY)
            .parseClaimsJws(token.getRefreshToken())
            .getHeader();
        return System.currentTimeMillis() - (Long)header.get("issueDate") < REFRESH_EXPIRE_PERIOD;
    }
}
