package com.jerry.ticketing.application.seat;


import com.jerry.ticketing.domain.seat.ConcertSeat;
import com.jerry.ticketing.domain.seat.enums.ConcertSeatStatus;
import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.SeatErrorCode;
import com.jerry.ticketing.repository.seat.ConcertSeatRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatBlockingService {

    private final ConcertSeatRepository concertSeatRepository;
    private static final int BLOCKING_TIMEOUT_MINUTES = 15;


    /**
     * 좌석을 선택하고, 일시적으로 선점(Blocked)상태로 변경합니다.
     * @param concertId 콘서스 Id
     * @param seatIds 선택한 좌석 ID 목록
     * @param memberId 멤버 ID
     * @return 선정된 좌석 목록
     * */
    @Transactional
    public List<ConcertSeat> blockSeats(Long concertId, List<Long> seatIds, Long memberId){
        List<ConcertSeat> concertSeats = concertSeatRepository.findByConcertIdAndSeatIdIn(concertId, seatIds);

        if(concertSeats.size() != seatIds.size()){
            throw new BusinessException(SeatErrorCode.SEAT_NOT_FOUND);
        }

        //  좌석 상태 확인 및 변경
        for (ConcertSeat concertSeat : concertSeats) {
            if(!concertSeat.isAvailable()){
                throw new BusinessException(SeatErrorCode.SEAT_ALREADY_BLOCKED);
            }

            concertSeat.setStatus(ConcertSeatStatus.BLOCKED);
            concertSeat.setBlockedBy(memberId);
            concertSeat.setBlockedAt(OffsetDateTime.now());
            concertSeat.setBlockedExpireAt(OffsetDateTime.now().plusMinutes(BLOCKING_TIMEOUT_MINUTES));
        }

        return concertSeatRepository.saveAll(concertSeats);
    }


    /**
     * 만료된 좌석 선점을 자동으로 해제합니다.
     * 스케줄러에 의해 주기적으로 실행됩니다.
     * */
    @Transactional
    public void releaseExpiredBlockedSeats(){
        OffsetDateTime now = OffsetDateTime.now();
        List<ConcertSeat> expiredConcertSeats = concertSeatRepository.findByBlockedExpireAtBeforeAndStatus(now, ConcertSeatStatus.BLOCKED);

        for (ConcertSeat expiredConcertSeat : expiredConcertSeats) {
            expiredConcertSeat.setStatus(ConcertSeatStatus.AVAILABLE);
            expiredConcertSeat.setBlockedBy(null);
            expiredConcertSeat.setBlockedAt(null);
            expiredConcertSeat.setBlockedExpireAt(null);
        }
        concertSeatRepository.saveAll(expiredConcertSeats);
    }


}
