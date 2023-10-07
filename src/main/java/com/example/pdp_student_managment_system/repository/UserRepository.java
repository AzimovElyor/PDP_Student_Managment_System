package com.example.pdp_student_managment_system.repository;

import com.example.pdp_student_managment_system.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
}
