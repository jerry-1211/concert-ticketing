package com.jerry.ticketing.seat.application.seat;

import com.jerry.ticketing.seat.domain.Seat;
import com.jerry.ticketing.seat.infrastructure.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SeatCommandService {

    private final SeatRepository seatRepository;

    @Transactional
    public void saveAll(List<Seat> seats) {
        seatRepository.saveAll(seats);
    }

}
