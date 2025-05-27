package com.jerry.ticketing.application.seat;


import com.jerry.ticketing.application.seat.validation.ConcertSeats;
import com.jerry.ticketing.application.seat.validation.SeatBlockValidator;
import com.jerry.ticketing.domain.seat.ConcertSeat;
import com.jerry.ticketing.domain.seat.enums.ConcertSeatStatus;
import com.jerry.ticketing.dto.request.SeatBlockingRequest;
import com.jerry.ticketing.exception.BusinessException;
import com.jerry.ticketing.exception.SeatErrorCode;
import com.jerry.ticketing.repository.seat.ConcertSeatRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatBlockingService {

    private final SeatBlockValidator seatBlockValidator;
    private final ConcertSeatRepository concertSeatRepository;

    /**
     * 좌석을 선택하고, 일시적으로 선점(Blocked)상태로 변경합니다.
     * @param concertId 콘서스 Id
     * @param seatIds 선택한 좌석 ID 목록
     * @param memberId 멤버 ID
     * @return 선정된 좌석 목록
     * */
    @Transactional
    public List<ConcertSeat> blockSeats(SeatBlockingRequest request){
        ConcertSeats concertSeats = ConcertSeats.from(concertSeatRepository.findByConcertIdAndSeatIdIn(concertId, seatIds));

        seatBlockValidator.validate(concertSeats, request.getSeatIds());

        concertSeats.block(request.getMemberId());
        return concertSeats.item();
    }


    /**
     * 만료된 좌석 선점을 자동으로 해제합니다.
     * 스케줄러에 의해 주기적으로 실행됩니다.
     * */
    @Transactional
    public void releaseExpiredBlockedSeats(){
        LocalDateTime now = LocalDateTime.now();
        ConcertSeats expiredConcertSeats = ConcertSeats.from(concertSeatRepository.findByBlockedExpireAtBeforeAndStatus(now, ConcertSeatStatus.BLOCKED));
        expiredConcertSeats.available();
    }
}

// BaseEntity
