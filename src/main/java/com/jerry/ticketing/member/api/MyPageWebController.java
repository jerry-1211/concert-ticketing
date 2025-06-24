package com.jerry.ticketing.member.api;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageWebController {

    @GetMapping
    public String myPage() {
        return "member/profile";
    }


    @GetMapping("/reservations")
    public String myReservation() {
        return "member/my-reservations";
    }

}
