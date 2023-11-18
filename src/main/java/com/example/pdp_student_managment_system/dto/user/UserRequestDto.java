package com.example.pdp_student_managment_system.dto.user;

import com.example.pdp_student_managment_system.enums.CourseName;
import com.example.pdp_student_managment_system.enums.Permissions;
import com.example.pdp_student_managment_system.enums.UserRole;
import jakarta.validation.constraints.*;
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
    @Pattern(regexp = "^(95|99|90|94|88|33)[0-9]{7}$",message = "Invalid uzbek phone number")
    @NotBlank(message = "Phone number must not be null or empty")
    private String phoneNumber;
    @Size(min = 4,max = 16, message = "Password mmi length = {min} and max length= {max}")
    @NotNull(message = "Password must not be null")
    private String password;
    private Set<CourseName> languages;
    @NotNull(message = "Role must not be null")
    private UserRole role;
    @NotNull(message = "Permission must not be null")
    private Set<Permissions> permissions;
}
