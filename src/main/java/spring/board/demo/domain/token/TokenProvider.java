package spring.board.demo.domain.token;

public interface TokenProvider {
    Token createToken(String userId);

    String getSubject(String accessToken);

    boolean validateAccessToken(String token);
}
