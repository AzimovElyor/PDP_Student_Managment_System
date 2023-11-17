package com.example.pdp_student_managment_system.entity;

import com.example.pdp_student_managment_system.enums.GroupStatus;
import com.example.pdp_student_managment_system.enums.GroupType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "Groups")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Group extends BaseEntity{
    @Column(unique = true, nullable = false)
    private String groupName;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity mentor;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Student> students = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;
    @Enumerated(EnumType.STRING)
    private GroupStatus groupStatus = GroupStatus.CREATED;
    @Enumerated(EnumType.STRING)
    private GroupType groupType;
    private Integer moduleNum = 0;
    private Integer minStudentCount;
    private Integer maxStudentCount;
    private LocalDateTime startedDate;
    private LocalDateTime finishedDate;


}
