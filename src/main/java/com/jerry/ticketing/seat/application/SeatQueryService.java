package com.jerry.ticketing.seat.application;

import com.jerry.ticketing.seat.application.dto.domain.SeatDto;
import com.jerry.ticketing.seat.domain.Seat;
import com.jerry.ticketing.seat.infrastructure.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SeatQueryService {

    private final SeatRepository seatRepository;

    @Transactional
    public <T> Map<Long, T> findSeatByIds(List<Long> seatIds, Function<Seat, T> mapper) {
        List<Seat> seats = seatRepository.findAllById(seatIds);

        return seats.stream()
                .collect(Collectors.toMap(
                        Seat::getId,
                        mapper
                ));
    }
}
