package com.example.indie91.config;

import com.phonepe.sdk.pg.Env;
import com.phonepe.sdk.pg.payments.v1.PhonePePaymentClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PhonePeConfig {

    @Bean
    public PhonePePaymentClient phonePePaymentClient() {
        String merchantId = "PGTESTPAYUAT86";
        String saltKey = "96434309-7796-489d-8924-ab56988a6076";
        Integer saltIndex = 1;
        Env env = Env.UAT;
        boolean shouldPublishEvents = true;

        return new PhonePePaymentClient(merchantId, saltKey, saltIndex, env, shouldPublishEvents);
    }
}

