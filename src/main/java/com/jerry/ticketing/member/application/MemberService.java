package com.jerry.ticketing.member.application;


import com.jerry.ticketing.member.application.dto.domain.MemberDto;
import com.jerry.ticketing.member.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberDto findMemberById(Long memberId) {
        return MemberDto.from(memberRepository.findById(memberId)
                .orElseThrow());
    }

}
