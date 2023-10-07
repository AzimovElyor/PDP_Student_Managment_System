package com.example.pdp_student_managment_system.service;

import com.example.pdp_student_managment_system.dto.StudentRequestDto;
import com.example.pdp_student_managment_system.dto.StudentResponseDto;
import com.example.pdp_student_managment_system.entity.Student;
import com.example.pdp_student_managment_system.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    public void create(StudentRequestDto studentRequestDto){
        if (studentRepository.existsByPhoneNumber(studentRequestDto.getPhoneNumber())) {
            throw new RuntimeException("Phone number already exists");
        }
       modelMapper.map(studentRequestDto, Student.class);
    }
    public List<StudentResponseDto> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> all = studentRepository.findAll(pageable);
     return    all.get()
                .map(StudentResponseDto::new)
                .toList();
    }

}
