package com.jerry.ticketing.seat.application;

import com.jerry.ticketing.seat.application.dto.SectionDto;
import com.jerry.ticketing.seat.infrastructure.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;

    @Transactional
    public SectionDto findSectionById(Long sectionId) {
        return SectionDto.from(sectionRepository.findById(sectionId)
                .orElseThrow());
    }
}
