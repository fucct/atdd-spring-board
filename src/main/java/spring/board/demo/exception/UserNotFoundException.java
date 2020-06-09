package spring.board.demo.exception;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
    }

    public UserNotFoundException(Long userId) {
        super(userId + " 회원님을 찾을 수 없습니다.");
    }

    public UserNotFoundException(String userId) {
        super(userId + " 회원님을 찾을 수 없습니다.");
    }
}
