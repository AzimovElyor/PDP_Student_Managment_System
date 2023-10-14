package com.example.pdp_student_managment_system.service;

import com.example.pdp_student_managment_system.dto.course.CourseResponseDto;
import com.example.pdp_student_managment_system.dto.group.GroupRequestDto;
import com.example.pdp_student_managment_system.dto.group.GroupResponseDto;
import com.example.pdp_student_managment_system.dto.student.StudentResponseDto;
import com.example.pdp_student_managment_system.dto.user.UserResponseDto;
import com.example.pdp_student_managment_system.entity.*;
import com.example.pdp_student_managment_system.enums.GroupStatus;
import com.example.pdp_student_managment_system.enums.LessonStatus;
import com.example.pdp_student_managment_system.enums.UserRole;
import com.example.pdp_student_managment_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {
  private final GroupRepository groupRepository;
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final StudentRepository studentRepository;
  private final LessonRepository lessonRepository;
  private final ModelMapper  modelMapper;

  @Value("${group.max-student}")
  Integer maxStudentCount;
  @Value("${group.min-student}")
  Integer minStudentCount;
  @Value("${group.module-lessons}")
  Integer moduleLessons;


  public GroupResponseDto create(GroupRequestDto groupRequestDto) {
    if (groupRepository.existsByGroupName(groupRequestDto.getGroupName())) {
      throw new RuntimeException("Group name already exists");
    }
    UserEntity mentor = userRepository.findByIdAndIsActiveTrueAndRoleAndIsVerificationTrue(groupRequestDto.getMentorId(), UserRole.MENTOR)
            .orElseThrow(() -> new RuntimeException("Mentor not found or mentor not active or mentor no verification"));

    Course course = courseRepository.findById(groupRequestDto.getCourseId())
            .orElseThrow(() -> new RuntimeException("Course not found"));
    Group group = new Group();
    group.setGroupName(groupRequestDto.getGroupName());
    group.setMentor(mentor);
    group.setCourse(course);
    groupRepository.save(group);
    UserResponseDto mentorResponse = mapToUserResponse(group.getMentor());
    CourseResponseDto courseResponse = mapToCourseResponse(group.getCourse());
    return GroupResponseDto.builder()
            .id(group.getId())
            .groupName(group.getGroupName())
            .mentor(mentorResponse)
            .course(courseResponse)
            .groupStatus(group.getGroupStatus())
            .moduleNum(group.getModuleNum())
            .startedDate(group.getStartedDate())
            .createdDate(group.getCreatedDate())
            .studentCount(0)
            .build();
  }
  public List<GroupResponseDto> getAll(Integer page, Integer size){
    Pageable pageable = PageRequest.of(page,size);
    Page<Group> all = groupRepository.findAll(pageable);
   return all.get()
            .map(this::mapToGroupResponse)
            .toList();
  }
  public String addStudentToGroup(UUID groupId, UUID studentId){
    Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new RuntimeException("Group not found or group status finished"));
    Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
    if (group.getGroupStatus() == GroupStatus.FINISHED) {
      throw new RuntimeException("Group is finished");
    }
    if(group.getStudents().size() > maxStudentCount ){
      throw new RuntimeException("there were already "+ maxStudentCount + " students in the group");
    }
    if (groupRepository.existStudent(studentId,groupId)) {
      throw new RuntimeException("Student already in group");
    }

    group.getStudents().add(student);
    groupRepository.save(group);
    return student.getName() + " " + student.getSurname() + " successfully added to " + group.getGroupName();
  }
  public void startGroup(UUID groupId){
    Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new RuntimeException("Group not found"));
    if(group.getGroupStatus() != GroupStatus.CREATED){
      throw new RuntimeException("Group already started or finished");
    }
    if (!(group.getStudents().size()  >= minStudentCount)) {
          throw new RuntimeException("there should be at least " + minStudentCount + " students in the group");
    }

    group.setGroupStatus(GroupStatus.IN_PROGRESS);
    group.setStartedDate(LocalDateTime.now());
    group.setModuleNum(1);
    groupRepository.save(group);
    startFirstModule(group);
  }
  public void finishGroup(UUID id){
    Group group = groupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Group not found"));
    if(group.getGroupStatus() == GroupStatus.FINISHED){
      throw new RuntimeException("Group already finished");
    }
    group.setGroupStatus(GroupStatus.FINISHED);
    groupRepository.save(group);
  }
  public List<GroupResponseDto> getMentorsGroup(UUID mentorId, GroupStatus groupStatus) {
    userRepository.findById(mentorId)
            .orElseThrow(() -> new RuntimeException("Mentor not found"));
    List<Group> mentorGroups = groupRepository.findByMentorIdAndGroupStatus(mentorId, groupStatus);
    return mentorGroups.stream()
            .map(this::mapToGroupResponse)
            .toList();
  }
  public List<GroupResponseDto> getStudentGroups(UUID studentId, GroupStatus groupStatus){
    studentRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
    List<Group> studentsGroups = groupRepository.getStudentsGroups(groupStatus, studentId);
    return studentsGroups.stream()
            .map(this::mapToGroupResponse)
            .toList();
  }
  private UserResponseDto mapToUserResponse(UserEntity user){
    UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);
    userResponseDto.setFullName(user.getName() + " " +user.getSurname());
    return userResponseDto;
  }
  private List<StudentResponseDto> mapToStudentResponse(List<Student> students){
   return students.stream()
           .map(StudentResponseDto::new)
           .toList();
  }
  private CourseResponseDto mapToCourseResponse(Course course) {
    return modelMapper.map(course, CourseResponseDto.class);
  }
  private GroupResponseDto mapToGroupResponse(Group group){
    return GroupResponseDto.builder()
            .id(group.getId())
            .groupName(group.getGroupName())
            .mentor(mapToUserResponse(group.getMentor()))
            .course(mapToCourseResponse(group.getCourse()))
            .students(mapToStudentResponse(group.getStudents()))
            .studentCount(group.getStudents().size())
            .startedDate(group.getStartedDate())
            .createdDate(group.getCreatedDate())
            .groupStatus(group.getGroupStatus())
            .moduleNum(group.getModuleNum())
            .build();
  }
  private void startFirstModule(Group group){
    for(int i = 1; i <= moduleLessons; i++ ){

      Lesson entity = new Lesson(group, i, group.getModuleNum(), LessonStatus.CREATED,null);
      System.out.println("entity = " + entity);
      lessonRepository.save(entity);
    }

  }



}
