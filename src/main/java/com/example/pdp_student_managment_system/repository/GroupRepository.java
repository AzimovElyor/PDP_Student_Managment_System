package com.example.pdp_student_managment_system.repository;

import com.example.pdp_student_managment_system.entity.Group;
import com.example.pdp_student_managment_system.enums.GroupStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {
  Boolean existsByGroupName(String groupName);
  List<Group> findByMentorIdAndGroupStatus(UUID mentorId,GroupStatus status);
  @Query(nativeQuery = true,
       value =   """
select g from groups g
inner join groups_students gs on  gs.groups_id = g.id
where gs.students_id = :studentId and g.group_status = :groupStatus
"""
  )
  List<Group> getStudentsGroups(GroupStatus groupStatus,UUID studentId);
  @Query(value = """
 select exists(select s from student s
 inner join groups_students gs on s.id = gs.students_id
 inner join groups g on g.id = gs.groups_id
 where s.id = :studentId and g.id = :groupId)
""",nativeQuery = true)
  Boolean existStudent(@Param("studentId") UUID studentId,
                       @Param("groupId") UUID groupId);

}
