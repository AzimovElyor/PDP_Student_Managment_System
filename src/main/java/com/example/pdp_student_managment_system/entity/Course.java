package com.example.pdp_student_managment_system.entity;

import com.example.pdp_student_managment_system.enums.CourseName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Course extends BaseEntity {
    @Column(unique = true,nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseName courseName;
    private Integer duration;
}
