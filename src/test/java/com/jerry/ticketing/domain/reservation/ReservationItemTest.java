package com.jerry.ticketing.domain.reservation;

import com.jerry.ticketing.repository.concert.ConcertRepository;
import com.jerry.ticketing.repository.member.MemberRepository;
import com.jerry.ticketing.repository.reservation.ReservationItemRepository;
import com.jerry.ticketing.repository.reservation.ReservationRepository;
import com.jerry.ticketing.repository.seat.ConcertSeatRepository;
import com.jerry.ticketing.repository.seat.SeatRepository;
import com.jerry.ticketing.domain.TestFixture;
import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.member.Member;
import com.jerry.ticketing.domain.seat.ConcertSeat;
import com.jerry.ticketing.domain.seat.Seat;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class ReservationItemTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ConcertSeatRepository concertSeatRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationItemRepository reservationItemRepository;

    private ConcertSeat savedConcertSeat;

    private Reservation savedReservation;

    @BeforeEach
    void setUp(){

        Member member = memberRepository.save(TestFixture.createMember());
        Concert concert = concertRepository.save(TestFixture.createConcert());
        Seat seat = seatRepository.save(TestFixture.createSeat());


        ConcertSeat concertSeat = TestFixture.createConcertSeat(concert, seat);
        Reservation reservation = TestFixture.createReservation(member, concert);

        savedConcertSeat = concertSeatRepository.save(concertSeat);
        savedReservation = reservationRepository.save(reservation);
    }

    @Test
    @DisplayName("예약 아이템 생성 및 저장 검증")
    void saveReservation(){
        // Given
        ReservationItem reservationItem = ReservationItem.builder()
                .concertSeat(savedConcertSeat)
                .reservation(savedReservation)
                .build();

        // When
        ReservationItem savedReservationItem = reservationItemRepository.save(reservationItem);

        // Then
        assertThat(savedReservationItem).isNotNull();
        assertThat(savedReservationItem.getConcertSeat().getSeat().getSeatRow()).isEqualTo("A");
        assertThat(savedReservationItem.getReservation().getMember().getEmail()).isEqualTo("jerry@naver.com");
        assertThat(savedReservationItem.getConcertSeat().getPrice()).isEqualTo(100_000);
        assertThat(savedReservationItem.getConcertSeat().getConcert().getVenue()).isEqualTo("일산 고양시 대화동");
    }

    @Test
    @DisplayName("예약 아이템은 연관된 콘서트 좌성 정보를 가져올 수 있다")
    void shouldAccessRelatedConcertSeatInfo(){

        // Given
        ReservationItem reservationItem = ReservationItem.builder()
                .concertSeat(savedConcertSeat)
                .reservation(savedReservation)
                .build();

        ReservationItem savedReservationItem = reservationItemRepository.save(reservationItem);

        // When
        ConcertSeat concertSeat = savedReservationItem.getConcertSeat();

        // Then
        assertThat(concertSeat).isEqualTo(savedConcertSeat);
    }

}