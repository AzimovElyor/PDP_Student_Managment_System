package com.example.pdp_student_managment_system.entity;

import com.example.pdp_student_managment_system.enums.LessonStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Lesson extends BaseEntity {
    @OneToOne
    private Group group;
    private Integer lessonNumber;
    @Enumerated(EnumType.STRING)
    private LessonStatus lessonStatus = LessonStatus.CREATED;
}
