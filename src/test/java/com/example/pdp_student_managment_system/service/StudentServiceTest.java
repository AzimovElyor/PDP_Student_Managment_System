package com.example.pdp_student_managment_system.service;

import com.example.pdp_student_managment_system.dto.student.StudentRequestDto;
import com.example.pdp_student_managment_system.dto.student.StudentResponseDto;
import com.example.pdp_student_managment_system.entity.Student;
import com.example.pdp_student_managment_system.enums.UserRole;
import com.example.pdp_student_managment_system.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StudentServiceTest {
    @Mock
   private StudentRepository studentRepository;
    @Mock
    private ModelMapper modelMapper;
    private StudentService studentService;
    @Mock
    private Student student;
    private StudentRequestDto studentRequestDto;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        studentRequestDto = new StudentRequestDto("Elyor","Azimov","994724134", LocalDate.of(2005,2,12));
        studentService = new StudentService(studentRepository,modelMapper);
        student = new Student("Elyor","Azimov","994724134", UserRole.STUDENT, LocalDate.of(2005,2,12),true);

    }
    @Test
    void create() {
        System.out.println(studentRequestDto);
        System.out.println(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(modelMapper.map(studentRequestDto,Student.class)).thenReturn(student);
        Assertions.assertNotNull(studentService.create(studentRequestDto));
        StudentResponseDto studentResponseDto = studentService.create(studentRequestDto);
        System.out.println(studentResponseDto);
        assertNotNull(studentResponseDto);
        assertEquals(studentResponseDto.getFullName(),(studentRequestDto.getName() + " " + studentRequestDto.getSurname()));

    }
    @Test
    void deleteById(){
        UUID id = UUID.randomUUID();
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(studentRepository.save(student));
        assertThrows(RuntimeException.class , ()-> studentService.delete(id));

    }
}