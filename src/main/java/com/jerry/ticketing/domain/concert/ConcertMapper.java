package com.jerry.ticketing.domain.concert;

import com.jerry.ticketing.dto.ConcertCreate;
import com.jerry.ticketing.dto.request.ConcertCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class ConcertMapper {

    public Concert buildConcert(ConcertCreate.Request request) {
        return Concert.createConcert(
                request.getTitle(),
                request.getDateTime(),
                request.getVenue(),
                request.getPrice(),
                request.getDescription(),
                request.getMaxTicketsPerUser()
        );
    }
}
