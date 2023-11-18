package com.example.pdp_student_managment_system.service;

import com.example.pdp_student_managment_system.dto.sending.MessageDto;

public interface SendingService {
    void send(String to);
    void send(MessageDto messageDto);

}
