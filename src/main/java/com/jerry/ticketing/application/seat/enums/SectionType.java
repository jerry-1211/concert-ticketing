package com.jerry.ticketing.application.seat.enums;

import com.jerry.ticketing.domain.seat.enums.SeatType;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum SectionType {
    VIP(1000, 'A', 'F', 'a', 'j', 1, 100, SeatType.VIP, 3),
    STANDARD(1500, 'G','L','a', 'o', 1, 100, SeatType.STANDARD,2),
    ECONOMY(2000, 'M','Z','a', 't', 1, 100, SeatType.ECONOMY,1);

    private final int capacity;
    private final char startZone;
    private final char endZone;
    private final char startRow;
    private final char endRow;
    private final int startNumber;
    private final int endNumber;
    private final SeatType seatType;
    private final int premium;

    SectionType(int capacity, char startZone, char endZone, char startRow, char endRow, int startNumber, int endNumber, SeatType seatType, int premium) {
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
        return ((int)(sectionType.getEndRow() - sectionType.getStartRow()) + 1 ) * sectionType.getEndNumber();
    }


    public static List<SectionType> getSectionTypes() {
        return Arrays.asList(SectionType.values());
    }

    public int seatPrice(int price) {
        return this.premium * price;
    }
}
