package com.example.pdp_student_managment_system.repository;

import com.example.pdp_student_managment_system.entity.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MaileRepository extends JpaRepository<MailEntity, UUID> {
    Optional<MailEntity> findByEmail(String email);
}
