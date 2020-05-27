package spring.board.demo.exception;

public class NotMatchPasswordException extends AuthenticationException {
    public NotMatchPasswordException() {
        super("패스워드가 일치하지 않습니다.");
    }

    public NotMatchPasswordException(String message) {
        super(message);
    }
}
