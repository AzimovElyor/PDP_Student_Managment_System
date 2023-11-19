package com.example.pdp_student_managment_system.repository;

import com.example.pdp_student_managment_system.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Boolean existsByPhoneNumber(String phoneNUmber);
    Optional<Student>findByIdAndIsActiveTrue(UUID id);
}
