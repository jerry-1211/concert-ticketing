package com.jerry.ticketing.seat.domain.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Getter
public enum SeatSectionType {
    VIP(1000, "A", "F", "a", "j", 1, 100, SeatType.VIP, 3),
    STANDARD(1500, "G", "L", "a", "o", 1, 100, SeatType.STANDARD, 2),
    ECONOMY(2000, "M", "Z", "a", "t", 1, 100, SeatType.ECONOMY, 1);

    private final int capacity;
    private final String startZone;
    private final String endZone;
    private final String startRow;
    private final String endRow;
    private final int startNumber;
    private final int endNumber;
    private final SeatType seatType;
    private final int premium;

    SeatSectionType(int capacity, String startZone, String endZone, String startRow, String endRow, int startNumber, int endNumber, SeatType seatType, int premium) {
        this.capacity = capacity;
        this.startZone = startZone;
        this.endZone = endZone;
        this.startRow = startRow;
        this.endRow = endRow;
        this.startNumber = startNumber;
        this.endNumber = endNumber;
        this.seatType = seatType;
        this.premium = premium;
    }


    public static List<String> createRowList(String startRow, String endRow) {
        return IntStream.rangeClosed(startRow.charAt(0), endRow.charAt(0))
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.toList());
    }


    private static boolean isZoneInSection(String zone, SeatSectionType seatSection) {
        String startZone = String.valueOf(seatSection.getStartZone());
        String endZone = String.valueOf(seatSection.getEndZone());
        return zone.compareTo(startZone) >= 0 && zone.compareTo(endZone) <= 0;
    }


    public static List<SeatSectionType> getSectionTypes() {
        return Arrays.asList(SeatSectionType.values());
    }


    public static List<String> getZones() {
        return IntStream.rangeClosed(VIP.getStartZone().charAt(0), ECONOMY.getEndZone().charAt(0))
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.toList());
    }


    public static List<String> getRows(String zone) {
        return getSectionTypes().stream()
                .filter(sectionType -> isZoneInSection(zone, sectionType))
                .findFirst()
                .map(sectionType -> createRowList(sectionType.getStartRow(), sectionType.getEndRow()))
                .orElse(Collections.emptyList());
    }


    public int concertSeatAmount(int price) {
        return this.premium * price;
    }


}
