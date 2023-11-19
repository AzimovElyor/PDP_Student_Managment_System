package com.example.pdp_student_managment_system.service;

import com.example.pdp_student_managment_system.dto.PageDto;
import com.example.pdp_student_managment_system.dto.course.CourseResponseDto;
import com.example.pdp_student_managment_system.dto.group.GroupRequestDto;
import com.example.pdp_student_managment_system.dto.group.GroupResponseDto;
import com.example.pdp_student_managment_system.dto.student.StudentResponseDto;
import com.example.pdp_student_managment_system.dto.user.UserResponseDto;
import com.example.pdp_student_managment_system.entity.*;
import com.example.pdp_student_managment_system.enums.GroupStatus;
import com.example.pdp_student_managment_system.enums.GroupType;
import com.example.pdp_student_managment_system.enums.LessonStatus;
import com.example.pdp_student_managment_system.enums.UserRole;
import com.example.pdp_student_managment_system.exception.DataNotFoundException;
import com.example.pdp_student_managment_system.repository.*;
import com.example.pdp_student_managment_system.util.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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





  public GroupResponseDto create(GroupRequestDto groupRequestDto) {
    if (groupRepository.existsByGroupName(groupRequestDto.getGroupName())) {
      throw new RuntimeException("Group name already exists");
    }
    UserEntity mentor = userRepository.findByIdAndIsActiveTrueAndRoleAndIsVerificationTrue(groupRequestDto.getMentorId(), UserRole.MENTOR)
            .orElseThrow(() -> new RuntimeException("Mentor not found or mentor not active or mentor no verification"));

    Course course = courseRepository.findById(groupRequestDto.getCourseId())
            .orElseThrow(() -> new RuntimeException("Course not found"));
    Group group = modelMapper.map(groupRequestDto,Group.class);
    group.setGroupName(groupRequestDto.getGroupName());
    group.setMentor(mentor);
    group.setCourse(course);
    groupRepository.save(group);
    return mapToGroupResponse(group);
  }
  public PageDto<GroupResponseDto> getAll(Integer page, Integer size){
    Pageable pageable = PageRequest.of(page,size);
    Page<Group> all = groupRepository.findAll(pageable);

   return mapPageToPageDto(all);
  }
  public String addStudentToGroup(UUID groupId, UUID studentId){
    Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new RuntimeException("Group not found or group status finished"));
    Student student = studentRepository.findByIdAndIsActiveTrue(studentId)
            .orElseThrow(() -> new RuntimeException(MessageConstants.STUDENT_NOT_FOUND));
    if (group.getGroupStatus() == GroupStatus.FINISHED) {
      throw new RuntimeException("Group is finished");
    }
    if(group.getStudents().size() > group.getMaxStudentCount() ){
      throw new RuntimeException("there were already "+ group.getMaxStudentCount() + " students in the group");
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
            .orElseThrow(() -> new RuntimeException(MessageConstants.GROUP_NOT_FOUND));
    if(group.getGroupStatus() != GroupStatus.CREATED){
      throw new RuntimeException("Group already started or finished");
    }
    if (!(group.getStudents().size()  >= group.getMinStudentCount())) {
          throw new RuntimeException("there should be at least " + group.getMinStudentCount() + " students in the group");
    }

    group.setGroupStatus(GroupStatus.IN_PROGRESS);
    group.setStartedDate(LocalDateTime.now());
    group.setModuleNum(1);
    groupRepository.save(group);
    startFirstModule(group);
  }
  public void finishGroup(UUID id){
    Group group = groupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(MessageConstants.GROUP_NOT_FOUND));
    if(group.getGroupStatus() == GroupStatus.FINISHED){
      throw new RuntimeException("Group already finished");
    }
    group.setGroupStatus(GroupStatus.FINISHED);
    groupRepository.save(group);
  }
  public List<GroupResponseDto> getMentorsGroup(UUID mentorId, GroupStatus groupStatus) {
    userRepository.findByIdAndIsActiveTrue(mentorId)
            .orElseThrow(() -> new DataNotFoundException(MessageConstants.MENTOR_NOT_FOUND));
    List<Group> mentorGroups = groupRepository.findByMentorIdAndGroupStatus(mentorId, groupStatus);
    return mentorGroups.stream()
            .map(this::mapToGroupResponse)
            .toList();
  }
  public List<GroupResponseDto> getStudentGroups(UUID studentId, GroupStatus groupStatus){
    studentRepository.findByIdAndIsActiveTrue(studentId)
            .orElseThrow(() -> new DataNotFoundException(MessageConstants.STUDENT_NOT_FOUND));
    List<Group> studentsGroups = groupRepository.getStudentsGroups(groupStatus, studentId);
    return studentsGroups.stream()
            .map(this::mapToGroupResponse)
            .toList();
  }
  public String deleteStudent(UUID groupId, UUID studentId){
 groupRepository.findById(groupId)
            .orElseThrow(() -> new DataNotFoundException(MessageConstants.GROUP_NOT_FOUND));
 studentRepository.findByIdAndIsActiveTrue(studentId)
            .orElseThrow(() -> new DataNotFoundException(MessageConstants.STUDENT_NOT_FOUND));
  groupRepository.deleteStudentInGroup(studentId,groupId);
  return MessageConstants.DELETE;
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
    int moduleLessons = group.getGroupType()== GroupType.BOOTCAMP ? GroupType.BOOTCAMP.getModuleLessons():GroupType.ROADMAP.getModuleLessons();
    for(int i = 1; i <= moduleLessons ; i++ ){
      Lesson entity = new Lesson(group, i, group.getModuleNum(), LessonStatus.CREATED,null);
      System.out.println("entity = " + entity);
      lessonRepository.save(entity);
    }

  }
  private PageDto<GroupResponseDto> mapPageToPageDto(Page<Group> groups){
    PageDto<GroupResponseDto> pageDto = new PageDto<>();
    pageDto.setContent(
            groups.getContent()
                    .stream()
                    .map(this::mapToGroupResponse)
                    .toList()
    );
    pageDto.setPageNumber(groups.getPageable().getPageNumber());
    pageDto.setSize(groups.getPageable().getPageSize());
    pageDto.setLast(pageDto.isLast());
    pageDto.setFirst(pageDto.isFirst());
    pageDto.setSorted(groups.getPageable().getSort().isSorted());
    pageDto.setOffset(groups.getPageable().getOffset());
    pageDto.setTotalPages(groups.getTotalPages());
    pageDto.setTotalElements(groups.getTotalElements());
    return pageDto;
  }



}
