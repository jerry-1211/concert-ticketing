package com.jerry.ticketing.domain.reservation;

import com.jerry.ticketing.seat.domain.Section;
import com.jerry.ticketing.concert.infrastructure.repository.ConcertRepository;
import com.jerry.ticketing.member.infrastructure.repository.MemberRepository;
import com.jerry.ticketing.reservation.infrastructure.repository.ReservationItemRepository;
import com.jerry.ticketing.reservation.infrastructure.repository.ReservationRepository;
import com.jerry.ticketing.seat.infrastructure.repository.ConcertSeatRepository;
import com.jerry.ticketing.seat.infrastructure.repository.SeatRepository;
import com.jerry.ticketing.domain.TestFixture;
import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.Seat;
import com.jerry.ticketing.seat.infrastructure.repository.SectionRepository;
import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.domain.ReservationItem;
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
    private SectionRepository sectionRepository;


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
    void setUp() {

        Member member = memberRepository.save(TestFixture.createMember());
        Concert concert = concertRepository.save(TestFixture.createConcert());
//        Section section = sectionRepository.save(TestFixture.createSection(concert));
        Seat seat = seatRepository.save(TestFixture.createSeat());


//        ConcertSeat concertSeat = TestFixture.createConcertSeat(concert, seat, section);
        Reservation reservation = TestFixture.createReservation(member, concert);

//        savedConcertSeat = concertSeatRepository.save(concertSeat);
        savedReservation = reservationRepository.save(reservation);
    }

    @Test
    @DisplayName("예약 아이템 생성 및 저장 검증")
    void saveReservation() {
        // Given
        ReservationItem reservationItem = ReservationItem.createReservationItem(savedReservation, savedConcertSeat);

        // When
        ReservationItem savedReservationItem = reservationItemRepository.save(reservationItem);

        // Then
        assertThat(savedReservationItem).isNotNull();
//        assertThat(savedReservationItem.getConcertSeat().getSeat().getSeatRow()).isEqualTo("A");
//        assertThat(savedReservationItem.getReservation().getMember().getEmail()).isEqualTo("jerry@naver.com");
//        assertThat(savedReservationItem.getConcertSeat().getPrice()).isEqualTo(1_000);
//        assertThat(savedReservationItem.getConcertSeat().getConcert().getVenue()).isEqualTo("Test-Venue");
    }

    @Test
    @DisplayName("예약 아이템은 연관된 콘서트 좌성 정보를 가져올 수 있다")
    void shouldAccessRelatedConcertSeatInfo() {

        // Given
        ReservationItem reservationItem = ReservationItem.createReservationItem(savedReservation, savedConcertSeat);
        ReservationItem savedReservationItem = reservationItemRepository.save(reservationItem);

        // When
        ConcertSeat concertSeat = savedReservationItem.getConcertSeat();

        // Then
        assertThat(concertSeat).isEqualTo(savedConcertSeat);
    }

}