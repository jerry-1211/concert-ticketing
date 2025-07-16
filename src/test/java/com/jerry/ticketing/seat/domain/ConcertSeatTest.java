package com.jerry.ticketing.seat.domain;

import com.jerry.ticketing.seat.domain.enums.ConcertSeatStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;


class ConcertSeatTest {

    @Test
    @DisplayName("초기 콘서트 좌석은 예약이 가능한 AVAILABLE 상태이다.")
    void newConcertSeatShouldBeAvailable() {
        // given
        ConcertSeat concertSeat = ConcertSeat.of(1L, 1L, 1L, 1_000);

        // then
        assertThat(concertSeat.getStatus()).isEqualByComparingTo(ConcertSeatStatus.AVAILABLE);
    }

    @Test
    @DisplayName("콘서트 좌석 예약이 가능하다.")
    void concertSeatShouldBeAvailable() {
        // given
        ConcertSeat concertSeat = ConcertSeat.of(1L, 1L, 1L, 1_000);

        // then
        assertThat(concertSeat.isAvailable()).isTrue();
    }


    @Test
    @DisplayName("콘서트 좌석이 이미 예약이 불가능하다.")
    void concertSeatShouldNotBeAvailable() {
        // given
        ConcertSeat concertSeat = ConcertSeat.of(1L, 1L, 1L, 1_000);

        // when
        concertSeat.block();

        // then
        assertThat(concertSeat.isNotAvailable()).isTrue();
    }


    @Test
    @DisplayName("콘서트 좌석 예약 점유를 하면 BLOCK 상태가 된다.")
    void occupyConcertSeatShouldChangeStatusToBlock() {
        // given
        ConcertSeat concertSeat = ConcertSeat.of(1L, 1L, 1L, 1_000);
        OffsetDateTime now = OffsetDateTime.now();

        // when
        concertSeat.occupy(1L, now);

        // then
        assertThat(concertSeat)
                .extracting("status", "blockedBy", "blockedAt", "blockedExpireAt")
                .containsExactlyInAnyOrder(
                        ConcertSeatStatus.BLOCKED, 1L, now, now.plusMinutes(ConcertSeat.BLOCKING_TIMEOUT_MINUTES)
                );
    }


    @Test
    @DisplayName("콘서트 좌석 예약 확정을 하면 RESERVED 상태가 된다.")
    void confirmConcertSeatShouldChangeStatusToReserve() {
        // given
        ConcertSeat concertSeat = ConcertSeat.of(1L, 1L, 1L, 1_000);
        OffsetDateTime now = OffsetDateTime.now();

        // when
        concertSeat.occupy(1L, now);
        concertSeat.confirm();

        // then
        assertThat(concertSeat)
                .extracting("status", "blockedExpireAt")
                .containsExactlyInAnyOrder(
                        ConcertSeatStatus.RESERVED,
                        concertSeat.getBlockedAt().plusYears(ConcertSeat.CONCERT_SEAT_EXPIRE_AT)
                );
    }


    @Test
    @DisplayName("콘서트 좌석이 다시 예약이 가능한 AVAILABLE 상태가 된다.")
    void releaseConcertSeatShouldChangeStatusToAvailable() {
        // given
        ConcertSeat concertSeat = ConcertSeat.of(1L, 1L, 1L, 1_000);

        // when
        concertSeat.release();

        // then
        assertThat(concertSeat)
                .extracting("status", "blockedBy", "blockedAt", "blockedExpireAt")
                .containsExactlyInAnyOrder(
                        ConcertSeatStatus.AVAILABLE, null, null, null);
    }

}