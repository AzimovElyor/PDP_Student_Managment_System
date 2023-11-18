package com.example.pdp_student_managment_system.controller;

import com.example.pdp_student_managment_system.dto.sending.MessageDto;
import com.example.pdp_student_managment_system.service.SMSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
public class SMSController {
    private final SMSService service;
    @PostMapping("/send")
    public void sendSMS(@RequestBody MessageDto messageDto){
        service.send(messageDto);
    }
}
