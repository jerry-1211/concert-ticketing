package com.jerry.ticketing.application.seat;


import com.jerry.ticketing.domain.seat.ConcertSeat;
import com.jerry.ticketing.domain.seat.ConcertSeats;
import com.jerry.ticketing.domain.seat.enums.ConcertSeatStatus;
import com.jerry.ticketing.dto.BlockingSeat;
import com.jerry.ticketing.global.validation.ConcertSeatBlockValidator;
import com.jerry.ticketing.repository.seat.ConcertSeatRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatBlockingService {

    private final ConcertSeatBlockValidator concertSeatBlockValidator;
    private final ConcertSeatRepository concertSeatRepository;


    /**
     * 좌석을 선택하고, 일시적으로 선점(Blocked)상태로 변경합니다.
     *
     * @param request 클라이언트로 부터 block 요청을 받은 요청
     */
    @Transactional
    public List<ConcertSeat> blockSeats(BlockingSeat.Request request){

        ConcertSeats concertSeats = ConcertSeats.from(
                concertSeatRepository.findByConcertIdAndSeatIdIn(request.getConcertId(), request.getConcertSeatIds()));

        concertSeatBlockValidator.validator(concertSeats, request.getConcertSeatIds());

        concertSeats.block(request.getMemberId());

        return concertSeats.item();
    }


    /**
     * 만료된 좌석 선점을 자동으로 해제합니다.
     * 스케줄러에 의해 주기적으로 실행됩니다.
     * */
    @Transactional
    public void releaseExpiredBlockedSeats(){
        OffsetDateTime now = OffsetDateTime.now();
        ConcertSeats concertSeats = ConcertSeats.from(
                concertSeatRepository.findByBlockedExpireAtBeforeAndStatus(now, ConcertSeatStatus.BLOCKED));

        concertSeats.available();

    }


}
