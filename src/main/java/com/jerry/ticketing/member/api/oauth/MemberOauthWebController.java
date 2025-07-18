package com.jerry.ticketing.member.api.oauth;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
public class MemberOauthWebController {

    @GetMapping("/oauth2/callback")
    public String callback(@RequestParam("token") String token,
                           @RequestParam("type") String type) {
        return "auth/oauth-callback";
    }
}
