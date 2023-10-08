package com.example.pdp_student_managment_system.repository;

import com.example.pdp_student_managment_system.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
    @Modifying
    @Query(value = "update UserEntity u set u.isVerification = true , u.updatedDate=current_timestamp " +
            "where u.email = :email")
     void updateVerification(@Param("email") String email);

}
