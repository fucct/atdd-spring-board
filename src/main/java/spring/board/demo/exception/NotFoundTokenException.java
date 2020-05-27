package spring.board.demo.exception;

public class NotFoundTokenException extends AuthenticationException{
    public NotFoundTokenException() {
        super("로그인을 먼저 해주세요.");
    }

    public NotFoundTokenException(String message) {
        super(message);
    }
}
