package com.jerry.ticketing.concert.infrastructure.mapper;

import com.jerry.ticketing.concert.application.dto.web.CreateConcertDto;
import com.jerry.ticketing.concert.domain.Concert;
import org.springframework.stereotype.Component;

@Component
public class ConcertMapper {

    public Concert from(CreateConcertDto.Request request) {
        return Concert.of(
                request.getTitle(),
                request.getDateTime(),
                request.getVenue(),
                request.getPrice(),
                request.getDescription(),
                request.getMaxTicketsPerUser()
        );
    }

}
