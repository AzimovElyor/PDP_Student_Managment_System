package com.example.pdp_student_managment_system.entity;

import com.example.pdp_student_managment_system.enums.LessonStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Lesson extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Group group;
    private Integer lessonNumber;
    private Integer moduleNumber;
    @Enumerated(EnumType.STRING)
    private LessonStatus lessonStatus = LessonStatus.CREATED;
    @OneToMany(cascade = CascadeType.ALL)
    private List<StudentAttendance> studentAttendance;


}
