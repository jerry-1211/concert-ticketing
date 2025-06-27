package com.jerry.ticketing.member.api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class MemberOauthApiController {


    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        String[] cookiesToDelete = {"JSESSIONID", "SPRING_SECURITY_REMEMBER_ME_COOKIE"};
        for (String cookieName : cookiesToDelete) {
            Cookie deleteCookie = new Cookie(cookieName, "");
            deleteCookie.setMaxAge(0);
            deleteCookie.setPath("/");
            deleteCookie.setHttpOnly(true);
            deleteCookie.setSecure(request.isSecure());
            response.addCookie(deleteCookie);
        }
    }


    @GetMapping("/validate")
    public ResponseEntity<String> getMyReservation(Authentication authentication) {

        if (authentication != null) {
            return ResponseEntity.ok(authentication.getName());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
