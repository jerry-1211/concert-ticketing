package com.jerry.ticketing.seat.domain;


import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.SectionErrorCode;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Section {

    // 구역 id
    @Id
    @Column(name = "section_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 콘서트 id
    @Column(name = "concert_id")
    private Long concertId;

    // 구역별 위치 (ex) A석, B석
    @Column(nullable = false)
    private String zone;

    // 구역별 수용 인원
    @Column(nullable = false)
    private int capacity;

    // 구역별 남은 좌석
    @Column(nullable = false)
    private int remainingConcertSeats;


    private Section(Long concertId, String zone, int capacity) {
        this.concertId = concertId;
        this.zone = zone;
        this.capacity = capacity;
        this.remainingConcertSeats = capacity;
    }


    public static Section of(Long concertId, String zone, int capacity) {
        return new Section(concertId, zone, capacity);
    }


    public void decreaseRemainingSeats() {
        if (this.remainingConcertSeats <= 0) {
            throw new BusinessException(SectionErrorCode.SECTION_SOLD_OUT);
        }
        --remainingConcertSeats;
    }

    public boolean hasAvailableSeats() {
        return this.remainingConcertSeats > 0;
    }
}
