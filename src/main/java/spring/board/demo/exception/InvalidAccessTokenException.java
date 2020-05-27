package spring.board.demo.exception;

public class InvalidAccessTokenException extends AuthenticationException {
    public InvalidAccessTokenException() {
        super("유효하지 않은 토큰입니다.");
    }
}
