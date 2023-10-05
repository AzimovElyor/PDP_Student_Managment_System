package com.example.pdp_student_managment_system.entity;

import com.example.pdp_student_managment_system.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Student extends BaseEntity{
    @Column(nullable = false)
    private String name;
    private String surname;
    @Column(unique = true)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.STUDENT;
    private Boolean isActive = true;
}
