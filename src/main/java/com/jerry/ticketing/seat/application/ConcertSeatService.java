package com.jerry.ticketing.seat.application;

import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.application.dto.ConcertSeatSelect;
import com.jerry.ticketing.seat.infrastructure.repository.ConcertSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConcertSeatService {

    private final ConcertSeatRepository concertSeatRepository;

    @Transactional(readOnly = true)
    public List<ConcertSeatSelect.Response> getSeats(Long concertId, String zone, String row){
        List<ConcertSeat> concertSeats = concertSeatRepository.findByJoinConditions(concertId, zone, row);

        return concertSeats.stream()
                .map(ConcertSeatSelect.Response::from)
                .collect(Collectors.toList());

    }
}
