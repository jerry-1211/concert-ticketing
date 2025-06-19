package com.jerry.ticketing.member.api;


import com.jerry.ticketing.member.application.MyPageService;
import com.jerry.ticketing.member.application.dto.MyPageDto;
import com.jerry.ticketing.member.application.dto.ReservationListDto;
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
    private final MyPageService myPageService;

    @GetMapping
    public ResponseEntity<MyPageDto> getMyPage(Authentication authentication) {
        String email = authentication.getName();
        MyPageDto myPage = myPageService.getMyPage(email);

        return ResponseEntity.ok(myPage);
    }

    @PutMapping("/profile")
    public ResponseEntity<MyPageDto> updateProfile(Authentication authentication,
                                                   @RequestBody UpdateProfile.Request request) {

        String email = authentication.getName();
        MyPageDto updateProfile = myPageService.updateMyPage(email, request);

        return ResponseEntity.ok(updateProfile);
    }


    @GetMapping("/reservation")
    public ResponseEntity<List<ReservationListDto>> getMyReservation(Authentication authentication) {
        String email = authentication.getName();

        List<ReservationListDto> reservations = myPageService.getMyReservation(email);


        return ResponseEntity.ok(reservations);
    }


}
