package com.example.pdp_student_managment_system.repository;

import com.example.pdp_student_managment_system.entity.MailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MaileRepository extends JpaRepository<MailVerification, UUID> {
    Optional<MailVerification> findByEmail(String email);
}
