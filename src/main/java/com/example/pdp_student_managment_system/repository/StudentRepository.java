package com.example.pdp_student_managment_system.repository;

import com.example.pdp_student_managment_system.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByPhoneNumber(String phoneNumber);
    Boolean existsByPhoneNumber(String phoneNUmber);
}
