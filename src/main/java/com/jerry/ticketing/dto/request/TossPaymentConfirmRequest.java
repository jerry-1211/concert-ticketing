package com.jerry.ticketing.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TossPaymentConfirmRequest {

    private String paymentKey;
    private String orderId;
    private String amount;


}
