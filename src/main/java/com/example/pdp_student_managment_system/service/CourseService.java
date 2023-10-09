package com.example.pdp_student_managment_system.service;

import com.example.pdp_student_managment_system.dto.course.CourseRequestDto;
import com.example.pdp_student_managment_system.dto.course.CourseResponseDto;
import com.example.pdp_student_managment_system.entity.Course;
import com.example.pdp_student_managment_system.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CourseResponseDto save(CourseRequestDto courseRequestDto){
        if(courseRepository.existsByCourseName(courseRequestDto.getCourseName())){
            throw new RuntimeException("Course name already exists");
        }
        Course course = modelMapper.map(courseRequestDto, Course.class);
        courseRepository.save(course);
        return modelMapper.map(course,CourseResponseDto.class);
    }
    public List<CourseResponseDto> getAll(){
        return modelMapper.map(courseRepository.findAll(),new TypeToken<List<CourseResponseDto>>(){}.getType());
    }
    public CourseResponseDto updateDuration(UUID courseId,Integer duration){
        if(duration < 2) throw new RuntimeException("Duration must not less than 2");
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setDuration(duration);
        courseRepository.save(course);
        return modelMapper.map(course,CourseResponseDto.class);
    }
}
