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

            public PaymentData(String lastTransactionKey, String paymentKey, String orderId, String orderName, String status, String method, int totalAmount) {
                this.lastTransactionKey = lastTransactionKey;
                this.paymentKey = paymentKey;
                this.orderId = orderId;
                this.orderName = orderName;
                this.status = status;
                this.method = method;
                this.totalAmount = totalAmount;
            }
        }

        public Request(String eventType, String createdAt, PaymentData data) {
            this.eventType = eventType;
            this.createdAt = createdAt;
            this.data = data;
        }

    }
}

