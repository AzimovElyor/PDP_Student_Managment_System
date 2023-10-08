package com.example.pdp_student_managment_system.service;

import com.example.pdp_student_managment_system.entity.MailEntity;
import com.example.pdp_student_managment_system.entity.UserEntity;
import com.example.pdp_student_managment_system.exception.EmailSendingMessageException;
import com.example.pdp_student_managment_system.repository.MaileRepository;
import com.example.pdp_student_managment_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MaileRepository maileRepository;
    @Autowired
    private UserRepository userRepository;
    public void sendPasswordEmail(String to){
       try {
           SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
           simpleMailMessage.setTo(to);
           simpleMailMessage.setText("Your verification passoword : " + generatePassword());
           simpleMailMessage.setSubject("TEAM4_STUDENT_MANAGEMENT_SYSTEM");
           String generatingPassord = generatePassword();
           System.out.println("generatePassword() = " + generatingPassord);
            saveMail(to,generatingPassord);
           javaMailSender.send(simpleMailMessage);
       }catch (Exception e){
           e.printStackTrace();
           throw new EmailSendingMessageException("Exception when sending message to email");
       }
    }
    @Transactional
    public String validatePassword(String email, String password){
       try {
           MailEntity mail = maileRepository.findByEmail(email).
                   orElseThrow(() -> new RuntimeException("Email not found"));
           if (!mail.getVerificationPassword().equals(password)) {
               throw new RuntimeException("Incorrect password");
           }
           userRepository.updateVerification(email);
           return "Successfully verification";
       }catch (Exception e){
           e.printStackTrace();
           return e.getMessage();
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
    private void saveMail(String email, String generatePassword){
        Optional<MailEntity> byEmail = maileRepository.findByEmail(email);
        if(byEmail.isEmpty()){
            maileRepository.save(new MailEntity(email,generatePassword));
        }else {
            MailEntity mailEntity = byEmail.get();
            mailEntity.setVerificationPassword(generatePassword);
            maileRepository.save(mailEntity);
        }

    }

}
