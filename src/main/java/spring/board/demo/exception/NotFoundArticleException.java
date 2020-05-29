package spring.board.demo.exception;

public class NotFoundArticleException extends RuntimeException {
    public NotFoundArticleException(Long id) {
        super(id + "번 글을 찾을 수 없습니다.");
    }
}
