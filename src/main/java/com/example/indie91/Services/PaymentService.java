package com.example.indie91.Services;

import com.example.indie91.Models.Payment;
import com.phonepe.sdk.pg.common.http.PhonePeResponse;
import com.phonepe.sdk.pg.payments.v1.PhonePePaymentClient;
import com.phonepe.sdk.pg.payments.v1.models.request.PgPayRequest;
import com.phonepe.sdk.pg.payments.v1.models.response.PayPageInstrumentResponse;
import com.phonepe.sdk.pg.payments.v1.models.response.PgPayResponse;
import com.phonepe.sdk.pg.payments.v1.models.response.PgTransactionStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PhonePePaymentClient phonepeClient;

    public URL initiatePayment(Payment payment) throws MalformedURLException {

        try{
            payment.setTransactionId(UUID.randomUUID());

            PgPayRequest pgPayRequest = PgPayRequest.PayPagePayRequestBuilder()
                    .amount(payment.getAmount())
                    .merchantUserId(payment.getUserName())
                    .merchantId("PGTESTPAYUAT86")
                    .merchantTransactionId(payment.getTransactionId().toString())
                    .callbackUrl("https://indie91.onrender.com/api/payment/callback-url")
                    .redirectUrl("https://indie91.onrender.com/api/payment/callback-url")
                    .redirectMode("POST")
                    .build();

            PhonePeResponse<PgPayResponse> payResponse = this.phonepeClient.pay(pgPayRequest);
            PayPageInstrumentResponse payPageInstrumentResponse = (PayPageInstrumentResponse) payResponse.getData().getInstrumentResponse();
            return new URL(payPageInstrumentResponse.getRedirectInfo().getUrl());
        } catch (Exception e){
            System.out.println("Payment Failed!!!" + e);
            return null;
        }
    }

    public String getStatus(final String code, final String transactionId, final String merchantId) {
        if (code.equals("PAYMENT_SUCCESS")) {
            return "Payment Success!";
        }else{
            return "Payment Failed!";
        }
    }
}
