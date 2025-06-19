package com.jerry.ticketing.domain.payment;

import com.jerry.ticketing.payment.domain.Payment;
import com.jerry.ticketing.concert.infrastructure.repository.ConcertRepository;
import com.jerry.ticketing.member.infrastructure.repository.MemberRepository;
import com.jerry.ticketing.payment.infrastructure.repository.PaymentRepository;
import com.jerry.ticketing.reservation.infrastructure.repository.ReservationRepository;
import com.jerry.ticketing.domain.TestFixture;
import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.payment.domain.enums.PaymentMethod;
import com.jerry.ticketing.payment.domain.enums.PaymentStatus;
import com.jerry.ticketing.reservation.domain.Reservation;
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
    void setUp() {
//        Member member = memberRepository.save(TestFixture.createMember());
//        Concert concert = concertRepository.save(TestFixture.createConcert());
//        Reservation reservation = TestFixture.createReservation(member, concert);

//        this.savedReservation = reservationRepository.save(reservation);
    }

    @Test
    @DisplayName("결제 생성 및 저장 검증")
    void savePayment() {
        // Given
        Payment payment = TestFixture.createPayment(1L);

        // When
        Payment savedPayment = paymentRepository.save(payment);

        // Then
        assertThat(savedPayment).isNotNull();
        assertThat(savedPayment.getOrderId()).isEqualTo("TEST-IDEMPOTENT");
        assertThat(savedPayment.getPaymentMethod()).isEqualTo(PaymentMethod.TOSS_PAY);
        assertThat(savedPayment.getPaymentStatus()).isEqualTo(PaymentStatus.PENDING);
    }


}