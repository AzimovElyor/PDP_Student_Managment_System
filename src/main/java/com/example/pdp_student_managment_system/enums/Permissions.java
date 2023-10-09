package com.example.pdp_student_managment_system.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Permissions {
   STUDENT_CREATE(
           Set.of(UserRole.ADMIN)
   ),
   STUDENT_DELETE(
           Set.of(UserRole.ADMIN)
   ),
    GROUP_CREATE(
            Set.of(UserRole.ADMIN)
    ),
    ADD_STUDENT_GROUP(
            Set.of(UserRole.ADMIN)
    ),
   DELETE_STUDENT_GROUP(
           Set.of(UserRole.ADMIN)
   ),
    ADD_MENTOR_GROUP(
            Set.of(UserRole.ADMIN)
    ),
    ALL_STUDENTS(Set.of(UserRole.ADMIN,UserRole.SUPER_ADMIN)),
    GROUP_STUDENTS(
            Set.of(UserRole.ADMIN,UserRole.SUPER_ADMIN,UserRole.MENTOR)
    ),
   ALL_GROUPS(
           Set.of(UserRole.ADMIN,UserRole.SUPER_ADMIN)
   ),
    START_GROUP(
           Set.of(UserRole.ADMIN)
   ),
    FINISH_GROUP(Set.of(UserRole.ADMIN)),

    START_LESSON(Set.of(UserRole.MENTOR)),
    FINISH_LESSON(Set.of(UserRole.MENTOR)),

    ADD_COURSE(Set.of(UserRole.ADMIN)),
    DELETE_COURSE(Set.of(UserRole.ADMIN)),
    UPDATE_COURSE(Set.of(UserRole.ADMIN)),

    ADD_ADMIN(Set.of(UserRole.SUPER_ADMIN)),
    BLOCK_ADMIN(Set.of(UserRole.SUPER_ADMIN)),
    OPEN_BLOCK_ADMIN(Set.of(UserRole.SUPER_ADMIN)),
    ALL_MENTORS(Set.of(UserRole.SUPER_ADMIN,UserRole.ADMIN)),
    DELETE_ADMIN(Set.of(UserRole.SUPER_ADMIN)),
    UPDATE_ADMIN(Set.of(UserRole.SUPER_ADMIN));





   private final Set<UserRole> roles;
}
