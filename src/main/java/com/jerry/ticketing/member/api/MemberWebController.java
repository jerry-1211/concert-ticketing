package com.jerry.ticketing.member.api;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberWebController {
    @GetMapping("login")
    public String login() {
        return "login";
    }
}
