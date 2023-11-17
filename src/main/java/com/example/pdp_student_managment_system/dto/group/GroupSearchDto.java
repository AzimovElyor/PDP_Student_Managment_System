package com.example.pdp_student_managment_system.dto.group;

import com.example.pdp_student_managment_system.enums.CourseName;
import com.example.pdp_student_managment_system.enums.GroupStatus;
import com.example.pdp_student_managment_system.enums.GroupType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupSearchDto {
    private String name;
    private String mentorName;
    private GroupType groupType;
    private GroupStatus groupStatus;
    private CourseName courseName;
}
