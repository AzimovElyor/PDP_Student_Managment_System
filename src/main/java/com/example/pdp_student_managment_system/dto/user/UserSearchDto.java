package com.example.pdp_student_managment_system.dto.user;

import com.example.pdp_student_managment_system.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSearchDto {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private UserRole userRole;
    private Integer size = 10;
    private Integer page = 0;
}
