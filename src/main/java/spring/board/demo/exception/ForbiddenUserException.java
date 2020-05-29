package spring.board.demo.exception;

public class ForbiddenUserException extends ForbiddenException {
    public ForbiddenUserException() {
        super("작성자만 글을 수정할 수 있습니다.");
    }
}
