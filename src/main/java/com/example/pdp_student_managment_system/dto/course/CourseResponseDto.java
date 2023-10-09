package com.example.pdp_student_managment_system.dto.course;

import com.example.pdp_student_managment_system.enums.CourseName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDto {
    private UUID id;
    private CourseName courseName;
    private Integer duration;
    private LocalDateTime createdDate;
}
