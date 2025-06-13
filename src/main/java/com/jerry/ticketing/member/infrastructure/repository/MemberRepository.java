package com.jerry.ticketing.member.infrastructure.repository;

import com.jerry.ticketing.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {
}
