package spring.board.demo.exception;

public class ArticleNotFoundException extends BusinessException {
    private static final String ERROR_MESSAGE = " 번 Article을 찾을수 없습니다.";

    public ArticleNotFoundException() {
        super(ERROR_MESSAGE);
    }

    public ArticleNotFoundException(Long id) {
        super(id + ERROR_MESSAGE);
    }

}
