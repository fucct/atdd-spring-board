package spring.board.demo.exception;

public class InvalidAccessTokenException extends AccessDeniedException {
    public InvalidAccessTokenException() {
        super("유효하지 않은 토큰입니다.");
    }
}
