package com.jerry.ticketing.dto;

import com.jerry.ticketing.domain.payment.Payment;
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

        private String clientKey;
        private String successUrl;
        private String failUrl;


        public static CreatePayment.Response from(Payment payment){
            return CreatePayment.Response.builder()
                    .paymentId(payment.getId())
                    .orderId(payment.getIdempotencyKey())
                    .orderName(payment.getReservation().getConcert().getTitle() + "공연 티켓")
                    .customEmail(payment.getReservation().getMember().getEmail())
                    .customName(payment.getReservation().getMember().getName())
                    .status(payment.getPaymentStatus().name())
                    .build();
        }


        public static CreatePayment.Response from(Payment payment,ConfirmTossPayment.Response confirmTossPaymentResponse){
            CreatePayment.Response response = from(payment);

            // TODO 추후 필요하면 tossPaymentResponse 정보 사용
            // 에를 들어 paymentKey, OrderID 등등

            return response;
        }
    }

}
