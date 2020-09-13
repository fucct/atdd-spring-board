package spring.board.demo.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import spring.board.demo.controller.prehandler.AuthorizeCheck;
import spring.board.demo.controller.prehandler.LoginUser;
import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.accounts.dto.AccountCreateRequest;
import spring.board.demo.domain.accounts.dto.AccountCreateResponse;
import spring.board.demo.domain.accounts.dto.AccountDetailResponse;
import spring.board.demo.domain.accounts.dto.AccountResponse;
import spring.board.demo.domain.accounts.dto.AccountUpdateRequest;
import spring.board.demo.domain.accounts.dto.LoginRequest;
import spring.board.demo.domain.token.dto.TokenResponse;
import spring.board.demo.service.AccountService;
import spring.board.demo.service.DomainService;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    private final DomainService domainService;
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountCreateResponse> create(
        @Valid @RequestBody AccountCreateRequest request) {
        AccountCreateResponse response = accountService.create(request);
        return ResponseEntity.created(URI.create("/users/" + response.getId())).body(response);
    }

    @AuthorizeCheck
    @GetMapping("/mypage")
    public ResponseEntity<AccountResponse> getLoginMember(@LoginUser Account account) {
        return ResponseEntity.ok(AccountResponse.of(account));
    }


    @AuthorizeCheck
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@LoginUser Account account, @PathVariable Long id,
        @Valid @RequestBody AccountUpdateRequest request) {
        accountService.update(id, account, request);
        return ResponseEntity.noContent().build();
    }

    @AuthorizeCheck
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@LoginUser Account account, @PathVariable Long id) {
        accountService.delete(account, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse token = accountService.login(request);
        return ResponseEntity.ok(token);
    }
}
