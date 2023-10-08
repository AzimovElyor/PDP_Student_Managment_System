package com.example.pdp_student_managment_system.dto.student;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@Builder
public class StudentRequestDto {
   @NotNull(message = "Student name must not be empty or null")
    private String name;
    private String surname;
    @Pattern(regexp = "^(95|99|90|94|88|33)}[0-9]{7}$")
    private String phoneNumber;
    @NotNull(message = "Student date of Birth must not be null")
    private LocalDate dateOfBirth;
}
