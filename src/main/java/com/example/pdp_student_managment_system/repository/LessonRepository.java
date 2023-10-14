package com.example.pdp_student_managment_system.repository;

import com.example.pdp_student_managment_system.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    List<Lesson> findByGroupIdAndModuleNumber(UUID groupId,Integer moduleNumber);
}
