package com.jerry.ticketing.application.seat.validation;

import com.jerry.ticketing.domain.seat.ConcertSeat;
import com.jerry.ticketing.exception.BusinessException;
import com.jerry.ticketing.exception.SeatErrorCode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeatBlockValidator {


    public void validate(ConcertSeats seatBlock, List<Long> seatIds) {

        checkBlockedSeatsSame(seatBlock, seatIds);
        checkIsAlreadyBlocked(seatBlock);
    }

    private void checkBlockedSeatsSame(ConcertSeats seatBlock, List<Long> seatIds) {
        if(seatBlock.isNotSame(seatIds)){
            throw new BusinessException(SeatErrorCode.SEAT_NOT_FOUND);
        }
    }

    private void checkIsAlreadyBlocked(ConcertSeats seatBlock) {
        seatBlock.item()
                .stream()
                .filter(ConcertSeat::isNotAvailable)
                .findFirst().ifPresent(seat -> {
                    throw new BusinessException(SeatErrorCode.SEAT_ALREADY_BLOCKED);
                });
    }
}
