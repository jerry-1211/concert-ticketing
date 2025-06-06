package com.jerry.ticketing.member.infrastructure.repository;

import com.jerry.ticketing.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {
    List<Member> findByEmail(String email);
}
