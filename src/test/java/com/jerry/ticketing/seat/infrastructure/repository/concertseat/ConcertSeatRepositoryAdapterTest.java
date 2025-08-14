package com.jerry.ticketing.seat.infrastructure.repository.concertseat;

import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.enums.ConcertSeatStatus;
import com.jerry.ticketing.seat.domain.port.ConcertSeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@DataJpaTest
class ConcertSeatRepositoryAdapterTest {

    @Autowired
    private ConcertSeatJpaRepository jpaRepository;

    private ConcertSeatRepository concertSeatRepository;

    @BeforeEach
    void setUp() {
        concertSeatRepository = new ConcertSeatRepositoryAdapter(jpaRepository);
    }

    @Test
    @DisplayName("콘서트 좌석을 한번에 저장 할 수 있다.")
    void saveAllConcertSeat() {
        // given
        ConcertSeat concertSeat1 = ConcertSeat.of(1L, 1L, 1L, 1_000);
        ConcertSeat concertSeat2 = ConcertSeat.of(1L, 2L, 2L, 1_000);

        // when
        List<ConcertSeat> concertSeats = concertSeatRepository.saveAll(List.of(concertSeat1, concertSeat2));

        // then
        assertThat(concertSeats)
                .extracting("concertId", "seatId", "sectionId", "amount", "status")
                .containsExactlyInAnyOrder(
                        tuple(1L, 1L, 1L, 1_000, ConcertSeatStatus.AVAILABLE),
                        tuple(1L, 2L, 2L, 1_000, ConcertSeatStatus.AVAILABLE)
                );
    }


    @Test
    @DisplayName("콘서트 id로 콘서트 좌석들을 찾을 수 있다.")
    void findConcertSeatByConcertId() {
        // given
        long concertId1 = 1L;
        long concertId2 = 2L;
        ConcertSeat concertSeat1 = ConcertSeat.of(concertId1, 1L, 1L, 1_000);
        ConcertSeat concertSeat2 = ConcertSeat.of(concertId1, 2L, 2L, 1_000);
        ConcertSeat concertSeat3 = ConcertSeat.of(concertId2, 1L, 3L, 1_000);
        concertSeatRepository.saveAll(List.of(concertSeat1, concertSeat2, concertSeat3));

        // when
        List<ConcertSeat> foundConcertSeats = concertSeatRepository.findByConcertId(concertId1);

        // then
        assertThat(foundConcertSeats).hasSize(2)
                .extracting("concertId", "seatId", "sectionId", "amount", "status")
                .containsExactlyInAnyOrder(
                        tuple(1L, 1L, 1L, 1_000, ConcertSeatStatus.AVAILABLE),
                        tuple(1L, 2L, 2L, 1_000, ConcertSeatStatus.AVAILABLE)
                );
    }

    @Test
    @DisplayName("콘서드 좌석 id들로 콘서트 좌석들을 찾을 수 있다.")
    void findConcertSeatByIn() {
        // given
        long concertId1 = 1L;
        long concertId2 = 2L;

        ConcertSeat concertSeat1 = ConcertSeat.of(concertId1, 1L, 1L, 1_000);
        ConcertSeat concertSeat2 = ConcertSeat.of(concertId1, 2L, 2L, 1_000);
        ConcertSeat concertSeat3 = ConcertSeat.of(concertId2, 1L, 3L, 1_000);
        concertSeatRepository.saveAll(List.of(concertSeat1, concertSeat2, concertSeat3));

        // when
        List<ConcertSeat> foundConcertSeats = concertSeatRepository.findByIdIn(
                List.of(concertSeat1.getId(), concertSeat2.getId()));

        // then
        assertThat(foundConcertSeats).hasSize(2)
                .extracting("concertId", "seatId", "sectionId", "amount", "status")
                .containsExactlyInAnyOrder(
                        tuple(1L, 1L, 1L, 1_000, ConcertSeatStatus.AVAILABLE),
                        tuple(1L, 2L, 2L, 1_000, ConcertSeatStatus.AVAILABLE));

    }

    @Test
    @DisplayName("콘서트 id와 고유 좌석 id들로 콘서트 좌석들을 찾을 수 있다.")
    void findConcertSeatConcertIdAndSeatIdIn() {
        // given
        long concertId1 = 1L;

        long seatId1 = 1L;
        long seatId2 = 2L;

        ConcertSeat concertSeat1 = ConcertSeat.of(concertId1, 1L, seatId1, 1_000);
        ConcertSeat concertSeat2 = ConcertSeat.of(concertId1, 2L, seatId2, 1_000);
        ConcertSeat concertSeat3 = ConcertSeat.of(2L, 1L, 3L, 1_000);
        concertSeatRepository.saveAll(List.of(concertSeat1, concertSeat2, concertSeat3));

        // when
        List<ConcertSeat> foundConcertSeats = concertSeatRepository.findByConcertIdAndSeatIdIn(
                concertId1, List.of(seatId1, seatId2));

        // then
        assertThat(foundConcertSeats).hasSize(2)
                .extracting("concertId", "seatId", "sectionId", "amount", "status")
                .containsExactlyInAnyOrder(
                        tuple(1L, 1L, 1L, 1_000, ConcertSeatStatus.AVAILABLE),
                        tuple(1L, 2L, 2L, 1_000, ConcertSeatStatus.AVAILABLE));
    }


    @Test
    @DisplayName("콘서드 좌석 id들로 콘서트 좌석들을 찾을 수 있다.")
    void findConcertSeatByExpireAtBeforeAndStatus() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        OffsetDateTime expiredTime = dateTime.minusMinutes(ConcertSeat.BLOCKING_TIMEOUT_MINUTES * 2);
        OffsetDateTime activeTime = dateTime.plusMinutes(ConcertSeat.BLOCKING_TIMEOUT_MINUTES * 2);

        ConcertSeat concertSeat1 = ConcertSeat.of(1L, 1L, 1L, 1_000);
        concertSeat1.occupy(1L, expiredTime);
        ConcertSeat concertSeat2 = ConcertSeat.of(1L, 2L, 2L, 1_000);
        concertSeat2.occupy(1L, activeTime);

        concertSeatRepository.saveAll(List.of(concertSeat1, concertSeat2));

        // when
        List<ConcertSeat> foundConcertSeats = concertSeatRepository.findByExpireAtBeforeAndStatus(dateTime, ConcertSeatStatus.BLOCKED);

        // then
        assertThat(foundConcertSeats).hasSize(1)
                .extracting("concertId", "seatId", "sectionId", "amount", "status")
                .containsExactlyInAnyOrder(tuple(1L, 1L, 1L, 1_000, ConcertSeatStatus.BLOCKED));
    }

    @Test
    @DisplayName("콘서트 id, zone, row에 맞는 콘서드 좌석들을 찾을 수 있다.")
    void findConcertSeatByJoinConditions() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        OffsetDateTime expiredTime = dateTime.minusMinutes(ConcertSeat.BLOCKING_TIMEOUT_MINUTES * 2);
        OffsetDateTime activeTime = dateTime.plusMinutes(ConcertSeat.BLOCKING_TIMEOUT_MINUTES * 2);

        ConcertSeat concertSeat1 = ConcertSeat.of(1L, 1L, 1L, 1_000);
        concertSeat1.occupy(1L, expiredTime);
        ConcertSeat concertSeat2 = ConcertSeat.of(1L, 2L, 2L, 1_000);
        concertSeat2.occupy(1L, activeTime);

        concertSeatRepository.saveAll(List.of(concertSeat1, concertSeat2));

        // when
        List<ConcertSeat> foundConcertSeats = concertSeatRepository.findByExpireAtBeforeAndStatus(dateTime, ConcertSeatStatus.BLOCKED);

        // then
        assertThat(foundConcertSeats).hasSize(1)
                .extracting("concertId", "seatId", "sectionId", "amount", "status")
                .containsExactlyInAnyOrder(tuple(1L, 1L, 1L, 1_000, ConcertSeatStatus.BLOCKED));
    }

}