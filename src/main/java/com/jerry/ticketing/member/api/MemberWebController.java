package com.jerry.ticketing.member.api;


import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
public class MemberWebController {

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("/oauth2/callback")
    public String callback(@RequestParam("type") String type) {
        return "callback";
    }
}
