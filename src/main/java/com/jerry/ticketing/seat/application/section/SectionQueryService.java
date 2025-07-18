package com.jerry.ticketing.seat.application.section;

import com.jerry.ticketing.global.exception.common.BusinessException;
import com.jerry.ticketing.global.exception.SectionErrorCode;
import com.jerry.ticketing.seat.domain.Section;
import com.jerry.ticketing.seat.domain.port.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SectionQueryService {

    private final SectionRepository sectionRepository;

    @Transactional
    public <T> Map<Long, T> getSectionMap(List<Long> sectionIds, Function<Section, T> mapper) {
        List<Section> sections = sectionRepository.findAllById(sectionIds);

        return sections.stream()
                .collect(Collectors.toMap(
                        Section::getId,
                        mapper
                ));
    }


    @Transactional
    public Section getSection(Long concertId, String zone) {
        return sectionRepository.findByConcertIdAndZone(concertId, zone)
                .orElseThrow(() -> new BusinessException(SectionErrorCode.SECTION_NOT_FOUND));
    }

    @Transactional
    public Section getSection(Long sectionId) {
        return sectionRepository.findById(sectionId)
                .orElseThrow(() -> new BusinessException(SectionErrorCode.SECTION_NOT_FOUND));
    }


}
