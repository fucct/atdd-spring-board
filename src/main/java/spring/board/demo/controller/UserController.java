package spring.board.demo.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.board.demo.domain.token.dto.TokenResponse;
import spring.board.demo.domain.user.dto.LoginRequest;
import spring.board.demo.domain.user.dto.UserCreateRequest;
import spring.board.demo.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Long> create(@Valid @RequestBody UserCreateRequest request){
        Long id = userService.create(request);
        return ResponseEntity.created(URI.create("/users/"+id)).body(id);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse token = userService.login(request);
        return ResponseEntity.ok(token);

    }
}
