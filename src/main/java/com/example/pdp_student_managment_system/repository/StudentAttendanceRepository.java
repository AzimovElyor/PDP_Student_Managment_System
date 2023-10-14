package com.example.pdp_student_managment_system.repository;

import com.example.pdp_student_managment_system.entity.StudentAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, UUID> {

}
