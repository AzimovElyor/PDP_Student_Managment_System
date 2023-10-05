package com.example.pdp_student_managment_system.entity;

import com.example.pdp_student_managment_system.enums.GroupStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "groups")
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
    private List<Student> students;
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;
    @Enumerated(EnumType.STRING)
    private GroupStatus groupStatus = GroupStatus.CREATED;
    private Integer moduleNum;

}
