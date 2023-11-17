package com.example.pdp_student_managment_system.twilio;

import com.example.pdp_student_managment_system.config.TwilioConfig;
import com.twilio.Twilio;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;



@Configuration
@Slf4j
public class TwilioInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioInitializer.class);
    public TwilioInitializer(TwilioConfig twilioConfig){
        Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getTwilioToken());
        LOGGER.info("Twilio initializer");
    }
}
