package com.example.pdp_student_managment_system.dto.user;

import com.example.pdp_student_managment_system.enums.CourseName;
import com.example.pdp_student_managment_system.enums.Permissions;
import com.example.pdp_student_managment_system.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private UUID id;
    private String fullName;
    private String email;
    private UserRole role;
    private Set<Permissions> permissions;
    private Set<CourseName> languages;
    private Boolean isActive;
    private Boolean isVerification;
    private LocalDateTime createdDate;
}
