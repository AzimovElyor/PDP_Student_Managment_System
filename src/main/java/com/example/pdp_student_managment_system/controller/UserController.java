package com.example.pdp_student_managment_system.controller;

import com.example.pdp_student_managment_system.dto.PageDto;
import com.example.pdp_student_managment_system.dto.user.UserResponseDto;
import com.example.pdp_student_managment_system.dto.user.UserSearchDto;
import com.example.pdp_student_managment_system.entity.UserEntity;
import com.example.pdp_student_managment_system.enums.UserRole;
import com.example.pdp_student_managment_system.service.EmailService;
import com.example.pdp_student_managment_system.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    @GetMapping("/find-by-id/{id}")
    @SecurityRequirement(
            name = "bearerAuth"
    )
    @PreAuthorize("hasAnyAuthority('GET_TEACHER') or hasRole('SUPER_ADMIN')")

    public ResponseEntity<UserResponseDto> findById(@PathVariable UUID id){
        return ResponseEntity.ok(userService.findById(id));
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/get-all")
    @SecurityRequirement(
            name = "bearerAuth"
    )
    public ResponseEntity<PageDto<UserResponseDto>> getAllUsers(
            @RequestParam(required = false , defaultValue = "0") Integer page,
            @RequestParam(required = false , defaultValue = "10") Integer size

    ){
          return ResponseEntity.ok(userService.getAll(page,size));
    }
    @SecurityRequirement(
            name = "bearerAuth"
    )
    @GetMapping("/search")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<PageDto<UserResponseDto>> getAllUsers(UserSearchDto userSearchDto){
          return ResponseEntity.ok(userService.searchUser(userSearchDto));
    }
    @SecurityRequirement(
            name = "bearerAuth"
    )
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable UUID id){
        userService.deleteById(id);
    }
    @PatchMapping("/new-password/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updatePassword(@PathVariable UUID id,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword){
        return userService.newUserPassword(id,oldPassword,newPassword);
    }
    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestParam String email){
        emailService.send(email);
    }
    @PatchMapping("/edit-forgot-password")
    @ResponseStatus(HttpStatus.OK)
    public String updateForgotPassword(@RequestParam String email,
                                       @RequestParam String newPassword){
       return userService.forgotPassword(email, newPassword);
    }



}
