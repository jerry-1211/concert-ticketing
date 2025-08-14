package com.jerry.ticketing.global.validation;

import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.vo.ConcertSeats;
import com.jerry.ticketing.global.exception.common.BusinessException;
import com.jerry.ticketing.global.exception.SeatErrorCode;
import org.springframework.stereotype.Component;


@Component
public class ConcertSeatBlockValidator {

    public void validator(ConcertSeats concertSeatsBlock, int seatCount) {
        checkAllSeatsExist(concertSeatsBlock, seatCount);
        checkAlreadyBlocked(concertSeatsBlock);
    }

    private void checkAllSeatsExist(ConcertSeats concertSeatsBlock, int seatCount) {
        if (concertSeatsBlock.isNotSame(concertSeatsBlock, seatCount)) {
            throw new BusinessException(SeatErrorCode.SEAT_NOT_FOUND);
        }
    }

    private void checkAlreadyBlocked(ConcertSeats concertSeatsBlock) {
        concertSeatsBlock.item()
                .stream()
                .filter(ConcertSeat::isNotAvailable)
                .findFirst().ifPresent(concertSeat -> {
                    throw new BusinessException(SeatErrorCode.SEAT_ALREADY_BLOCKED);
                });

    }
}
