package com.jerry.ticketing.seat.application.seat;

import com.jerry.ticketing.global.exception.common.BusinessException;
import com.jerry.ticketing.global.exception.SeatErrorCode;
import com.jerry.ticketing.seat.domain.Seat;
import com.jerry.ticketing.seat.domain.port.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SeatQueryService {

    private final SeatRepository seatRepository;

    @Transactional
    public <T> Map<Long, T> getSeatMap(List<Long> seatIds, Function<Seat, T> mapper) {
        List<Seat> seats = seatRepository.findAllById(seatIds);

        return seats.stream()
                .collect(Collectors.toMap(
                        Seat::getId,
                        mapper
                ));
    }


    @Transactional
    public Seat getSeat(Long seatId) {
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new BusinessException(SeatErrorCode.SEAT_NOT_FOUND));
    }


    @Transactional(readOnly = true)
    public boolean hasNoSeats() {
        return CollectionUtils.isEmpty(seatRepository.findAll());
    }


}
