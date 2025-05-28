package com.jerry.ticketing.dto.response;

import com.jerry.ticketing.domain.payment.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PaymentResponse {
    private Long paymentId;
    private String orderId;
    private Integer amount;
    private String orderName;
    private String customEmail;
    private String customName;
    private String status;

    private String clientKey;
    private String successUrl;
    private String failUrl;


    public static PaymentResponse from(Payment payment){
        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .orderId(payment.getIdempotencyKey())
                .amount(payment.getAmount())
                .orderName(payment.getReservation().getConcert().getTitle() + "공연 티켓")
                .customEmail(payment.getReservation().getMember().getEmail())
                .customName(payment.getReservation().getMember().getName())
                .status(payment.getPaymentStatus().name())
                .build();
    }


    public static PaymentResponse from(Payment payment, TossPaymentResponse tossPaymentResponse){
        PaymentResponse response = from(payment);

        // TODO 추후 필요하면 tossPaymentResponse 정보 사용
        // 에를 들어 paymentKey, OrderID 등등

        return response;
    }
}
