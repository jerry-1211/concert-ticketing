package com.jerry.ticketing.seat.application.initializer;


import com.jerry.ticketing.seat.application.manager.ConcertSeatManager;
import com.jerry.ticketing.seat.application.manager.SectionManager;
import com.jerry.ticketing.seat.infrastructure.repository.section.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConcertInitializationService {

    private final SectionRepository sectionRepository;
    private final SectionManager sectionManager;
    private final ConcertSeatManager concertSeatManager;

    @Transactional
    public void initializeSectionAndConcertSeats(Long concertId) {

        if (sectionRepository.existsByConcertId(concertId)) {
            return;
        }

        sectionManager.createIfNotExists(concertId);
        concertSeatManager.createIfNotExists(concertId);
    }

}
