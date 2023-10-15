package com.example.pdp_student_managment_system.dto.attandance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentAttendanceRequestDto {
   private UUID id;
   private UUID studentId;
   private Boolean isCome;
   private String reason;
}
