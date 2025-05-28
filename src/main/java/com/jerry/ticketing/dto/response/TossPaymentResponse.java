package com.jerry.ticketing.dto.response;

import lombok.*;

import java.time.OffsetDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TossPaymentResponse {

    private String paymentKey;
    private String orderId;
    private String orderName;
    private int totalAmount;
    private String status;
    private OffsetDateTime requestedAt;
    private OffsetDateTime approvedAt;
    private String method;
    private String currency;
    private String mId;
    private String disdcount;

    // 결제 수단별 종류
    private Object card;
    private Object cashReceipt;
    private Object transfer;
    private Object virtualAccount;
    private Object giftCertificate;
    private Object mobilePhone;

    // 취소 정보
    private Object[] cancels;

    // 실패 정보
    private Object failure;


}
