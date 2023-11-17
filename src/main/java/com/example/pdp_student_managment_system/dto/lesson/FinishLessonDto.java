package com.example.pdp_student_managment_system.dto.lesson;

import com.example.pdp_student_managment_system.dto.attandance.StudentAttendanceRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FinishLessonDto {
    private UUID id;
    private List<StudentAttendanceRequestDto> studentAttendance;
}
