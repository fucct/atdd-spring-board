package spring.board.demo.exception;

public class IdNotFoundException extends BusinessException {
    private static final String ERROR_MESSAGE = " 아이디를 찾을수 없습니다.";

    public IdNotFoundException() {
        super(ERROR_MESSAGE);
    }

    public IdNotFoundException(Long id) {
        super(id + ERROR_MESSAGE);
    }

}
