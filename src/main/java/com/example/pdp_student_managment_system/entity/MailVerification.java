package com.example.pdp_student_managment_system.entity;

import com.example.pdp_student_managment_system.enums.MessageType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MailVerification extends BaseEntity{
    @Column( nullable = false)
    private String email;
    @Column(nullable = false)
    private String message;
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
}
