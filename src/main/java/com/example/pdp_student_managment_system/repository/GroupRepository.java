package com.example.pdp_student_managment_system.repository;

import com.example.pdp_student_managment_system.entity.Group;
import com.example.pdp_student_managment_system.enums.GroupStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {
  Boolean existsByGroupName(String groupName);
 /* Optional<Group> findByIdAndGroupStatusIn(UUID id, List<GroupStatus> groupStatus);*/
  @Query(value = """
 select exists(select s from student s
 inner join groups_students gs on s.id = gs.students_id
 inner join groups g on g.id = gs.groups_id
 where s.id = :studentId and g.id = :groupId)
""",nativeQuery = true)
  Boolean existStudent(@Param("studentId") UUID studentId,
                       @Param("groupId") UUID groupId);

}
