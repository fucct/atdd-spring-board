package spring.board.demo.exception;

public class NotFoundUserException extends AuthenticationException {

    public NotFoundUserException() {
    }

    public NotFoundUserException(String userId) {
        super(userId + " 회원님을 찾을 수 없습니다.");
    }
}
