package com.jerry.ticketing.member.application;


import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.MemberErrorCode;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.member.infrastructure.repository.MemberRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
           .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public <T> T getMemberById(Long memberId, Function<Member, T> mapper) {

        return mapper.apply(memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND)));
    }

}
