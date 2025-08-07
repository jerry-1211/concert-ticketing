package com.jerry.ticketing.payment.domain;


import com.jerry.ticketing.payment.domain.enums.PaymentMethod;
import com.jerry.ticketing.payment.domain.enums.PaymentStatus;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    // 결제 id
    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 예약 id
    @Column(name = "reservation_id")
    private Long reservationId;

    // 결제 수단
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    // 결제 상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    // 결제 날짜
    @Column(nullable = false)
    private OffsetDateTime paymentDate;

    // 결제 멱등성
    @Column(nullable = false)
    private String orderId;

    // 마지막 트랜잭션키
    @Column
    private String lastTransactionKey;

    // 주문자명
    @Column
    private String orderName;

    // 결제 방법
    @Column
    private String method;

    // 결제 금액
    @Column
    private int totalAmount;

    // 결제 key
    @Column
    private String paymentKey;


    private Payment(Long reservationId, PaymentMethod paymentMethod,
                    PaymentStatus paymentStatus, OffsetDateTime paymentDate, String orderId) {
        this.reservationId = reservationId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.orderId = orderId;
    }

    public static Payment createTossPayment(Long reservationId, String orderId, OffsetDateTime dateTime) {
        return new Payment(reservationId, PaymentMethod.TOSS_PAY, PaymentStatus.PENDING, dateTime, orderId);
    }

    public void updateConfirm(String paymentKey) {
        paymentStatus = PaymentStatus.CONFIRMED;
        this.paymentKey = paymentKey;
    }

    public void complete(WebhookPaymentDto.Request.PaymentData data) {
        OffsetDateTime dateTime = OffsetDateTime.now();
        this.lastTransactionKey = data.getLastTransactionKey();
        this.orderName = data.getOrderName();
        this.method = data.getMethod();
        this.totalAmount = data.getTotalAmount();
        paymentDate = dateTime;
    }
}
