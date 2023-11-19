package com.example.pdp_student_managment_system.service;

import com.example.pdp_student_managment_system.dto.sending.MessageDto;
import com.example.pdp_student_managment_system.entity.MailVerification;
import com.example.pdp_student_managment_system.enums.MessageType;
import com.example.pdp_student_managment_system.exception.EmailSendingMessageException;
import com.example.pdp_student_managment_system.exception.VerificationTimeOutException;
import com.example.pdp_student_managment_system.repository.MaileRepository;
import com.example.pdp_student_managment_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class EmailService implements SendingService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MaileRepository maileRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void send(MessageDto messageDto) {
       try {
           SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

           simpleMailMessage.setTo(messageDto.getTo());
           simpleMailMessage.setSubject("NEW MESSAGE TO PDP_ERP");
           simpleMailMessage.setText(messageDto.getMessage());
       }catch (Exception e){
           throw new EmailSendingMessageException("Exception when sending message to email");
       }
    }

    @Override
    public void send(String to){
       try {
           SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
           simpleMailMessage.setTo(to);
           String generatePassword = generatePassword();
           simpleMailMessage.setText("Your verification passoword : " + generatePassword);
           simpleMailMessage.setSubject("TEAM4_STUDENT_MANAGEMENT_SYSTEM");
            saveMail(to,generatePassword);
           javaMailSender.send(simpleMailMessage);

       }catch (Exception e){
           e.printStackTrace();
           throw new EmailSendingMessageException("Exception when sending message to email");
       }
    }
    @Transactional
    public String validatePassword(String email, String password){
       try {
           MailVerification mail = maileRepository.findByEmailAndMessageType(email,MessageType.VERIFICATION).
                   orElseThrow(() -> new RuntimeException("Email not found"));
           LocalDateTime now = LocalDateTime.now();
           long seconds = Duration.between(mail.getCreatedDate(), now).getSeconds();
           if(seconds > 60){
               throw new VerificationTimeOutException("Verification");
           }
           if (!mail.getMessage().equals(password)) {
               throw new RuntimeException("Incorrect password");
           }
           userRepository.updateVerification(email);
           return "Successfully verification email %s".formatted(email);
       }catch (Exception e){
           e.printStackTrace();
           throw new EmailSendingMessageException("Email sending error");
       }
    }
    private String generatePassword(){
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 6; i++){
            password.append(random.nextInt(0,10));
        }
        return password.toString();
    }
    private void    saveMail(String email, String generatePassword){
        Optional<MailVerification> byEmail = maileRepository.findByEmailAndMessageType(email,MessageType.VERIFICATION);
        if(byEmail.isEmpty()){
            maileRepository.save(new MailVerification(email,generatePassword, MessageType.VERIFICATION));
        }else {
            MailVerification mailEntity = byEmail.get();
            mailEntity.setMessage(generatePassword);
            maileRepository.save(mailEntity);
        }

    }

}
