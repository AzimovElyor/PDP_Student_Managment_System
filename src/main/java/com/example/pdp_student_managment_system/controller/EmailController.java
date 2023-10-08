package com.example.pdp_student_managment_system.controller;

import com.example.pdp_student_managment_system.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailController {
    private final EmailService emailService;
    @PostMapping("/verification")
    public ResponseEntity<String > passwordVerification(
            @RequestParam String email,
            @RequestParam String password
    ){
        return ResponseEntity.status(200).
                body(emailService.validatePassword(email,password));
    }


}
