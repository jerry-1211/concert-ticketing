package com.jerry.ticketing.seat.application;


import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.vo.ConcertSeats;
import com.jerry.ticketing.seat.domain.enums.ConcertSeatStatus;
import com.jerry.ticketing.seat.application.dto.web.BlockConcertSeatDto;
import com.jerry.ticketing.global.validation.ConcertSeatBlockValidator;
import com.jerry.ticketing.seat.infrastructure.repository.ConcertSeatRepository;
import com.jerry.ticketing.seat.util.ConcertSeatIdExtractor;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertSeatCommandService {

    private final ConcertSeatBlockValidator concertSeatBlockValidator;
    private final ConcertSeatRepository concertSeatRepository;

    @Transactional
    public List<ConcertSeat> blockSeats(BlockConcertSeatDto.Request request) {

        ConcertSeats concertSeats = ConcertSeats.from(
                concertSeatRepository.findByConcertIdAndIdIn(request.getConcertId(), request.getConcertSeatIds()));

        concertSeatBlockValidator.validator(concertSeats, request.getConcertSeatIds());

        concertSeats.block(request.getMemberId());

        return concertSeats.item();
    }


    @Transactional
    public void releaseExpiredBlockedSeats() {
        OffsetDateTime now = OffsetDateTime.now();
        ConcertSeats concertSeats = ConcertSeats.from(
                concertSeatRepository.findByBlockedExpireAtBeforeAndStatus(now, ConcertSeatStatus.BLOCKED));
        concertSeats.available();
    }


    @Transactional
    public void confirmConcertSeat(String orderName) {
        List<Long> concertSeatIds = ConcertSeatIdExtractor.extractFromOrderName(orderName);
        List<ConcertSeat> concertSeats = concertSeatRepository.findByIdIn(concertSeatIds);
        concertSeats.forEach(ConcertSeat::confirm);
    }


    @Transactional
    public void saveAll(List<ConcertSeat> concertSeats) {
        concertSeatRepository.saveAll(concertSeats);
    }


}
