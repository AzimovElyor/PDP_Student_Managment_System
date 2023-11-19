package com.example.pdp_student_managment_system.dto.user;

import com.example.pdp_student_managment_system.enums.CourseName;
import com.example.pdp_student_managment_system.enums.Permissions;
import com.example.pdp_student_managment_system.enums.UserRole;
import com.example.pdp_student_managment_system.util.MessageConstants;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@ParameterObject
public class UserRequestDto {
    @NotNull(message = "Name must not be null or empty ")
    private String name;
    private String surname;
    @Email(regexp = MessageConstants.EMAIL_REGEX ,message = "Incorrect email")
    @NotBlank(message = "Email must not be null or empty")
    private String email;
    @Pattern(regexp = MessageConstants.UZBEK_PHONE_NUMBER_REGEX,message = "Invalid uzbek phone number")
    @NotBlank(message = "Phone number must not be null or empty")
    private String phoneNumber;
    @Size(min = 4,max = 16, message = "Password min length = {min} and max length= {max}")
    @NotNull(message = "Password must not be null")
    private String password;
    private Set<CourseName> languages;
    @NotNull(message = "Role must not be null")
    private UserRole role;
    @NotNull(message = "Permission must not be null")
    private Set<Permissions> permissions;
}
