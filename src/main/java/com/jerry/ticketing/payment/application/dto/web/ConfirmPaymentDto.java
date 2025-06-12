package com.jerry.ticketing.payment.application.dto.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

public class ConfirmPaymentDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private String paymentKey;
        private String orderId;
        private String amount;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

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
        private String discount;

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

}
