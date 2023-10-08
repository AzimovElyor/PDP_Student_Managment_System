package com.example.pdp_student_managment_system.dto.student;

import com.example.pdp_student_managment_system.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponseDto {
    private UUID id;
    private String fullName;
    private String phoneNumber;
    private Integer age;
    private boolean isActive;
    private LocalDateTime createdDate;
    public StudentResponseDto(Student student){
        this.id = student.getId();
        this.fullName = student.getName() + student.getSurname();
        this.phoneNumber = student.getPhoneNumber();
        this.age = Period.between(student.getDateOfBirth(), LocalDate.now()).getYears();
        this.isActive = student.getIsActive();
        this.createdDate = student.getCreatedDate();
    }
}
