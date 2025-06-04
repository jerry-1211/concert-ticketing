package com.jerry.ticketing.dto;

import com.jerry.ticketing.domain.payment.Payment;
import com.jerry.ticketing.global.config.payment.TossPaymentConfig;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class CreatePayment {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotNull(message = "예약 ID는 필수입니다.")
        private Long reservationId;

    }


    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private Long paymentId;
        private String orderId;
        private String orderName;
        private String customEmail;
        private String customName;
        private String status;

        private String secreteKey;
        private String successUrl;
        private String failUrl;


        public void setPaymentUrls(TossPaymentConfig tossPaymentConfig) {
            this.secreteKey = tossPaymentConfig.getTestSecreteApiKey();
            this.successUrl = tossPaymentConfig.getSuccessUrl();
            this.failUrl = tossPaymentConfig.getFailUrl();
        }


        public static CreatePayment.Response from(Payment payment){
            return CreatePayment.Response.builder()
                    .paymentId(payment.getId())
                    .orderId(payment.getOrderId())
                    .orderName(payment.getReservation().getConcert().getTitle() + "공연 티켓")
                    .customEmail(payment.getReservation().getMember().getEmail())
                    .customName(payment.getReservation().getMember().getName())
                    .status(payment.getPaymentStatus().name())
                    .build();
        }

        public static CreatePayment.Response from(ConfirmTossPayment.Response response, Payment payment) {

            CreatePayment.Response createResponse = from(payment);

            // TODO 추후 필요하면 response 정보 사용
            // 에를 들어 paymentKey, OrderID 등등

            return createResponse;
        }
    }

}
