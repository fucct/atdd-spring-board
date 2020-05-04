package spring.board.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import spring.board.demo.dto.CommentForm;
import spring.board.demo.service.ArticleService;
import spring.board.demo.service.CommentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final ArticleService articleService;
    private final CommentService commentService;

    @PostMapping("/{articleId}")
    public String addComment(@PathVariable Long articleId, CommentForm commentForm,
        RedirectAttributes redirectAttributes) {

        Long commentId = commentService.addComment(articleId, commentForm.getCommentContent(), commentForm.getCommentNickname(),
            commentForm.getCommentPassword());

        return "redirect:/post/" + articleId;
    }
}
