package com.jerry.ticketing.seat.application.section.dto;


import com.jerry.ticketing.seat.domain.Section;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SectionDto {

    private Long sectionId;
    private Long concertId;
    private String zone;
    private int capacity;
    private int remainingSeats;

    public static SectionDto from(Section section) {
        return SectionDto.builder()
                .sectionId(section.getId())
                .concertId(section.getConcertId())
                .zone(section.getZone())
                .capacity(section.getCapacity())
                .remainingSeats(section.getRemainingConcertSeats())
                .build();
    }

}
