package com.example.pdp_student_managment_system.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonTimes extends BaseEntity {
    private Set<DayOfWeek> dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
