package com.jerry.ticketing.payment.application.dto.domain;


import com.jerry.ticketing.payment.domain.Payment;
import com.jerry.ticketing.payment.domain.enums.PaymentMethod;
import com.jerry.ticketing.payment.domain.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
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
        return new PaymentDto(
                payment.getId(),
                payment.getReservationId(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getPaymentDate(),
                payment.getOrderId(),
                payment.getLastTransactionKey(),
                payment.getOrderName(),
                payment.getMethod(),
                payment.getTotalAmount(),
                payment.getPaymentKey()
        );
    }
}
