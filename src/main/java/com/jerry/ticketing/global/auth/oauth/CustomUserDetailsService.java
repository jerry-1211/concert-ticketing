package com.jerry.ticketing.global.auth.oauth;


import com.jerry.ticketing.global.auth.adopter.MemberDetails;
import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.MemberErrorCode;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.member.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).
                orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        return new MemberDetails(member);
    }
}
