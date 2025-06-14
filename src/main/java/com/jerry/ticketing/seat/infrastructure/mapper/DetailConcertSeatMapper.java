package com.jerry.ticketing.seat.infrastructure.mapper;

import com.jerry.ticketing.seat.application.dto.domain.ConcertSeatDto;
import com.jerry.ticketing.seat.application.dto.domain.SeatDto;
import com.jerry.ticketing.seat.application.dto.domain.SectionDto;
import com.jerry.ticketing.seat.application.dto.web.DetailedConcertSeatDto;
import com.jerry.ticketing.seat.domain.vo.ConcertSeats;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DetailConcertSeatMapper {
    public List<DetailedConcertSeatDto> mapToDetailDto(
            ConcertSeats concertSeats, Map<Long, SeatDto> seatDtoMap, Map<Long, SectionDto> sectionDtoMap) {

        return concertSeats.item().stream()
                .map(cs -> DetailedConcertSeatDto.from(
                        seatDtoMap.get(cs.getSeatId()),
                        ConcertSeatDto.from(cs),
                        sectionDtoMap.get(cs.getSectionId())
                ))
                .toList();
    }
}
