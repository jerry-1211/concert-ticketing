package com.jerry.ticketing.global.util;

import java.util.UUID;

public class PaymentOrderIdGenerator {
    public static String generate(Long reservationId) {
        return "RES-" + reservationId + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

}
