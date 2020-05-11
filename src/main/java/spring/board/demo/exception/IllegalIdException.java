package spring.board.demo.exception;

import org.springframework.dao.DataAccessException;

public class IllegalIdException extends DataAccessException {
    private static final String ERROR_MESSAGE = "아이디를 찾을수 없습니다.";

    public IllegalIdException() {
        super(ERROR_MESSAGE);
    }

    public IllegalIdException(String message) {
        super(message);
    }
}
