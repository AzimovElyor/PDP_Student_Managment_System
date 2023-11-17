package com.example.pdp_student_managment_system.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Permissions {
   CREATE_GROUP(Set.of(UserRole.ADMIN)),
   UPDATE_GROUP(Set.of(UserRole.ADMIN)),
   DELETE_GROUP(Set.of(UserRole.ADMIN)),
   GET_ALL_GROUPS(Set.of(UserRole.ADMIN)),
   GET_GROUP(Set.of(UserRole.ADMIN)),
    ADD_STUDENT_TO_GROUP(Set.of(UserRole.ADMIN)),
    DELETE_STUDENT_TO_GROUP(Set.of(UserRole.ADMIN)),

    CREATE_STUDENT(Set.of(UserRole.ADMIN)),
    GET_ALL_STUDENTS(Set.of(UserRole.ADMIN)),
    DELETE_STUDENT(Set.of(UserRole.ADMIN)),
    UPDATE_STUDENT(Set.of(UserRole.ADMIN)),

    CREATE_ADMIN(Set.of(UserRole.SUPER_ADMIN)),
    BLOCK_ADMIN(Set.of(UserRole.SUPER_ADMIN)),
    DELETE_ADMIN(Set.of(UserRole.SUPER_ADMIN)),

    CREATE_TEACHER(Set.of(UserRole.SUPER_ADMIN)),
    DELETE_TEACHER(Set.of(UserRole.SUPER_ADMIN)),
    BLOCK_TEACHER(Set.of(UserRole.SUPER_ADMIN)),

    CREATE_COURSE(Set.of(UserRole.ADMIN)),
        UPDATE_COURSE(Set.of(UserRole.ADMIN)),
    GET_COURSE(Set.of(UserRole.ADMIN,UserRole.STUDENT,UserRole.MENTOR)),
    DELETE_COURSE(Set.of(UserRole.ADMIN)),

    CREATE_LESSON_TIMES(Set.of(UserRole.ADMIN)),
    UPDATE_LESSON_TIMES(Set.of(UserRole.ADMIN)),
    DELETE_LESSON_TIMES(Set.of(UserRole.ADMIN)),
    GET_LESSON_TIMES(Set.of(UserRole.ADMIN));
   private final Set<UserRole> roles;

}
