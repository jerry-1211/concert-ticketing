package com.jerry.ticketing.member.domain.port;

import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.member.domain.enums.Provider;

import java.util.Optional;


public interface MemberRepository {
    Optional<Member> findByProviderAndProviderId(Provider provider, String providerId);

    Optional<Member> findByEmail(String email);

    Optional<Member> findById(Long id);

    Member save(Member member);
}
