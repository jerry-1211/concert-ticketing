package com.jerry.ticketing.seat.application.concertseat;


import com.jerry.ticketing.global.validation.ConcertSeatBlockValidator;
import com.jerry.ticketing.seat.application.concertseat.web.BlockConcertSeatDto;
import com.jerry.ticketing.seat.domain.vo.ConcertSeats;
import com.jerry.ticketing.seat.domain.port.ConcertSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class ConcertSeatTransactionService {
    private final ConcertSeatRepository concertSeatRepository;
    private final ConcertSeatBlockValidator concertSeatBlockValidator;


    @Transactional
    public ConcertSeats occupy(BlockConcertSeatDto.Request request) {
        OffsetDateTime now = OffsetDateTime.now();
        ConcertSeats concertSeats = ConcertSeats.from(
                concertSeatRepository.findByConcertIdAndSeatIdIn(request.getConcertId(), request.getSeatIds()));

        int seatCount = request.getSeatIds().size();
        concertSeatBlockValidator.validator(concertSeats, seatCount);
        concertSeats.occupy(request.getMemberId(), now);

        return concertSeats;
    }


}
