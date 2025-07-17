package com.jerry.ticketing.reservation.infrastructure.repository;

import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.domain.enums.ReservationStatus;
import com.jerry.ticketing.reservation.domain.port.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryAdapter implements ReservationRepository {

    private final ReservationJpaRepository jpaRepository;

    @Override
    public Reservation save(Reservation reservation) {
        return jpaRepository.save(reservation);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Reservation> findByMemberId(Long id) {
        return jpaRepository.findByMemberId(id);
    }

    @Override
    public Optional<Reservation> findByOrderId(String orderId) {
        return jpaRepository.findByOrderId(orderId);
    }

    @Override
    public List<Reservation> findByExpiresAtBeforeAndStatus(OffsetDateTime expiresAtBefore, ReservationStatus status) {
        return jpaRepository.findByExpiresAtBeforeAndStatus(expiresAtBefore, status);
    }

}
