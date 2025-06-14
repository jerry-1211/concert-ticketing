package com.jerry.ticketing.seat.application.manager;


import com.jerry.ticketing.concert.application.ConcertQueryService;
import com.jerry.ticketing.seat.domain.enums.SeatSectionType;
import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.seat.domain.Section;
import com.jerry.ticketing.seat.infrastructure.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class SectionManager {

    private final SectionRepository sectionRepository;
    private final ConcertQueryService concertQueryService;

    /**
     * Section A-Z 까지의 구역을 만듭니다.
     */
    public void createIfNotExists(Long concertId) {
        if (sectionRepository.findByConcertId(concertId).isEmpty()) {
            create(concertId);
        }
    }


    private void create(Long concertId) {
        List<SeatSectionType> types = SeatSectionType.getSectionTypes();

        Concert concert = concertQueryService.findConcertById(concertId, Function.identity());

        List<Section> sections = types.stream()
                .flatMap(type ->
                        IntStream.rangeClosed(type.getStartZone().charAt(0), type.getEndZone().charAt(0))
                                .mapToObj(zone -> Section.of(concert.getId(), String.valueOf((char) zone), type.getCapacity())))
                .toList();

        sectionRepository.saveAll(sections);
    }

}
