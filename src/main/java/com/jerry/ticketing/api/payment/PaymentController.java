package com.jerry.ticketing.api.payment;


import com.jerry.ticketing.application.payment.PaymentService;
import com.jerry.ticketing.config.payment.TossPaymentConfig;
import com.jerry.ticketing.dto.request.PaymentRequest;
import com.jerry.ticketing.dto.request.TossPaymentConfirmRequest;
import com.jerry.ticketing.dto.response.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final TossPaymentConfig tossPaymentConfig;

    /**
     * 결제 페이지
     * */
    @GetMapping(value = "/")
    public String index(){
        return "widget/checkout";
    }

    /**
     * 결제 요청 생성
     */
    @PostMapping("/request")
    @ResponseBody
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.createPayment(request);

        response.setClientKey(tossPaymentConfig.getTestClientApiKey());
        response.setSuccessUrl(tossPaymentConfig.getSuccessUrl());
        response.setFailUrl(tossPaymentConfig.getFailUrl());

        return ResponseEntity.ok(response);
    }



    /**
     * 토스페이먼츠 결제 성공 콜백
     */
    @GetMapping("/toss/success")
    public String tossPaymentSuccessPage(){

        return "widget/success";
    }

    @PostMapping("/toss/success")
    @ResponseBody
    public ResponseEntity<PaymentResponse> tossPaymentSuccess(
            @RequestBody TossPaymentConfirmRequest request){

        PaymentResponse response = paymentService.confirmTossPayment(request.getPaymentKey(),request.getOrderId(),request.getAmount());
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
