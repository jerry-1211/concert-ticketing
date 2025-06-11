package com.jerry.ticketing.seat.application;

import com.jerry.ticketing.global.util.OrderNameParser;
import com.jerry.ticketing.seat.application.dto.DetailConcertSeatDto;
import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.infrastructure.repository.ConcertSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertSeatService {

    private final ConcertSeatRepository concertSeatRepository;
    private final ConcertSeatApplicationService concertSeatApplicationService;

    @Transactional(readOnly = true)
    public List<DetailConcertSeatDto> getSeats(Long concertId, String zone, String row) {
        List<ConcertSeat> concertSeats = concertSeatRepository.findByJoinConditions(concertId, zone, row);

        List<DetailConcertSeatDto> detailSeats = new ArrayList<>();


        for (ConcertSeat concertSeat : concertSeats) {
            detailSeats.add(concertSeatApplicationService.getConcertSeatDetail(concertSeat.getId()));
        }

        return detailSeats;
    }


    public void confirmConcertSeat(String orderName) {
        List<Long> concertSeatIds = OrderNameParser.extractConcertSeatIds(orderName);
        List<ConcertSeat> concertSeats = concertSeatRepository.findByIdIn(concertSeatIds);
        concertSeats.forEach(ConcertSeat::confirmConcertSeat);
    }
}
