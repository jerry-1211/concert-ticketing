package com.jerry.ticketing.payment.util;

import java.util.UUID;

public class PaymentOrderIdGenerator {
    private static final String PREFIX = "ORDER";
    private static final int RANDOM_LENGTH = 8;

    private PaymentOrderIdGenerator() {
    }

    public static String generate(Long reservationId) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String randomPart = UUID.randomUUID().toString().replace("-", "").substring(0, RANDOM_LENGTH);
        String hashedReservationId = String.valueOf(Math.abs(reservationId.hashCode()));

        return String.format("%s-%s-%s-%s", PREFIX, timestamp, hashedReservationId, randomPart);
    }

}
