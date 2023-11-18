package com.example.pdp_student_managment_system.dto.group;

import com.example.pdp_student_managment_system.enums.GroupType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;
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
    private GroupType groupType;
    private UUID lessonTimes;
    @Min(value = 10,message = "Group min students count grater then {value}")
    private Integer minStudentCount;
    @Max(value = 30,message = "Group max student count ")
    private Integer maxStudentCount;

}
