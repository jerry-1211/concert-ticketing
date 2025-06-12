package com.jerry.ticketing.seat.application;

import com.jerry.ticketing.seat.application.dto.domain.SectionDto;
import com.jerry.ticketing.seat.domain.Section;
import com.jerry.ticketing.seat.infrastructure.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SectionQueryService {

    private final SectionRepository sectionRepository;

    @Transactional
    public Map<Long, SectionDto> findSectionByIds(List<Long> sectionIds) {
        List<Section> sections = sectionRepository.findAllById(sectionIds);

        return sections.stream()
                .collect(Collectors.toMap(
                        Section::getId,
                        SectionDto::from
                ));
    }
}
