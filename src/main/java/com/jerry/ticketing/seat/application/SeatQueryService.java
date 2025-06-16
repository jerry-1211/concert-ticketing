package com.jerry.ticketing.seat.application;

import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.SeatErrorCode;
import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.seat.domain.Seat;
import com.jerry.ticketing.seat.infrastructure.repository.SeatRepository;
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
    private final ConcertSeatQueryService concertSeatQueryService;

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

//controller ->  service  -> domain service ->  repoisotiry
//
//
//결제흐름이ㅑ ~
//serivce.좌석결제() {
//    좌석조회 및 블로킹,
//    payments.결제
//}

//좌석, 결제, 예약

//Concert Service -> Reservation Service
//
//Reservation Service -> Concert Service
