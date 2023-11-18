package com.example.pdp_student_managment_system.controller;

import com.example.pdp_student_managment_system.dto.PageDto;
import com.example.pdp_student_managment_system.dto.user.UserResponseDto;
import com.example.pdp_student_managment_system.dto.user.UserSearchDto;
import com.example.pdp_student_managment_system.entity.UserEntity;
import com.example.pdp_student_managment_system.enums.UserRole;
import com.example.pdp_student_managment_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable UUID id){
        return ResponseEntity.ok(userService.findById(id));
    }
    @GetMapping("/search")
    public ResponseEntity<PageDto<UserResponseDto>> getAllUsers(
            @RequestParam(required = false , defaultValue = "0") Integer page,
            @RequestParam(required = false , defaultValue = "10") Integer size

    ){
          return ResponseEntity.ok(userService.getAll(page,size));
    }
    @GetMapping("/get-all")
    public ResponseEntity<PageDto<UserResponseDto>> getAllUsers(UserSearchDto userSearchDto){
          return ResponseEntity.ok(userService.searchUser(userSearchDto));
    }
}
