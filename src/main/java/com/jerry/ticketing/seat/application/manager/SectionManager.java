package com.jerry.ticketing.seat.application.manager;


import com.jerry.ticketing.seat.domain.enums.SectionType;
import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.seat.domain.Section;
import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.ConcertErrorCode;
import com.jerry.ticketing.concert.infrastructure.repository.ConcertRepository;
import com.jerry.ticketing.seat.infrastructure.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class SectionManager {

    private final SectionRepository sectionRepository;
    private final ConcertRepository concertRepository;


    /**
     * Section A-Z 까지의 구역을 만듭니다.
     *
     * @param concertId 콘서트 아이디
     */
    public void createIfNotExists(Long concertId) {
        if (sectionRepository.findByConcertId(concertId).isEmpty()) {
            createSection(concertId);
        }
    }


    private void createSection(Long concertId) {
        List<SectionType> types = SectionType.getSectionTypes();

        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(() -> new BusinessException(ConcertErrorCode.CONCERT_NOT_FOUND));

        List<Section> sections = types.stream()
                .flatMap(type ->
                        IntStream.rangeClosed(type.getStartZone().charAt(0), type.getEndZone().charAt(0))
                                .mapToObj(zone -> Section.of(concert.getId(), String.valueOf((char) zone), type.getCapacity())))
                .toList();

        sectionRepository.saveAll(sections);
    }

}
