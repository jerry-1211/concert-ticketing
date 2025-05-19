package com.jerry.ticketing.domain;

import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.member.Address;
import com.jerry.ticketing.domain.member.Member;
import com.jerry.ticketing.domain.payment.Payment;
import com.jerry.ticketing.domain.payment.enums.PaymentMethod;
import com.jerry.ticketing.domain.payment.enums.PaymentStatus;
import com.jerry.ticketing.domain.reservation.Reservation;
import com.jerry.ticketing.domain.reservation.enums.ReservationStatus;
import com.jerry.ticketing.domain.seat.ConcertSeat;
import com.jerry.ticketing.domain.seat.Seat;
import com.jerry.ticketing.domain.seat.Section;
import com.jerry.ticketing.domain.seat.enums.ConcertSeatStatus;
import com.jerry.ticketing.domain.seat.enums.SeatType;

import java.time.LocalDate;

public class TestFixture {

    /**
     * 더미 데이터
     */


    // 멤버 데이터 생성
    public static Member createMember() {
        return createMember("Jerry");
    }

    public static Member createMember(String name) {
        return Member.builder()
                .name(name) // name 필수값 추가
                .address(Address.of("경기도","고양시"))
                .email("jerry@naver.com")
                .phoneNumber("010-2304-4302")
                .password("password") // pw 필수값 추가
                .build();
    }


    // 콘서트 데이터 생성
    public static Concert createConcert() {
        return Concert.builder()
                .title("Cold Play")
                .date(LocalDate.now())
                .venue("일산 고양시 대화동")
                .price(100_000)
                .description("Cold Play의 2번째 내한 공연")
                .maxTicketsPerUser(2)
                .build();
    }


    // 구연 데이터 생성
    public static Section createSection(Concert concert) {
        return Section.builder()
                .concert(concert)
                .zone("A")
                .capacity(100_000)
                .remainingSeats(100)
                .build();
    }


    // 좌석 데이터 생성
    public static Seat createSeat(Section section) {
        return Seat.builder()
                .section(section)
                .seatRow("A")
                .number(10)
                .seatType(SeatType.STANDARD)
                .build();
    }


    // 콘서트 좌석
    public static ConcertSeat createConcertSeat(Concert concert, Seat seat) {
        return ConcertSeat.builder()
                .concert(concert)
                .seat(seat)
                .price(100_000)
                .status(ConcertSeatStatus.BLOCKED)
                .build();
    }


    // 예약
    public static Reservation createReservation(Member member, Concert concert){
        return Reservation.builder()
                .member(member)
                .concert(concert)
                .totalPrice(1000)
                .reservationStatus(ReservationStatus.PENDING)
                .createdAt(LocalDate.now())
                .expiresAt(LocalDate.now())
                .build();
    }


    // 결제
    public static Payment createPayment(Reservation reservation){
        return Payment.builder()
                .reservation(reservation)
                .paymentMethod(PaymentMethod.KAKAOPAY)
                .paymentStatus(PaymentStatus.PENDING)
                .paymentDate(LocalDate.now())
                .idempotencyKey("TEST-IDEMPOTENT")
                .amount(3)
                .build();
    }

}
