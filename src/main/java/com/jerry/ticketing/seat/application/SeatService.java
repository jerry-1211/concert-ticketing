package com.jerry.ticketing.seat.application;

import com.jerry.ticketing.seat.application.dto.domain.SeatDto;
import com.jerry.ticketing.seat.infrastructure.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    @Transactional
    public SeatDto findSeatById(Long seatId) {
        return SeatDto.from(seatRepository.findById(seatId)
                .orElseThrow());
    }
}
