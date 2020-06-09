package spring.board.demo.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import spring.board.demo.controller.prehandler.AuthorizeCheck;
import spring.board.demo.controller.prehandler.LoginUser;
import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.comments.dto.CommentDetailResponse;
import spring.board.demo.domain.comments.dto.CommentRequest;
import spring.board.demo.domain.comments.dto.CommentResponse;
import spring.board.demo.service.CommentService;

@RestController
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @AuthorizeCheck
    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<CommentResponse> addComment(@PathVariable("articleId") Long articleId,
        @LoginUser Account account, @Valid @RequestBody CommentRequest request) {
        return ResponseEntity.ok(commentService.addComment(articleId, account, request));
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentDetailResponse> getComment(@PathVariable("id") Long id) {
        CommentDetailResponse response = commentService.getComment(id);
        return ResponseEntity.ok(response);
    }

    @AuthorizeCheck
    @PutMapping("/comments/{id}")
    public ResponseEntity<Void> updateComment(@PathVariable("id") Long id,
        @Valid @RequestBody CommentRequest commentRequest, @LoginUser Account account) {
        commentService.updateComment(id, commentRequest, account);
        return ResponseEntity.ok().build();
    }

    @AuthorizeCheck
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> updateComment(@PathVariable("id") Long id,
        @LoginUser Account account) {
        commentService.deleteComment(id, account);
        return ResponseEntity.ok().build();
    }
}
