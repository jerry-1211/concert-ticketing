package com.jerry.ticketing.application.concert;

import com.jerry.ticketing.application.seat.ConcertSeatInitializer;
import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.dto.request.ConcertCreateRequest;
import com.jerry.ticketing.dto.response.ConcertResponse;
import com.jerry.ticketing.exception.BusinessException;
import com.jerry.ticketing.exception.ConcertErrorCode;
import com.jerry.ticketing.repository.concert.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final ConcertSeatInitializer concertSeatInitializer;

    @Transactional
    public ConcertResponse createConcert(ConcertCreateRequest request){

        try{
            Concert concert = Concert.builder()
                    .title(request.getTitle())
                    .dateTime(request.getDateTime())
                    .venue(request.getVenue())
                    .price(request.getPrice())
                    .description(request.getDescription())
                    .maxTicketsPerUser(request.getMaxTicketsPerUser())
                    .build();

            Concert saveConcert = concertRepository.save(concert);

            // 좌석 & 섹션 초기화
            concertSeatInitializer.initializeSectionAndConcertSeats(saveConcert.getId());

            return ConcertResponse.from(saveConcert);

        }catch (Exception e){
            throw new BusinessException(ConcertErrorCode.CONCERT_SAVE_FAILED);
        }

    }
}
