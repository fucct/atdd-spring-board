package spring.board.demo.exception;

public class CommentNotFoundException extends BusinessException {
    private static final String ERROR_MESSAGE = " 번 Comment를 찾을수 없습니다.";

    public CommentNotFoundException() {
        super(ERROR_MESSAGE);
    }

    public CommentNotFoundException(Long id) {
        super(id + ERROR_MESSAGE);
    }
}
