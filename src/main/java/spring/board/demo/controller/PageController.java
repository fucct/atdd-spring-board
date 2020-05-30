package spring.board.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index(Model model) {
        return "service/index";
    }

    @GetMapping("/board")
    public String board(Model model) {
        return "/service/board";
    }

    @GetMapping("/write")
    public String write(Model model) {
        return "/service/write";
    }

    @GetMapping("/join")
    public String join(Model model) {
        return "service/join";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "service/login";
    }

    @GetMapping("/mypage")
    public String mypage(Model model) {
        return "service/mypage";
    }

    @GetMapping("/mypage-edit")
    public String mypageEdit(Model model) {
        return "service/mypage-edit";
    }
}
