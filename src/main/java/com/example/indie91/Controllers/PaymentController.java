package com.example.indie91.Controllers;

import com.example.indie91.Models.Payment;
import com.example.indie91.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    private static final long DEFAULT_AMOUNT = 100;

    @GetMapping("/initiate")
    public RedirectView initiatePayment(RedirectAttributes redirectAttributes) throws MalformedURLException {
        Payment payment = new Payment();
        payment.setUserName("test");
        payment.setAmount(DEFAULT_AMOUNT);
        System.out.println("Payment Started!!!");

        return new RedirectView(paymentService.initiatePayment(payment).toString());
//        return paymentService.initiatePayment(payment).toString();

    }

    @PostMapping("/callback-url")
    public String paymentStatus(@RequestParam(required = false) String code, @RequestParam(required = false) String transactionId, @RequestParam(required = false) String merchantId){
        System.out.println("code" + code);
        System.out.println("transactionId" + transactionId);
        System.out.println("merchantId" + merchantId);

        return paymentService.getStatus(code, transactionId, merchantId);
    }
}
