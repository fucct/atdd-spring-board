package spring.board.demo.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import spring.board.demo.controller.prehandler.LoginUser;
import spring.board.demo.domain.comment.dto.CommentRequest;
import spring.board.demo.domain.users.User;
import spring.board.demo.service.CommentService;

@RestController
@AllArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @AuthorizeCheck
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateComment(@PathVariable("id") Long id,
        @Valid @RequestBody CommentRequest commentRequest, @LoginUser User user) {
        commentService.updateComment(id, commentRequest, user);
        return ResponseEntity.ok().build();
    }

    @AuthorizeCheck
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> updateComment(@PathVariable("id") Long id, @LoginUser User user) {
        commentService.deleteComment(id, user);
        return ResponseEntity.ok().build();
    }
}
