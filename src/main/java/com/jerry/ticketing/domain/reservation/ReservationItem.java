package com.jerry.ticketing.domain.reservation;


import com.jerry.ticketing.domain.seat.ConcertSeat;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "reservation_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationItem {

    // 예약 아이템 id
    @Id
    @Column(name = "reservation_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 예약 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;


    // 콘서트 좌석 id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_seat_id")
    private ConcertSeat concertSeat;


}
