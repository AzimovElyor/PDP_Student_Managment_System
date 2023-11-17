package com.example.pdp_student_managment_system.controller;

import com.example.pdp_student_managment_system.dto.PageDto;
import com.example.pdp_student_managment_system.dto.course.CourseRequestDto;
import com.example.pdp_student_managment_system.dto.course.CourseResponseDto;
import com.example.pdp_student_managment_system.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
@PostMapping("/create")
@PreAuthorize("hasAuthority('ADD_COURSE') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<CourseResponseDto> saveCourse(@Valid @RequestBody CourseRequestDto courseRequestDto){
    return new ResponseEntity<>(courseService.save(courseRequestDto), HttpStatus.CREATED);
}

@GetMapping("/get-all")
    public ResponseEntity<PageDto<CourseResponseDto>> getAllCourse(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "7") Integer size) {
    return ResponseEntity.ok(courseService.getAll(page,size));
}
@PreAuthorize("hasAuthority('UPDATE_COURSE') or hasRole('SUPER_ADMIN')")
@PutMapping("/update-duration/{id}")
    public ResponseEntity<CourseResponseDto> updateCourseDuration(
            @PathVariable UUID id,
            @RequestParam Integer duration
){
    return ResponseEntity.status(200).body(courseService.updateDuration(id,duration));
}

}
