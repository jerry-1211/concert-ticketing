package com.jerry.ticketing.payment.application.dto.web;

import com.jerry.ticketing.concert.application.dto.domain.ConcertDto;
import com.jerry.ticketing.member.application.dto.domain.MemberDto;
import com.jerry.ticketing.payment.infrastructure.config.TossPaymentConfig;
import com.jerry.ticketing.payment.application.dto.domain.PaymentDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class CreatePaymentDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotNull(message = "예약 ID는 필수입니다.")
        private Long reservationId;
        private int totalAmount;
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
    }

}
