package com.example.pdp_student_managment_system.dto.lesson;

import com.example.pdp_student_managment_system.enums.LessonStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonResponseDto {
    private UUID id;
    private Integer number;
    private LessonStatus lessonStatus;
    private LocalDateTime createDate;
    private Map<UUID,Boolean> attendance;
}
