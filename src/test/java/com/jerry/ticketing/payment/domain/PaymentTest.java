package com.jerry.ticketing.payment.domain;

import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import com.jerry.ticketing.payment.domain.enums.PaymentMethod;
import com.jerry.ticketing.payment.domain.enums.PaymentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentTest {

    @Test
    @DisplayName("초기에 토스결제가 생성되면, PENDING 상태이다.")
    void tossPaymentInitialStatusShouldBePending() {
        // given
        Long reservationId = 1L;
        String orderId = "orderId123";
        OffsetDateTime now = OffsetDateTime.now();
        Payment payment = Payment.createTossPayment(reservationId, orderId, now);

        // when // then
        assertThat(payment)
                .extracting("reservationId", "paymentMethod", "paymentStatus", "paymentDate", "orderId")
                .containsExactlyInAnyOrder(reservationId, PaymentMethod.TOSS_PAY, PaymentStatus.PENDING, now, orderId);
    }

    @Test
    @DisplayName("결제 확정이 업데이트 되면, CONFIRMED 상태이다.")
    void paymentConfirmationShouldUpdateToConfirmed() {
        // given
        OffsetDateTime now = OffsetDateTime.now();
        Payment payment = Payment.createTossPayment(1L, "orderId123", now);
        String paymentId = "paymentKey123";

        // when
        payment.updateConfirm(paymentId);

        // then
        assertThat(payment)
                .extracting("paymentStatus", "paymentKey")
                .containsExactlyInAnyOrder(PaymentStatus.CONFIRMED, paymentId);
    }

    @Test
    @DisplayName("결제 웹훅 요청으로부터 받은 데이터를 업데이트 한다.")
    void updatePaymentDataFromWebhookRequest() {
        // given
        OffsetDateTime now = OffsetDateTime.now();
        Payment payment = Payment.createTossPayment(1L, "orderId123", now);

        String lastTransactionKey = "lastTransactionKey123";
        String orderName = "orderName123";
        String method = "method123";
        int totalAmount = 1_000;
        OffsetDateTime paymentDate = now.plusHours(1);

        WebhookPaymentDto.Request.PaymentData paymentData = WebhookPaymentDto.Request.PaymentData.of(lastTransactionKey, orderName, method, totalAmount);

        // when
        payment.complete(paymentData, paymentDate);

        // then
        assertThat(payment)
                .extracting("lastTransactionKey", "orderName", "method", "totalAmount", "paymentDate")
                .containsExactlyInAnyOrder(lastTransactionKey, orderName, method, totalAmount, paymentDate);
    }

}