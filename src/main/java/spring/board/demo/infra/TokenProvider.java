package spring.board.demo.infra;

public interface TokenProvider {
    Token createToken(String userId);

    String getSubject(String accessToken);

    boolean validateAccessToken(String token);
}
