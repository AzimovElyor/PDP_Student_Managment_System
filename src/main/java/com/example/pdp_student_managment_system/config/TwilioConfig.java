package com.example.pdp_student_managment_system.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TwilioConfig {
    @Value("${twilio.auth_token}")
    private String twilioToken;
    @Value("${twilio.account_sid}")
    private String accountSid;
    @Value("${twilio.phone}")
    private String phone;

}
