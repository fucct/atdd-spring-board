package spring.board.demo.exception;

public class ArticleNotFoundException extends EntityNotFoundException {
    public ArticleNotFoundException(Long id) {
        super(id + "번 글을 찾을 수 없습니다.");
    }
}
