package com.example.pdp_student_managment_system.repository;

import com.example.pdp_student_managment_system.entity.Course;
import com.example.pdp_student_managment_system.enums.CourseName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    Boolean existsByCourseName(CourseName courseName);
}
