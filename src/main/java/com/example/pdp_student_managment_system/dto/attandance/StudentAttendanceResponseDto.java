package com.example.pdp_student_managment_system.dto.attandance;

import com.example.pdp_student_managment_system.dto.student.StudentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentAttendanceResponseDto {
    private UUID id;
    private StudentResponseDto student;
    private Boolean isCome;

}
