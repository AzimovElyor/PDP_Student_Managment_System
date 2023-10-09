package com.example.pdp_student_managment_system.entity;

import com.example.pdp_student_managment_system.enums.GroupStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private Integer moduleNum = 0;
    private LocalDateTime startedDate;

}
