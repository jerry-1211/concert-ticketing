package com.jerry.ticketing.domain.seat;


import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.reservation.ReservationItem;
import com.jerry.ticketing.domain.seat.enums.ConcertSeatStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "concert_seat")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConcertSeat {

    // 콘서트 좌석 id
    @Id
    @Column(name = "concert_seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 콘서트 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert;

    // 좌석 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    // 예약 아이템 id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_item_id")
    private ReservationItem reservation_item;

    // 콘서트 좌석 가격
    private int price;

    // 콘서트 좌석 상태
    @Enumerated(EnumType.STRING)
    private ConcertSeatStatus concertSeatStatus;


    public void updateStatus(ConcertSeatStatus concertSeatStatus){
        this.concertSeatStatus = concertSeatStatus;
    }

    public boolean isAvailable(){
        return this.concertSeatStatus == ConcertSeatStatus.AVAILABLE;
    }


     public void reserve(){
        if(!isAvailable()){
            throw new IllegalStateException("예약할 수 없는 좌석입니다.");
        }
         this.concertSeatStatus = ConcertSeatStatus.RESERVED;
     }

}
