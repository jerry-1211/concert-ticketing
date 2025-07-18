package com.jerry.ticketing.seat.infrastructure.repository.concertseat;

import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.enums.ConcertSeatStatus;
import com.jerry.ticketing.seat.domain.port.ConcertSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertSeatRepositoryAdapter implements ConcertSeatRepository {

    private final ConcertSeatJpaRepository jpaRepository;

    @Override
    public ConcertSeat save(ConcertSeat concertSeat) {
        return jpaRepository.save(concertSeat);
    }

    @Override
    public List<ConcertSeat> saveAll(List<ConcertSeat> concertSeats) {
        return jpaRepository.saveAll(concertSeats);
    }

    @Override
    public List<ConcertSeat> findByConcertId(Long id) {
        return jpaRepository.findByConcertId(id);
    }

    @Override
    public List<ConcertSeat> findByIdIn(List<Long> ids) {
        return jpaRepository.findByIdIn(ids);
    }

    @Override
    public List<ConcertSeat> findByExpireAtBeforeAndStatus(OffsetDateTime now, ConcertSeatStatus status) {
        return jpaRepository.findByExpireAtBeforeAndStatus(now, status);
    }

    @Override
    public List<ConcertSeat> findByJoinConditions(Long concertId, String zone, String row) {
        return jpaRepository.findByJoinConditions(concertId, zone, row);
    }
}
