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

import spring.board.demo.controller.prehandler.LoginUser;
import spring.board.demo.domain.token.dto.TokenResponse;
import spring.board.demo.domain.users.User;
import spring.board.demo.domain.users.dto.LoginRequest;
import spring.board.demo.domain.users.dto.UserCreateRequest;
import spring.board.demo.domain.users.dto.UserCreateResponse;
import spring.board.demo.domain.users.dto.UserDetailResponse;
import spring.board.demo.domain.users.dto.UserResponse;
import spring.board.demo.domain.users.dto.UserUpdateRequest;
import spring.board.demo.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserCreateResponse> create(
        @Valid @RequestBody UserCreateRequest request) {
        UserCreateResponse response = userService.create(request);
        return ResponseEntity.created(URI.create("/users/" + response.getId())).body(response);
    }

    @AuthorizeCheck
    @GetMapping("/mypage")
    public ResponseEntity<UserResponse> getLoginMember(@LoginUser User user) {
        return ResponseEntity.ok(UserResponse.of(user));
    }

    @AuthorizeCheck
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailResponse> getUser(@PathVariable("id") Long id,
        @LoginUser User user) {
        UserDetailResponse response = userService.getDetailUser(user);
        return ResponseEntity.ok(response);
    }

    @AuthorizeCheck
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@LoginUser User user, @PathVariable Long id,
        @Valid @RequestBody UserUpdateRequest request) {
        userService.update(id, user, request);
        return ResponseEntity.noContent().build();
    }

    @AuthorizeCheck
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@LoginUser User user, @PathVariable Long id) {
        userService.delete(user, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse token = userService.login(request);
        return ResponseEntity.ok(token);
    }
}
