package com.jerry.ticketing.global.auth.jwt;


import com.jerry.ticketing.member.application.MemberQueryService;
import com.jerry.ticketing.member.application.dto.domain.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtUserService {
    private final MemberQueryService memberQueryService;

    @Transactional
    public MemberDto getMemberByEmail(String email) {
        return memberQueryService.getMemberByEmail(email, MemberDto::from);
    }
}
