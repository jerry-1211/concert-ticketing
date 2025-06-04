package com.jerry.ticketing.api.payment;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("payment")
@RequiredArgsConstructor
public class PaymentViewController {

    /**
     * 결제 페이지
     * */
    @GetMapping(value = "/checkout")
    public String checkoutPage(){
        return "widget/checkout";
    }


    /**
     * 토스페이먼츠 결제 성공 페이지
     */
    @GetMapping("/success")
    public String successPage(){
        return "widget/success";
    }


    /**
     * 토스페이먼츠 결제 실패 페이지
     */
    @GetMapping("/fail")
    public String failPayment(HttpServletRequest request, Model model) {
        model.addAttribute("code", request.getParameter("code"));
        model.addAttribute("message", request.getParameter("message"));
        return "widget/fail";
    }
}
