package com.example.pdp_student_managment_system.service;

import com.example.pdp_student_managment_system.dto.student.StudentRequestDto;
import com.example.pdp_student_managment_system.dto.student.StudentResponseDto;
import com.example.pdp_student_managment_system.entity.Student;
import com.example.pdp_student_managment_system.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.model.MultipleFailureException.assertEmpty;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private StudentService studentService;
    @Test
    public void save(){

        StudentRequestDto build = StudentRequestDto.builder()
                .name("Elyor")
                .surname("Azimov")
                .phoneNumber("994724134")
                .dateOfBirth(LocalDate.of(2005, 2, 12))
                .build();
        Student student = Student.builder()
                .name("Elyor")
                .surname("Azimov")
                .phoneNumber("994724134")
                .dateOfBirth(LocalDate.of(2005, 2, 12))
                .isActive(true)
                .build();

        when(studentRepository.existsByPhoneNumber(Mockito.any(String.class))).thenReturn(false);
        when(modelMapper.map(build,Student.class)).thenReturn(student);
        when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);
        StudentResponseDto studentResponseDto = studentService.create(build);
        System.out.println(studentResponseDto);
        Assertions.assertNotNull(studentResponseDto);
    }
    @Test
    public void getAll(){
        Page<Student> students = Mockito.mock(Page.class);
        when(studentRepository.findAll(Mockito.any(Pageable.class))).thenReturn(students);
        List<StudentResponseDto> all = studentService.getAll(0, 1);
        System.out.println(all);
        Assertions.assertNotNull(all);

    }
    @Test
    public void findById(){
        UUID id = UUID.fromString("1656e73c-9c66-4cbb-95fb-b84cfcd2416b");
        Student student = Student.builder()
                .name("Axror")
                .surname("Jumayev")
                .isActive(false)
                .dateOfBirth(LocalDate.of(2005,2,12))
                .build();
        student.setId(id);

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        StudentResponseDto byId = studentService.findById(id);
        System.out.println(byId);
        Assertions.assertNotNull(byId);
        Assertions.assertEquals(id, byId.getId());
    }
    @Test
    public void delete(){
        UUID id = UUID.randomUUID();
        Student student = Student.builder()
                .name("Elyor")
                .surname("Azimov")
                .phoneNumber("994724134")
                .dateOfBirth(LocalDate.of(2005, 2, 12))
                .isActive(true)
                .build();
        student.setId(id);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);
        studentService.delete(id);
        Assertions.assertFalse(student.getIsActive());


    }

}