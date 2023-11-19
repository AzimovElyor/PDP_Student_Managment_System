package com.example.pdp_student_managment_system.dto.student;

import com.example.pdp_student_managment_system.util.MessageConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@Builder
@ParameterObject
public class StudentRequestDto {
   @NotNull(message = "Student name must not be empty or null")
    private String name;
    private String surname;
    @Pattern(regexp = MessageConstants.UZBEK_PHONE_NUMBER_REGEX,message = MessageConstants.INVALID_PHONE)
    private String phoneNumber;
    @NotNull(message = "Student date of Birth must not be null")
    private LocalDate dateOfBirth;
}
