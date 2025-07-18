package com.jerry.ticketing.seat.domain.port;

import com.jerry.ticketing.seat.domain.Seat;

import java.util.List;
import java.util.Optional;

public interface SeatRepository {
    Seat save(Seat seat);

    List<Seat> saveAll(List<Seat> seats);

    List<Seat> findAllById(List<Long> ids);

    Optional<Seat> findById(Long id);

    List<Seat> findAll();
}
