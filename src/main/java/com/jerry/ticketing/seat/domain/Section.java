package com.jerry.ticketing.seat.domain;


import com.jerry.ticketing.concert.domain.Concert;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert;

    // 구역별 위치 (ex) A석, B석
    @Column(nullable = false)
    private String zone;

    // 구역별 수용 인원
    @Column(nullable = false)
    private int capacity;

    // 구역별 남은 좌석
    @Column(nullable = false)
    private int remainingSeats;

    private Section(Concert concert, String zone, int capacity) {
        this.concert = concert;
        this.zone = zone;
        this.capacity = capacity;
        this.remainingSeats = capacity;
    }

    public static Section initSection(Concert concert, String zone, int capacity) {
        return new Section(concert, zone, capacity);
    }

    public int decreaseRemainingSeats(){
        if(this.remainingSeats<=0){
            throw new IllegalStateException("남은 좌석이 없습니다.");
        }
        return --remainingSeats;
    }

    public boolean hasAvailableSeats(){
        return this.remainingSeats > 0;
    }
}
