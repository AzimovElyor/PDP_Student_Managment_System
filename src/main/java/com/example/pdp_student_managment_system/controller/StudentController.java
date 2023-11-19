
package com.example.pdp_student_managment_system.controller;

import com.example.pdp_student_managment_system.dto.student.StudentRequestDto;
import com.example.pdp_student_managment_system.dto.student.StudentResponseDto;
import com.example.pdp_student_managment_system.service.StudentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('CREATE_STUDENT')")
    @SecurityRequirement(
            name = "bearerAuth"
    )
   public ResponseEntity<StudentResponseDto> create(@RequestBody StudentRequestDto studentRequestDto){
      return new ResponseEntity<>(studentService.create(studentRequestDto),HttpStatus.CREATED);
  }
  @SecurityRequirement(
          name = "bearerAuth"
  )
  @PreAuthorize("hasRole('ADMIN') and hasAuthority('ALL_STUDENTS') or hasRole('SUPER_ADMIN')")
  @GetMapping("/get-all")
    public ResponseEntity<List<StudentResponseDto>> getAllStudent(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
  ){
    return new ResponseEntity<>(studentService.getAll(page,size), HttpStatus.OK);
  }
    @SecurityRequirement(
            name = "bearerAuth"
    )
  @PreAuthorize("hasRole('ADMIN') and hasAuthority('STUDENT_DELETE')")
  @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable UUID id){
        studentService.delete(id);
        return ResponseEntity.ok("Successfully deleted");
  }

}
