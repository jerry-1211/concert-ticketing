package com.jerry.ticketing.payment.application.dto.web;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

public class ConfirmPaymentDto {
    @Getter
    @NoArgsConstructor
    public static class Request {
        private String paymentKey;
        private String orderId;
        private String amount;

        public Request(String paymentKey, String orderId, String amount) {
            this.paymentKey = paymentKey;
            this.orderId = orderId;
            this.amount = amount;
        }
    }


    @Getter
    @Builder
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
