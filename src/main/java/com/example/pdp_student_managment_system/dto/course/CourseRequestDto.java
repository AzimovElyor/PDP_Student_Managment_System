package com.example.pdp_student_managment_system.dto.course;

import com.example.pdp_student_managment_system.enums.CourseName;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;

@Data
@AllArgsConstructor
@Builder
@ParameterObject
public class CourseRequestDto {
    @NotNull(message = "Course name must bot be null or empty")
    private CourseName courseName;
    @NotBlank(message = "Description must not be null or empty")
    private String description;
    @Min(value = 3, message = "The duration of the course should be min {value}")
    @Max(value = 12, message = "The duration of the course should be max {value}")
    private Integer duration;
}
