package spring.board.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/")
    public String index(Model model) {
        return "service/index";
    }

    @GetMapping("/join")
    public String join(Model model) {
        return "service/join";
    }

    @GetMapping("/login")
    public String login(Model model){
        return "service/login";
    }

    @GetMapping("/mypage")
    public String mypage(Model model){
        return "service/mypage";
    }

    @GetMapping("/mypage-edit")
    public String mypageEdit(Model model){
        return "service/mypage-edit";
    }
}
