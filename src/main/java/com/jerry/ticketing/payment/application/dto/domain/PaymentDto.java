package com.jerry.ticketing.payment.application.dto.domain;


import com.jerry.ticketing.payment.domain.Payment;
import com.jerry.ticketing.payment.domain.enums.PaymentMethod;
import com.jerry.ticketing.payment.domain.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class PaymentDto {

    private Long paymentId;
    private Long reservationId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private OffsetDateTime paymentDate;
    private String orderId;
    private String lastTransactionKey;
    private String orderName;
    private String method;
    private int totalAmount;
    private String paymentKey;


    public static PaymentDto from(Payment payment) {
        return PaymentDto.builder()
                .paymentId(payment.getId())
                .reservationId(payment.getReservationId())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .paymentDate(payment.getPaymentDate())
                .orderId(payment.getOrderId())
                .lastTransactionKey(payment.getLastTransactionKey())
                .orderName(payment.getOrderName())
                .method(payment.getMethod())
                .totalAmount(payment.getTotalAmount())
                .paymentKey(payment.getPaymentKey())
                .build();
    }
}
