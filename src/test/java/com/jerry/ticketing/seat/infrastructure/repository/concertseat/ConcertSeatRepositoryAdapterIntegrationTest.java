package com.jerry.ticketing.seat.infrastructure.repository.concertseat;

import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.Seat;
import com.jerry.ticketing.seat.domain.Section;
import com.jerry.ticketing.seat.domain.enums.SeatType;
import com.jerry.ticketing.seat.domain.port.ConcertSeatRepository;
import com.jerry.ticketing.seat.domain.port.SeatRepository;
import com.jerry.ticketing.seat.domain.port.SectionRepository;
import com.jerry.ticketing.seat.infrastructure.repository.seat.SeatJpaRepository;
import com.jerry.ticketing.seat.infrastructure.repository.seat.SeatRepositoryAdapter;
import com.jerry.ticketing.seat.infrastructure.repository.section.SectionJpaRepository;
import com.jerry.ticketing.seat.infrastructure.repository.section.SectionRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@DataJpaTest
class ConcertSeatRepositoryAdapterIntegrationTest {

    @Autowired
    private ConcertSeatJpaRepository concertSeatJpaRepository;

    private ConcertSeatRepository concertSeatRepository;

    @Autowired
    private SectionJpaRepository sectionJpaRepository;

    private SectionRepository sectionRepository;

    @Autowired
    private SeatJpaRepository seatJpaRepository;

    private SeatRepository seatRepository;


    @BeforeEach
    void setUp() {
        concertSeatRepository = new ConcertSeatRepositoryAdapter(concertSeatJpaRepository);
        sectionRepository = new SectionRepositoryAdapter(sectionJpaRepository);
        seatRepository = new SeatRepositoryAdapter(seatJpaRepository);
    }

    @Test
    @DisplayName("콘서트 id, zone, row로 콘서트 좌석들을 찾을 수 있다.")
    void saveAllConcertSeat() {
        // given
        long concertId = 1L;
        String zone = "A";
        String row = "a";

        Section section1 = Section.of(concertId, zone, 100);
        Section section2 = Section.of(concertId, "B", 100);
        long sectionId = sectionRepository.save(section1).getId();
        sectionRepository.save(section2);

        Seat seat1 = Seat.of(row, 10, SeatType.ECONOMY);
        Seat seat2 = Seat.of("b", 11, SeatType.ECONOMY);
        Seat seat3 = Seat.of("c", 12, SeatType.ECONOMY);
        Long seatId = seatRepository.save(seat1).getId();
        seatRepository.save(seat2);
        seatRepository.save(seat3);

        ConcertSeat concertSeat1 = ConcertSeat.of(concertId, seatId, sectionId, 1_000);
        ConcertSeat concertSeat2 = ConcertSeat.of(concertId, seatId, sectionId, 1_000);
        ConcertSeat concertSeat3 = ConcertSeat.of(concertId, 2L, 2L, 1_000);
        ConcertSeat concertSeat4 = ConcertSeat.of(concertId, 1L, 2L, 1_000);
        ConcertSeat concertSeat5 = ConcertSeat.of(2L, 1L, 2L, 2_000);
        concertSeatRepository.saveAll(List.of(concertSeat1, concertSeat2, concertSeat3, concertSeat4, concertSeat5));

        // when
        List<ConcertSeat> concertSeats = concertSeatRepository.findByJoinConditions(concertId, zone, row);

        // then
        assertThat(concertSeats).hasSize(2)
                .extracting("concertId", "seatId", "sectionId", "amount")
                .containsExactlyInAnyOrder(
                        tuple(concertId, seatId, sectionId, 1_000),
                        tuple(concertId, seatId, sectionId, 1_000)
                );
    }

}