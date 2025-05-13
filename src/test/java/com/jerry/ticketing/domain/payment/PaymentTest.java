package com.jerry.ticketing.domain.payment;

import com.jerry.ticketing.Repository.concert.ConcertRepository;
import com.jerry.ticketing.Repository.member.MemberRepository;
import com.jerry.ticketing.Repository.payment.PaymentRepository;
import com.jerry.ticketing.Repository.reservation.ReservationRepository;
import com.jerry.ticketing.domain.TestFixture;
import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.member.Member;
import com.jerry.ticketing.domain.payment.enums.PaymentMethod;
import com.jerry.ticketing.domain.payment.enums.PaymentStatus;
import com.jerry.ticketing.domain.reservation.Reservation;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.PathMatcher;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PaymentTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private Reservation savedReservation;
    @Autowired
    private PathMatcher pathMatcher;
    @Autowired
    private PaymentRepository paymentRepository;


    @BeforeEach
    void setUp(){
        Member member = memberRepository.save(TestFixture.createMember());
        Concert concert = concertRepository.save(TestFixture.createConcert());
        Reservation reservation = TestFixture.createReservation(member,concert);

        this.savedReservation = reservationRepository.save(reservation);
    }

    @Test
    @DisplayName("결제 생성 및 저장 검증")
    void savePayment(){
        // Given
        Payment payment = TestFixture.createPayment(savedReservation);

        // When
        Payment savedPayment = paymentRepository.save(payment);

        // Then
        assertThat(savedPayment).isNotNull();
        assertThat(savedPayment.getAmount()).isEqualTo(3);
        assertThat(savedPayment.getIdempotent()).isEqualTo("TEST-IDEMPOTENT");
        assertThat(savedPayment.getPaymentMethod()).isEqualTo(PaymentMethod.KAKAOPAY);
        assertThat(savedPayment.getPaymentStatus()).isEqualTo(PaymentStatus.PENDING);
    }



}