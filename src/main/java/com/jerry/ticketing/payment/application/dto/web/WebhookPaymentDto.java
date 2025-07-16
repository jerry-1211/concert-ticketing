package com.jerry.ticketing.payment.application.dto.web;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class WebhookPaymentDto {

    @Getter
    @NoArgsConstructor
    public static class Request {
        private String eventType;
        private String createdAt;
        private PaymentData data;

        @Getter
        @NoArgsConstructor
        public static class PaymentData {
            private String lastTransactionKey;
            private String paymentKey;
            private String orderId;
            private String orderName;
            private String status;
            private String method;
            private int totalAmount;

            public static PaymentData of(String lastTransactionKey, String orderName,
                                         String method, int totalAmount) {
                PaymentData paymentData = new PaymentData();
                paymentData.lastTransactionKey = lastTransactionKey;
                paymentData.orderName = orderName;
                paymentData.method = method;
                paymentData.totalAmount = totalAmount;
                return paymentData;
            }

        }
    }
}

