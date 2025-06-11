package com.jerry.ticketing.payment.application.dto;

import com.jerry.ticketing.concert.application.dto.ConcertDto;
import com.jerry.ticketing.member.application.dto.MemberDto;
import com.jerry.ticketing.global.config.payment.TossPaymentConfig;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class CreatePaymentDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotNull(message = "예약 ID는 필수입니다.")
        private Long reservationId;

        private int totalPrice;

        private String orderName;

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
            this.secreteKey = tossPaymentConfig.getTestSecretApiKey();
            this.successUrl = tossPaymentConfig.getSuccessUrl();
            this.failUrl = tossPaymentConfig.getFailUrl();
        }


        public static CreatePaymentDto.Response from(PaymentDto payment, MemberDto member, ConcertDto concert) {
            return CreatePaymentDto.Response.builder()
                    .paymentId(payment.getPaymentId())
                    .orderId(payment.getOrderId())
                    .orderName(concert.getTitle() + "공연 티켓")
                    .customEmail(member.getEmail())
                    .customName(member.getName())
                    .status(payment.getPaymentStatus().name())
                    .build();
        }
//
//        public static CreatePaymentDto.Response from(ConfirmPaymentDto.Response response, Payment payment) {
//
//            CreatePaymentDto.Response createResponse = from(payment);
//
//            // TODO 추후 필요하면 response 정보 사용
//            // 에를 들어 paymentKey, OrderID 등등
//
//            return createResponse;
//        }
    }

}
