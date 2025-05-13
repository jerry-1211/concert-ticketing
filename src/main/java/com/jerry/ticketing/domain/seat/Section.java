package com.jerry.ticketing.domain.seat;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "section")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Section {

    // 구역 id
    @Id
    @Column(name = "section_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 구역별 위치 (ex) A석, B석
    private String zone;

    // 구역별 수용 인원
    private int capacity;

    // 구역별 남은 좌석
    private int remainingSeats;

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
