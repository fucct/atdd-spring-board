package spring.board.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.accounts.AccountRepository;
import spring.board.demo.domain.accounts.dto.AccountCreateRequest;
import spring.board.demo.domain.accounts.dto.AccountCreateResponse;
import spring.board.demo.domain.accounts.dto.AccountUpdateRequest;
import spring.board.demo.domain.accounts.dto.LoginRequest;
import spring.board.demo.domain.token.Token;
import spring.board.demo.domain.token.TokenProvider;
import spring.board.demo.domain.token.dto.TokenResponse;
import spring.board.demo.exception.AccountNotFoundException;

@Transactional
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TokenProvider tokenProvider;

    public AccountService(AccountRepository accountRepository, TokenProvider tokenProvider) {
        this.accountRepository = accountRepository;
        this.tokenProvider = tokenProvider;
    }

    public AccountCreateResponse create(AccountCreateRequest request) {
        Account save = accountRepository.save(request.toAccount());
        return new AccountCreateResponse(save.getId());
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest request) {
        Account account = findByEmail(request.getEmail());
        account.checkPassword(request.getPassword());
        Token token = tokenProvider.createToken(request.getEmail());
        return TokenResponse.of(token);
    }

    public void update(Long id, Account account, AccountUpdateRequest request) {
        account.update(id, request);
        accountRepository.save(account);
    }

    public void delete(Account account, Long id) {
        accountRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Account findByEmail(String userId) {
        return accountRepository.findByEmail(userId)
            .orElseThrow(() -> new AccountNotFoundException(userId));
    }

    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }
}
