package com.example.pdp_student_managment_system.dto.user;

import com.example.pdp_student_managment_system.enums.Permissions;
import com.example.pdp_student_managment_system.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class UserRequestDto {
    @NotNull(message = "Name must not be null or empty ")
    private String name;
    private String surname;
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$" ,message = "Incorrect email")
    private String email;
    @Size(min = 4,max = 16, message = "Password mmi length = {min} and max length= {max}")
    @NotNull(message = "Password must not be null")
    private String password;
    @NotNull(message = "Role must not be null")
    private UserRole role;
    @NotNull(message = "Permission must not be null")
    private Set<Permissions> permissions;
}
