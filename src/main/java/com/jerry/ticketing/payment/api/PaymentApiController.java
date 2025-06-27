package com.jerry.ticketing.payment.api;

import com.jerry.ticketing.payment.application.PaymentCommandService;
import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.web.CreatePaymentDto;
import com.jerry.ticketing.payment.infrastructure.config.TossPaymentConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@Profile("!test")
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentApiController {

    private final PaymentCommandService paymentCommandService;
    private final TossPaymentConfig tossPaymentConfig;

    @PostMapping("/request")
    public ResponseEntity<CreatePaymentDto.Response> createPayment(@Valid @RequestBody CreatePaymentDto.Request request) {
        CreatePaymentDto.Response response = paymentCommandService.createPayment(request);
        response.setPaymentUrls(tossPaymentConfig);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/toss/confirm")
    public ResponseEntity<CreatePaymentDto.Response> tossPaymentSuccess(
            @RequestBody ConfirmPaymentDto.Request request) {

        CreatePaymentDto.Response response = paymentCommandService.confirmPayment(request);
        response.setPaymentUrls(tossPaymentConfig);
        return ResponseEntity.ok(response);
    }


    @GetMapping("toss/fail")
    public String failPayment(HttpServletRequest request, Model model) {
        model.addAttribute("code", request.getParameter("code"));
        model.addAttribute("message", request.getParameter("message"));
        return "payment/payment-fail";
    }

}
