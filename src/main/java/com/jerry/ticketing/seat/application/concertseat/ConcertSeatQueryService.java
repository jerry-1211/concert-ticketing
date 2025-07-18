package com.jerry.ticketing.seat.application.concertseat;

import com.jerry.ticketing.seat.application.seat.SeatQueryService;
import com.jerry.ticketing.seat.application.section.SectionQueryService;
import com.jerry.ticketing.seat.application.concertseat.web.DetailedConcertSeatDto;
import com.jerry.ticketing.seat.application.seat.dto.SeatDto;
import com.jerry.ticketing.seat.application.section.dto.SectionDto;
import com.jerry.ticketing.seat.domain.vo.ConcertSeats;
import com.jerry.ticketing.seat.infrastructure.mapper.DetailConcertSeatMapper;
import com.jerry.ticketing.seat.domain.port.ConcertSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConcertSeatQueryService {

    private final ConcertSeatRepository concertSeatRepository;
    private final SeatQueryService seatQueryService;
    private final SectionQueryService sectionQueryService;
    private final DetailConcertSeatMapper mapper;


    @Transactional(readOnly = true)
    public List<DetailedConcertSeatDto> getDetailedConcertSeat(Long concertId, String zone, String row) {
        ConcertSeats concertSeats = new ConcertSeats(concertSeatRepository.findByJoinConditions(concertId, zone, row));

        List<Long> seatIds = concertSeats.getSeatIds();
        List<Long> sectionIds = concertSeats.getSectionIds();

        Map<Long, SeatDto> seatDtoMap = seatQueryService.getSeatMap(seatIds, SeatDto::from);
        Map<Long, SectionDto> sectionDtoMap = sectionQueryService.getSectionMap(sectionIds, SectionDto::from);

        return mapper.mapToDetailDto(concertSeats, seatDtoMap, sectionDtoMap);
    }


    @Transactional(readOnly = true)
    public boolean hasNoConcertSeats(Long concertId) {
        return CollectionUtils.isEmpty(concertSeatRepository.findByConcertId(concertId));
    }


}
