package com.jerry.ticketing.reservation.infrastructure.repository;

import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.domain.enums.ReservationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@DataJpaTest
class ReservationRepositoryAdapterTest {

    @Autowired
    private ReservationJpaRepository jpaRepository;

    private ReservationRepositoryAdapter reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepositoryAdapter(jpaRepository);
    }


    @Test
    @DisplayName("결제를 저장 하고, 초기에는 PENDING 상태이다.")
    void savePayment() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Reservation reservation = Reservation.of(1L, 1L,
                "ORDER-123", dateTime, dateTime, 10_000, 3);

        // when
        Reservation savedReservation = reservationRepository.save(reservation);

        // then
        assertThat(savedReservation)
                .extracting("memberId", "concertId", "status",
                        "orderName", "createdAt", "expiresAt", "totalAmount", "quantity")
                .containsExactlyInAnyOrder(1L, 1L, ReservationStatus.PENDING,
                        "ORDER-123", dateTime, dateTime, 10_000, 3
                );
    }


    @Test
    @DisplayName("아이디로 결제를 찾을 수 있다.")
    void findReservationById() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Reservation reservation = Reservation.of(1L, 1L,
                "ORDER-123", dateTime, dateTime, 10_000, 3);
        Reservation savedReservation = reservationRepository.save(reservation);

        // when
        Optional<Reservation> foundReservation = reservationRepository.findById(savedReservation.getId());

        // then
        assertThat(foundReservation).isPresent().hasValueSatisfying(
                m -> {
                    assertThat(m)
                            .extracting("memberId", "concertId", "status",
                                    "orderName", "createdAt", "expiresAt", "totalAmount", "quantity")
                            .containsExactlyInAnyOrder(1L, 1L, ReservationStatus.PENDING,
                                    "ORDER-123", dateTime, dateTime, 10_000, 3
                            );
                }
        );
    }


    @Test
    @DisplayName("멤버 아이디로 결제들을 찾을 수 있다.")
    void findReservationsByMemberId() {
        // given
        long memberId = 1L;
        OffsetDateTime dateTime = OffsetDateTime.now();

        Reservation reservation1 = Reservation.of(memberId, 1L,
                "ORDER-123", dateTime, dateTime, 10_000, 3);
        reservationRepository.save(reservation1);

        Reservation reservation2 = Reservation.of(memberId, 2L,
                "ORDER-456", dateTime, dateTime, 20_000, 5);
        reservationRepository.save(reservation2);

        // when
        List<Reservation> members = reservationRepository.findByMemberId(memberId);

        // then
        assertThat(members).hasSize(2)
                .extracting("memberId", "concertId", "status",
                        "orderName", "createdAt", "expiresAt", "totalAmount", "quantity")
                .containsExactlyInAnyOrder(
                        tuple(memberId, 1L, ReservationStatus.PENDING, "ORDER-123", dateTime, dateTime, 10_000, 3),
                        tuple(memberId, 2L, ReservationStatus.PENDING, "ORDER-456", dateTime, dateTime, 20_000, 5)
                );
    }


    @Test
    @DisplayName("결제를 주문 아이디로 찾을 수 있다.")
    void findReservationByOrderId() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Reservation reservation = Reservation.of(1L, 1L,
                "ORDER-123", dateTime, dateTime, 10_000, 3);
        Reservation savedReservation = reservationRepository.save(reservation);

        // when
        Optional<Reservation> foundReservation = reservationRepository.findByOrderId(savedReservation.getOrderId());

        // then
        assertThat(foundReservation).isPresent().hasValueSatisfying(
                m -> {
                    assertThat(m)
                            .extracting("memberId", "concertId", "status",
                                    "orderName", "createdAt", "expiresAt", "totalAmount", "quantity")
                            .containsExactlyInAnyOrder(1L, 1L, ReservationStatus.PENDING,
                                    "ORDER-123", dateTime, dateTime, 10_000, 3
                            );
                }
        );
    }


    @Test
    @DisplayName("기한이 지난 PENDING 상태의 결제들을 찾을 수 있다.")
    void findReservationByExpiresAtBeforeAndStatus() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        OffsetDateTime activeTime = OffsetDateTime.now().plusMinutes(30);
        OffsetDateTime expiredTime = OffsetDateTime.now().plusMinutes(10);
        OffsetDateTime expiresAtBefore = OffsetDateTime.now().plusMinutes(20);

        Reservation reservation1 = Reservation.of(1L, 1L,
                "ORDER-123", dateTime, activeTime, 10_000, 3);
        reservationRepository.save(reservation1);

        Reservation reservation2 = Reservation.of(1L, 1L,
                "ORDER-123", dateTime, expiredTime, 10_000, 3);
        reservationRepository.save(reservation2);

        // when
        List<Reservation> foundReservation = reservationRepository.findByExpiresAtBeforeAndStatus(expiresAtBefore, ReservationStatus.PENDING);

        // then
        assertThat(foundReservation).hasSize(1)
                .extracting("memberId", "concertId", "status",
                        "orderName", "createdAt", "expiresAt", "totalAmount", "quantity")
                .containsExactlyInAnyOrder(
                        tuple(1L, 1L, ReservationStatus.PENDING,
                                "ORDER-123", dateTime, expiredTime, 10_000, 3)
                );
    }


}
