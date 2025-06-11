package com.jerry.ticketing.seat.application;

import com.jerry.ticketing.seat.application.dto.domain.ConcertSeatDto;
import com.jerry.ticketing.seat.application.dto.web.DetailConcertSeatDto;
import com.jerry.ticketing.seat.application.dto.domain.SeatDto;
import com.jerry.ticketing.seat.application.dto.domain.SectionDto;
import com.jerry.ticketing.seat.infrastructure.repository.ConcertSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConcertSeatApplicationService {

    private final ConcertSeatRepository concertSeatRepository;
    private final SeatService seatService;
    private final SectionService sectionService;


    @Transactional
    public DetailConcertSeatDto getConcertSeatDetail(Long concertSeatId) {
        ConcertSeatDto concertSeat = findConcertSeatById(concertSeatId);
        SeatDto seat = seatService.findSeatById(concertSeat.getSeatId());
        SectionDto section = sectionService.findSectionById(concertSeat.getSectionId());

        return DetailConcertSeatDto.from(seat, concertSeat, section);
    }

    @Transactional
    public ConcertSeatDto findConcertSeatById(Long concertSeatId) {
        return ConcertSeatDto.from(concertSeatRepository.findById(concertSeatId)
                .orElseThrow());
    }

}
