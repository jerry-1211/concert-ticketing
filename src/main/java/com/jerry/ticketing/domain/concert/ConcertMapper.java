package com.jerry.ticketing.domain.concert;

import com.jerry.ticketing.dto.CreateConcert;
import org.springframework.stereotype.Component;

@Component
public class ConcertMapper {

    public Concert buildConcert(CreateConcert.Request request) {
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
