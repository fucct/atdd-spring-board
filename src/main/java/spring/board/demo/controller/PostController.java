package spring.board.demo.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import spring.board.demo.domain.Article;
import spring.board.demo.dto.ArticleForm;
import spring.board.demo.dto.ArticleResponseDto;
import spring.board.demo.dto.CommentForm;
import spring.board.demo.service.ArticleService;
import spring.board.demo.service.CommentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final ArticleService articleService;
    private final CommentService commentService;

    @GetMapping("/save")
    public String postForm(Model model) {
        ArticleForm articleForm = new ArticleForm();
        model.addAttribute("articleForm", articleForm);
        return "/posts-save";
    }

    @PostMapping("/save")
    public String post(@Valid ArticleForm articleForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "index";
        }
        Article article = articleForm.toArticle();

        Long articleId = articleService.save(article);
        return "redirect:/post/" + articleId;
    }

    @DeleteMapping("/delete")
    public String

    @GetMapping("/{articleId}")
    public String readArticle(@PathVariable Long articleId, Model model) {
        ArticleResponseDto articleResponseDto = articleService.findById(articleId);
        model.addAttribute("article", articleResponseDto);
        return "posts-read";
    }

    @PostMapping("/comment/{articleId}")
    public String addComment(@PathVariable Long articleId, CommentForm commentForm, Model model) {

        commentService.addComment(articleId, commentForm.getCommentContent(), commentForm.getCommentNickname(),
            commentForm.getCommentPassword());
        return "redirect:/post/" + articleId;
    }
}
