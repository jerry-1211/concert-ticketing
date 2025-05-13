package com.jerry.ticketing.Repository.member;

import com.jerry.ticketing.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {
    List<Member> findByEmail(String email);
}
