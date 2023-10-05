package com.example.pdp_student_managment_system.entity;

import com.example.pdp_student_managment_system.enums.CourseName;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private CourseName courseName;
    private Integer duration;
}
