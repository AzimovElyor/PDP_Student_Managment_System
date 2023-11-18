package com.example.pdp_student_managment_system.dto.group;

import com.example.pdp_student_managment_system.dto.course.CourseResponseDto;
import com.example.pdp_student_managment_system.dto.lesson.LessonResponseDto;
import com.example.pdp_student_managment_system.dto.student.StudentResponseDto;
import com.example.pdp_student_managment_system.dto.user.UserResponseDto;
import com.example.pdp_student_managment_system.entity.Course;
import com.example.pdp_student_managment_system.entity.UserEntity;
import com.example.pdp_student_managment_system.enums.GroupStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class GroupResponseDto {
    private UUID id;
    private String groupName;
    private UserResponseDto  mentor;
    private CourseResponseDto course;
    private List<StudentResponseDto> students;
    private GroupStatus groupStatus;
    private Integer moduleNum;
    Integer studentCount ;
    private LocalDateTime startedDate;
    private LocalDateTime finishedDate;
    private LocalDateTime createdDate;
}
