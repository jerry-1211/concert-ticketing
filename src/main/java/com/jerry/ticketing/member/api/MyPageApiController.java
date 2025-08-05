package com.jerry.ticketing.member.api;


import com.jerry.ticketing.global.auth.oauth.CustomOauth2User;
import com.jerry.ticketing.member.application.MyPage;
import com.jerry.ticketing.member.application.dto.MyPageDto;
import com.jerry.ticketing.member.application.dto.MyReservationListDto;
import com.jerry.ticketing.member.application.dto.UpdateProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageApiController {
    private final MyPage myPage;

    @GetMapping
    public ResponseEntity<MyPageDto> getMyPage(Authentication authentication) {
        String email = ((CustomOauth2User) authentication.getPrincipal()).getEmail();
        MyPageDto myPage = this.myPage.getMyPage(email);

        return ResponseEntity.ok(myPage);
    }

    @PutMapping("/profile")
    public ResponseEntity<MyPageDto> updateProfile(Authentication authentication,
                                                   @RequestBody UpdateProfile request) {

        String email = ((CustomOauth2User) authentication.getPrincipal()).getEmail();
        MyPageDto updateProfile = myPage.updateMyPage(email, request);

        return ResponseEntity.ok(updateProfile);
    }


    @GetMapping("/reservation")
    public ResponseEntity<List<MyReservationListDto>> getMyReservation(Authentication authentication) {
        String email = ((CustomOauth2User) authentication.getPrincipal()).getEmail();
        List<MyReservationListDto> reservations = myPage.getMyReservation(email);

        return ResponseEntity.ok(reservations);
    }


}
