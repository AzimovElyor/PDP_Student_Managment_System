package com.example.pdp_student_managment_system.controller;

import com.example.pdp_student_managment_system.dto.StudentRequestDto;
import com.example.pdp_student_managment_system.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
  public void create(@RequestBody StudentRequestDto studentRequestDto){

      studentService.create(studentRequestDto);
  }
}
