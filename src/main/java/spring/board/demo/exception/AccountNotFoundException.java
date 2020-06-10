package spring.board.demo.exception;

public class AccountNotFoundException extends EntityNotFoundException {

    public AccountNotFoundException() {
    }

    public AccountNotFoundException(Long userId) {
        super(userId + " 회원님을 찾을 수 없습니다.");
    }

    public AccountNotFoundException(String userId) {
        super(userId + " 회원님을 찾을 수 없습니다.");
    }
}
