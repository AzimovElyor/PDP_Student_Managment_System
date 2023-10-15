package com.example.pdp_student_managment_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MailVerification extends BaseEntity{
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String verificationPassword;
}
