package com.example.pdp_student_managment_system.repository;

import com.example.pdp_student_managment_system.entity.Course;
import com.example.pdp_student_managment_system.enums.CourseName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    Boolean existsByCourseName(CourseName courseName);
    Optional<Course> findByIdAndIsActiveTrue(UUID id);
    Page<Course> getAllByIsActiveTrue(Pageable pageable);
}
