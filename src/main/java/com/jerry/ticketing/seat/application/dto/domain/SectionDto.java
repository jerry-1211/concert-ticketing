package com.jerry.ticketing.seat.application.dto.domain;


import com.jerry.ticketing.seat.domain.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SectionDto {

    private Long sectionId;
    private Long concertId;
    private String zone;
    private int capacity;
    private int remainingSeats;

    public static SectionDto from(Section section) {
        return new SectionDto(
                section.getId(),
                section.getConcertId(),
                section.getZone(),
                section.getCapacity(),
                section.getRemainingSeats()
        );
    }

}
