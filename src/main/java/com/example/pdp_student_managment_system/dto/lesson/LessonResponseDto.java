package com.example.pdp_student_managment_system.dto.lesson;

import com.example.pdp_student_managment_system.dto.attandance.StudentAttendanceResponseDto;
import com.example.pdp_student_managment_system.enums.LessonStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonResponseDto {
    private UUID id;
    private Integer lessonNumber;
    private Integer moduleNumber;
    private LessonStatus lessonStatus;
    private List<StudentAttendanceResponseDto> attendance;
    private LocalDateTime createdDate;
}
