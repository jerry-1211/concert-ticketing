package com.jerry.ticketing.seat.application;


import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.SeatErrorCode;
import com.jerry.ticketing.redis.ConcertSeatCacheService;
import com.jerry.ticketing.seat.application.manager.ConcertSeatTransactionService;
import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.Section;
import com.jerry.ticketing.seat.domain.vo.ConcertSeats;
import com.jerry.ticketing.seat.domain.enums.ConcertSeatStatus;
import com.jerry.ticketing.seat.application.dto.web.BlockConcertSeatDto;
import com.jerry.ticketing.seat.infrastructure.repository.ConcertSeatRepository;
import com.jerry.ticketing.seat.util.ConcertSeatIdExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConcertSeatCommandService {

    private final ConcertSeatRepository concertSeatRepository;
    private final SectionCommandService sectionCommandService;
    private final SectionQueryService sectionQueryService;
    private final ConcertSeatCacheService concertSeatCacheService;
    private final ConcertSeatTransactionService concertSeatTransactionService;


    public List<ConcertSeat> blockSeats(BlockConcertSeatDto.Request request) {

        if (concertSeatCacheService.AllConcertSeatsNotCached(request.getConcertId(), request.getConcertSeatIds())) {
            ConcertSeats concertSeats = concertSeatTransactionService.block(request);
            concertSeatCacheService.saveToCache(concertSeats.item());
            return concertSeats.item();
        } else {
            throw new BusinessException(SeatErrorCode.SEAT_ALREADY_BLOCKED);
        }
    }


    @Transactional
    public void releaseExpiredConcertSeats() {
        OffsetDateTime now = OffsetDateTime.now();
        ConcertSeats concertSeats = ConcertSeats.from(
                concertSeatRepository.findByBlockedExpireAtBeforeAndStatus(now, ConcertSeatStatus.BLOCKED));
        concertSeats.available();
    }


    @Transactional
    public void confirm(String orderName) {
        List<Long> concertSeatIds = ConcertSeatIdExtractor.extractFromOrderName(orderName);
        List<ConcertSeat> concertSeats = concertSeatRepository.findByIdIn(concertSeatIds);

        for (ConcertSeat concertSeat : concertSeats) {
            concertSeat.confirm();
            Section section = sectionQueryService.getSection(concertSeat.getSectionId());
            sectionCommandService.decrease(section);
        }
    }


    @Transactional
    public void saveAll(List<ConcertSeat> concertSeats) {
        concertSeatRepository.saveAll(concertSeats);
    }


}
