package com.jerry.ticketing.reservation.domain;

import com.jerry.ticketing.reservation.domain.enums.ReservationStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {

    @Test
    @DisplayName("예약이 확정되면, CONFIMED 상태되고 만료기한이 늘어난다.")
    void reservationConfirmationUpdatesStatusAndExpiration() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Reservation reservation = Reservation.of(1L, 1L,
                "ORDER-123", dateTime, dateTime, 10_000, 3);

        // when
        reservation.confirmReservation();

        // then
        assertThat(reservation).extracting("status", "expiresAt")
                .containsExactlyInAnyOrder(ReservationStatus.CONFIRMED,
                        dateTime.plusMinutes(Reservation.RESERVATION_TIMEOUT));

    }

    @Test
    @DisplayName("예약이 취소되면, CANCELED 상태가 된다.")
    void reservationCancellationUpdatesToCanceledStatus() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Reservation reservation = Reservation.of(1L, 1L,
                "ORDER-123", dateTime, dateTime, 10_000, 3);

        // when
        reservation.cancelReservation();

        // then
        assertThat(reservation.getStatus()).isEqualByComparingTo(ReservationStatus.CANCELLED);
    }


    @Test
    @DisplayName("예약의 주문 아이디가 업데이트 된다.")
    void updateReservationOrderId() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        String newOrderId = "New-ORDER-123";
        Reservation reservation = Reservation.of(1L, 1L,
                "ORDER-123", dateTime, dateTime, 10_000, 3);

        // when
        reservation.updateOrderId(newOrderId);

        // then
        assertThat(reservation.getOrderId()).isEqualTo(newOrderId);
    }

}