package com.example.pdp_student_managment_system.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ExceptionDto {
    private String message;
    private Integer statusCode;
}
