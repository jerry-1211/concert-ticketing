package com.jerry.ticketing.seat.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConcertSeatIdExtractor {
    public static List<Long> extractFromOrderName(String orderName) {
        String seatIdsString = orderName.split(" - ")[0];

        return Arrays.stream(seatIdsString.split(","))
                .map(String::trim)
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }
}
