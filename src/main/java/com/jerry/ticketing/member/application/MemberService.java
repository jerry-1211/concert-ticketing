package com.jerry.ticketing.member.application;


import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.member.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public <T> T findMemberById(Long memberId, Function<Member, T> mapper) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        return mapper.apply(member);
    }

}
