package com.example.pdp_student_managment_system.dto.group;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class GroupRequestDto {
    @NotBlank(message = "Group name must not be empty or null")
    private String groupName;
    @NotNull(message = "Mentor id must not be empty or null")
    private UUID mentorId;
    @NotNull(message = "Course id must not be empty or null")
    private UUID courseId;
}
