package spring.board.demo.exception;

public class NotMatchUserIdException extends AuthenticationException {
    public NotMatchUserIdException() {
        super("아이디가 일치하지 않습니다.");
    }

    public NotMatchUserIdException(String message) {
        super(message);
    }
}
