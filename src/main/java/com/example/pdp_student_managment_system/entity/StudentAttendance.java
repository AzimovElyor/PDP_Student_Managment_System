package com.example.pdp_student_managment_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "attendance_student")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class StudentAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private Student student;
    private Boolean isCome;
    private String reason;

}
