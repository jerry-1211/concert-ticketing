package com.jerry.ticketing.member.infrastructure.repository;

import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.member.domain.enums.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderAndProviderId(Provider provider, String providerId);

    Optional<Member> findByEmail(String email);
}
