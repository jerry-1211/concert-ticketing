package com.jerry.ticketing.seat.infrastructure.repository.seat;

import com.jerry.ticketing.seat.domain.Seat;
import com.jerry.ticketing.seat.domain.port.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryAdapter implements SeatRepository {

    private final SeatJpaRepository jpaRepository;

    @Override
    public Seat save(Seat seat) {
        return jpaRepository.save(seat);
    }

    @Override
    public List<Seat> saveAll(List<Seat> seats) {
        return jpaRepository.saveAll(seats);
    }

    @Override
    public List<Seat> findAllById(List<Long> ids) {
        return jpaRepository.findAllById(ids);
    }

    @Override
    public Optional<Seat> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Seat> findAll() {
        return jpaRepository.findAll();
    }
}
