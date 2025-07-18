package com.jerry.ticketing.payment.infrastructure.repository;

import com.jerry.ticketing.payment.domain.Payment;
import com.jerry.ticketing.payment.domain.enums.PaymentMethod;
import com.jerry.ticketing.payment.domain.enums.PaymentStatus;
import com.jerry.ticketing.payment.domain.port.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class PaymentRepositoryAdapterTest {

    @Autowired
    private PaymentJpaRepository jpaRepository;

    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepositoryAdapter(jpaRepository);
    }

    @Test
    @DisplayName("결제를 저장 할 수 있다.")
    void savePayment() {
        // given
        OffsetDateTime now = OffsetDateTime.now();
        Long reservationId = 1L;
        String orderId = "orderId123";
        Payment payment = Payment.createTossPayment(reservationId, orderId, now);

        // when
        Payment savedPayment = paymentRepository.save(payment);

        // then
        assertThat(savedPayment)
                .extracting("reservationId", "paymentMethod", "paymentStatus", "paymentDate", "orderId")
                .containsExactlyInAnyOrder(reservationId, PaymentMethod.TOSS_PAY, PaymentStatus.PENDING, now, orderId);
    }

    @Test
    @DisplayName("결제를 아이디로 찾을 수 있다.")
    void findPaymentById() {
        // given
        OffsetDateTime now = OffsetDateTime.now();
        Long reservationId = 1L;
        String orderId = "orderId123";
        Payment payment = Payment.createTossPayment(reservationId, orderId, now);

        // when
        Payment savedPayment = paymentRepository.save(payment);
        Optional<Payment> foundPayment = paymentRepository.findById(savedPayment.getId());

        // then
        assertThat(foundPayment).isPresent().hasValueSatisfying(
                m -> {
                    assertThat(m.getId()).isEqualTo(savedPayment.getId());
                }
        );
    }

    @Test
    @DisplayName("결제를 주문 아이디로 찾을 수 있다.")
    void findPaymentByOrderId() {
        // given
        OffsetDateTime now = OffsetDateTime.now();
        Long reservationId = 1L;
        String orderId = "orderId123";
        Payment payment = Payment.createTossPayment(reservationId, orderId, now);

        // when
        Payment savedPayment = paymentRepository.save(payment);
        Optional<Payment> foundPayment = paymentRepository.findByOrderId(savedPayment.getOrderId());

        // then
        assertThat(foundPayment).isPresent().hasValueSatisfying(
                m -> {
                    assertThat(m.getOrderId()).isEqualTo(savedPayment.getOrderId());
                }
        );
    }


}