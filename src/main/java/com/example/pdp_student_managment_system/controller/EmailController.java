package com.example.pdp_student_managment_system.controller;

import com.example.pdp_student_managment_system.dto.sending.MessageDto;
import com.example.pdp_student_managment_system.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailController {
    private final EmailService emailService;
    @PostMapping("/send-message")
    public void sendMessage(
            @RequestBody MessageDto messageDto
            ){
         emailService.send(messageDto);
    }
    @PostMapping("/repeat-verification-code")
    public void retryEmailVerification(@RequestParam String email){
         emailService.send(email);
    }
    @PostMapping("/verification")
    @ResponseStatus(HttpStatus.OK)
    public String verificationUser(@RequestParam String email,@RequestParam String password){
      return  emailService.validatePassword(email,password);
    }



}
