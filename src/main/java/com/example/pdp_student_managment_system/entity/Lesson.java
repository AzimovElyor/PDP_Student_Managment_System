package com.example.pdp_student_managment_system.entity;

import com.example.pdp_student_managment_system.enums.LessonStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Lesson extends BaseEntity {
    @ManyToOne
    private Group group;
    private Integer lessonNumber;
    private Integer moduleNumber;
    @Enumerated(EnumType.STRING)
    private LessonStatus lessonStatus = LessonStatus.CREATED;

}
