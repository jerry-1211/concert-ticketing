package com.jerry.ticketing.application.seat.enums;

import com.jerry.ticketing.domain.seat.enums.SeatType;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Getter
public enum SectionType {
    VIP(1000, "A", "F", "a", "j", 1, 100, SeatType.VIP, 3),
    STANDARD(1500, "G","L","a", "o", 1, 100, SeatType.STANDARD,2),
    ECONOMY(2000, "M","Z","a", "t", 1, 100, SeatType.ECONOMY,1);

    private final int capacity;
    private final String startZone;
    private final String endZone;
    private final String startRow;
    private final String endRow;
    private final int startNumber;
    private final int endNumber;
    private final SeatType seatType;
    private final int premium;

    SectionType(int capacity, String startZone, String endZone, String startRow, String endRow, int startNumber, int endNumber, SeatType seatType, int premium) {
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



    public static int calculateSeats(SectionType sectionType){
        return ((int)(sectionType.getEndRow().charAt(0) - sectionType.getStartRow().charAt(0)) + 1 ) * sectionType.getEndNumber();
    }


    public static List<SectionType> getSectionTypes() {
        return Arrays.asList(SectionType.values());
    }
}
