package com.example.pdp_student_managment_system.repository;

import com.example.pdp_student_managment_system.dto.user.UserResponseDto;
import com.example.pdp_student_managment_system.entity.UserEntity;
import com.example.pdp_student_managment_system.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
    @Modifying
    @Query(value = "update UserEntity u set u.isVerification = true , u.updatedDate=current_timestamp " +
            "where u.email = :email")
     void updateVerification(@Param("email") String email);
    Optional<UserEntity> findByIdAndIsActiveTrueAndRoleAndIsVerificationTrue(UUID id, UserRole role);
    Optional<UserEntity> findByIdAndIsActiveTrue(UUID id);
    Page<UserEntity> getAllByIsActiveTrue(Pageable pageable);
/*    Page<UserEntity>
    findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndRoleAndPhoneNumberContainingIgnoreCaseAndIsActiveTrue
            (String name, String surname, String email, UserRole role, String  phoneNumber, Pageable pageable);*/
    @Query("SELECT u FROM UserEntity u WHERE " +
            "(:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:surname IS NULL OR LOWER(u.surname) LIKE LOWER(CONCAT('%', :surname, '%'))) AND " +
            "(:phoneNumber IS NULL OR LOWER(u.phoneNumber) LIKE LOWER(CONCAT('%', :phoneNumber, '%'))) AND " +
            "(:role IS NULL OR u.role = :role)")
    Page<UserEntity> search(@Param("name") String name,
                      @Param("email") String email,
                      @Param("surname") String surname,
                      @Param("phoneNumber") String phoneNumber,
                      @Param("role") UserRole role,
                            Pageable pageable);

}
