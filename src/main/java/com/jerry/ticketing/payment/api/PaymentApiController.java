package com.jerry.ticketing.payment.api;

import com.jerry.ticketing.payment.application.PaymentService;
import com.jerry.ticketing.payment.application.dto.ConfirmTossPayment;
import com.jerry.ticketing.payment.application.dto.CreatePayment;
import com.jerry.ticketing.global.config.payment.TossPaymentConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentApiController {

    private final PaymentService paymentService;
    private final TossPaymentConfig tossPaymentConfig;

    /**
     * 결제 요청 생성
     */
    @PostMapping("/request")
    public ResponseEntity<CreatePayment.Response> createPayment(@Valid @RequestBody CreatePayment.Request request) {
        CreatePayment.Response response = paymentService.createPayment(request);
        response.setPaymentUrls(tossPaymentConfig);
        return ResponseEntity.ok(response);
    }



    /**
     * Toss 결제 승인
     */
    @PostMapping("/toss/confirm")
    public ResponseEntity<CreatePayment.Response> tossPaymentSuccess(
            @RequestBody ConfirmTossPayment.Request request){

        CreatePayment.Response response = paymentService.confirmPayment(request);
        response.setPaymentUrls(tossPaymentConfig);
        return ResponseEntity.ok(response);
    }


    /**
     * 토스페이먼츠 결제 실패 콜백
     */
    @GetMapping("toss/fail")
    public String failPayment(HttpServletRequest request, Model model) {
        model.addAttribute("code", request.getParameter("code"));
        model.addAttribute("message", request.getParameter("message"));
        return "fail";
    }

}
