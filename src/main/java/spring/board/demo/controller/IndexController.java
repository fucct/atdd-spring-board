package spring.board.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import spring.board.demo.dto.ArticleResponseDto;
import spring.board.demo.service.ArticleService;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final ArticleService articleService;

    @RequestMapping("/")
    public String index(Model model) {
        List<ArticleResponseDto> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "index";
    }
}
