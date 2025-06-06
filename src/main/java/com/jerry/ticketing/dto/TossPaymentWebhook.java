package com.jerry.ticketing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TossPaymentWebhook {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private String eventType;
        private String createdAt;
        private PaymentData data;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PaymentData{
            private String lastTransactionKey;
            private String paymentKey;
            private String orderId;
            private String orderName;
            private String status;
            private String method;
            private int totalAmount;
        }
    }
}

