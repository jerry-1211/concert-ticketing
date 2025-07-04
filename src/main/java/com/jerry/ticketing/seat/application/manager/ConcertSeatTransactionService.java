package com.jerry.ticketing.seat.application.manager;


import com.jerry.ticketing.global.validation.ConcertSeatBlockValidator;
import com.jerry.ticketing.seat.application.dto.web.BlockConcertSeatDto;
import com.jerry.ticketing.seat.domain.vo.ConcertSeats;
import com.jerry.ticketing.seat.infrastructure.repository.ConcertSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConcertSeatTransactionService {
    private final ConcertSeatRepository concertSeatRepository;
    private final ConcertSeatBlockValidator concertSeatBlockValidator;


    @Transactional
    public ConcertSeats block(BlockConcertSeatDto.Request request) {
        ConcertSeats concertSeats = ConcertSeats.from(
                concertSeatRepository.findByConcertIdAndIdIn(request.getConcertId(), request.getConcertSeatIds()));

        concertSeatBlockValidator.validator(concertSeats, request.getConcertSeatIds());
        concertSeats.block(request.getMemberId());

        return concertSeats;
    }


}
