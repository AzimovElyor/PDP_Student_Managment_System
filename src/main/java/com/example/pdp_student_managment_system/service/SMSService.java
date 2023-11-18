package com.example.pdp_student_managment_system.service;

import com.example.pdp_student_managment_system.config.TwilioConfig;
import com.example.pdp_student_managment_system.dto.sending.MessageDto;
import com.example.pdp_student_managment_system.twilio.TwilioInitializer;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.rest.chat.v1.service.channel.MemberCreator;

import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class SMSService implements SendingService{
    private final TwilioConfig twilioConfig;
    private static final Logger LOGGER = LoggerFactory.getLogger(SMSService.class);

    @Override
    public void send(MessageDto messageDto) {
        PhoneNumber to = new PhoneNumber(messageDto.getTo());
        PhoneNumber from = new PhoneNumber(twilioConfig.getPhone());
        MessageCreator creator = Message.creator(to, from, messageDto.getMessage());
        creator.create();
        LOGGER.info("SMS send to %s".formatted(messageDto.getTo()));

    }

    @Override
    public void send(String to) {

    }
}
