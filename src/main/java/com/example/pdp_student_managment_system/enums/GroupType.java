package com.example.pdp_student_managment_system.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GroupType {
    BOOTCAMP(20),
    ROADMAP(12);
    private final Integer moduleLessons;
}
