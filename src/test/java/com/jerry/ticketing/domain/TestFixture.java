package com.jerry.ticketing.domain;

import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.member.Address;
import com.jerry.ticketing.domain.member.Member;
import com.jerry.ticketing.domain.payment.Payment;
import com.jerry.ticketing.domain.reservation.Reservation;
import com.jerry.ticketing.domain.reservation.enums.ReservationStatus;
import com.jerry.ticketing.domain.seat.ConcertSeat;
import com.jerry.ticketing.domain.seat.Seat;
import com.jerry.ticketing.domain.seat.Section;
import com.jerry.ticketing.domain.seat.enums.SeatType;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

public class TestFixture {

    /**
     * 더미 데이터
     */


    // 멤버 데이터 생성
    public static Member createMember() {
        return createMember("Jerry");
    }

    public static Member createMember(String name) {
        return Member.createMember(
                name, Address.of("경기도", "고양시"),
                "jerry@naver.com", "password","010-2304-4302");
    }


    // 콘서트 데이터 생성
    public static Concert createConcert() {
        return Concert.createConcert(
                "Test-Title", OffsetDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES)
                , "Test-Venue", 100_000, "Test-Description", 3);
    }


    // 구역 데이터 생성
    public static Section createSection(Concert concert) {
        return Section.initSection(concert, 'A', 100_000);
    }


    // 좌석 데이터 생성
    public static Seat createSeat() {
        return Seat.createSeat('A', 10, SeatType.STANDARD);
    }


    // 콘서트 좌석
    public static ConcertSeat createConcertSeat(Concert concert, Seat seat, Section section) {
        return ConcertSeat.creatConcertSeat(concert, seat, section, 1000);
    }


    // 예약
    public static Reservation createReservation(Member member, Concert concert){
        return Reservation.createReservation(member, concert, 1000,
                ReservationStatus.PENDING, OffsetDateTime.now(), OffsetDateTime.now(), 3);
    }


    // 결제
    public static Payment createPayment(Reservation reservation){
        return Payment.createTossPayment(reservation, "TEST-IDEMPOTENT");
    }

}
