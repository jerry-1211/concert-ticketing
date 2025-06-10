package com.jerry.ticketing.domain.reservation;

import com.jerry.ticketing.concert.infrastructure.repository.ConcertRepository;
import com.jerry.ticketing.member.infrastructure.repository.MemberRepository;
import com.jerry.ticketing.reservation.infrastructure.repository.ReservationRepository;
import com.jerry.ticketing.domain.TestFixture;
import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.reservation.domain.Reservation;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ReservationTest {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @BeforeEach
    void setUp(){
        Member member = TestFixture.createMember();
        Concert concert = TestFixture.createConcert();

        memberRepository.save(member);
        concertRepository.save(concert);
    }

    @Test
    @DisplayName("예약 생성 및 저장 검증")
    void saveReservation(){
        // Given
        Member member = memberRepository.findByEmail("jerry@naver.com").get(0);
        Concert concert = concertRepository.findByTitle("Test-Title").get(0);

        Reservation reservation = TestFixture.createReservation(member, concert);
        reservationRepository.save(reservation);

        // When
        List<Reservation> reservations = reservationRepository.findByMemberId(member.getId());

        // Then
        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).getMember()).isEqualTo(member);

    }
}