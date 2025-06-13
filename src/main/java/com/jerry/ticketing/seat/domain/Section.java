package com.jerry.ticketing.seat.domain;


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
    private int remainingSeats;

    private Section(Long concertId, String zone, int capacity) {
        this.concertId = concertId;
        this.zone = zone;
        this.capacity = capacity;
        this.remainingSeats = capacity;
    }

    public static Section initSection(Long concertId, String zone, int capacity) {
        return new Section(concertId, zone, capacity);
    }

    // Todo: 남은 좌석 표현해주는 로직
    public int decreaseRemainingSeats() {
        if (this.remainingSeats <= 0) {
            throw new IllegalStateException("남은 좌석이 없습니다.");
        }
        return --remainingSeats;
    }

    public boolean hasAvailableSeats() {
        return this.remainingSeats > 0;
    }
}
