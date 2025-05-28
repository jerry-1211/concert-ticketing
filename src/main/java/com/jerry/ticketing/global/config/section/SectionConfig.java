package com.jerry.ticketing.global.config.section;

import com.jerry.ticketing.domain.seat.enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 구역 설정 정보 클래스
 * */
@AllArgsConstructor
@Getter
public class SectionConfig {
    private int capacity;
    private char startZone;
    private char endZone;
    private char startRow;
    private char endRow;
    private int startNumber;
    private int endNumber;
    private SeatType seatType;
    private int premium;

    public static SectionConfig vipSection(){
        return new SectionConfig(1000,'A','F','a', 'j', 1, 100, SeatType.VIP,3);
    }

    public static SectionConfig standardSection(){
        return new SectionConfig(1500, 'G','L','a', 'o', 1, 100, SeatType.STANDARD,2);
    }

    public static SectionConfig economySection(){
        return new SectionConfig(2000, 'M','Z','a', 't', 1, 100, SeatType.ECONOMY,1);
    }

    public static int calculateSeats(SectionConfig sectionConfig){
        return ((int)(sectionConfig.getEndRow() - sectionConfig.getStartRow()) + 1 ) * sectionConfig.getEndNumber();
    }

    public static int currentSeats(SectionConfig sectionConfig, char row, int number){
        return sectionConfig.getEndNumber() * (int) (row - 'a') + number;
    }


}
