package com.jerry.ticketing.application.seat.factory;


import com.jerry.ticketing.application.seat.enums.SectionType;
import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.seat.Section;
import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.ConcertErrorCode;
import com.jerry.ticketing.repository.concert.ConcertRepository;
import com.jerry.ticketing.repository.seat.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class SectionFactory {

    private final SectionRepository sectionRepository;
    private final ConcertRepository concertRepository;


    /**
     * Section A-Z 까지의 구역을 만듭니다.
     * @param concertId 콘서트 아이디
     * */
    public void createIfNotExists(Long concertId) {
        if(sectionRepository.findByConcertId(concertId).isEmpty()){
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
                                .mapToObj(zone -> Section.initSection(concert, String.valueOf((char) zone), type.getCapacity())))
                .toList();

        sectionRepository.saveAll(sections);
    }

}
