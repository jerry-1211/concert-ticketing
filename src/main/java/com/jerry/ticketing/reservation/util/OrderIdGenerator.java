package com.jerry.ticketing.reservation.util;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@NoArgsConstructor
@Component
public class OrderIdGenerator {
    private static final String PREFIX = "ORDER";
    private static final int RANDOM_LENGTH = 8;

    public String generate(Long reservationId) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String randomPart = UUID.randomUUID().toString().replace("-", "").substring(0, RANDOM_LENGTH);
        String hashedReservationId = String.valueOf(Math.abs(reservationId.hashCode()));

        return String.format("%s-%s-%s-%s", PREFIX, timestamp, hashedReservationId, randomPart);
    }

}
