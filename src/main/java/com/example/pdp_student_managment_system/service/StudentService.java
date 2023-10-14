package com.example.pdp_student_managment_system.service;

import com.example.pdp_student_managment_system.dto.student.StudentRequestDto;
import com.example.pdp_student_managment_system.dto.student.StudentResponseDto;
import com.example.pdp_student_managment_system.entity.Student;
import com.example.pdp_student_managment_system.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    public StudentResponseDto create(StudentRequestDto studentRequestDto){
        if (studentRepository.existsByPhoneNumber(studentRequestDto.getPhoneNumber())) {
            throw new RuntimeException("Phone number already exists");
        }
        Student student = modelMapper.map(studentRequestDto, Student.class);
        student.setPhoneNumber("+998" + student.getPhoneNumber());
        studentRepository.save(student);
        return new StudentResponseDto(student);
    }
    public List<StudentResponseDto> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> all = studentRepository.findAll(pageable);
     return    all.get()
                .map(StudentResponseDto::new)
                .toList();
    }

    public void delete(UUID id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        student.setIsActive(false);
        studentRepository.save(student);
    }

}
