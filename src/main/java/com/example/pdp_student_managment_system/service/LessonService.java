package com.example.pdp_student_managment_system.service;

import com.example.pdp_student_managment_system.dto.attandance.StudentAttendanceRequestDto;
import com.example.pdp_student_managment_system.dto.attandance.StudentAttendanceResponseDto;
import com.example.pdp_student_managment_system.dto.lesson.FinishLessonDto;
import com.example.pdp_student_managment_system.dto.lesson.LessonResponseDto;
import com.example.pdp_student_managment_system.dto.student.StudentResponseDto;
import com.example.pdp_student_managment_system.entity.Group;
import com.example.pdp_student_managment_system.entity.Student;
import com.example.pdp_student_managment_system.enums.GroupStatus;
import com.example.pdp_student_managment_system.enums.LessonStatus;
import com.example.pdp_student_managment_system.entity.*;
import com.example.pdp_student_managment_system.repository.GroupRepository;
import com.example.pdp_student_managment_system.repository.LessonRepository;
import com.example.pdp_student_managment_system.repository.StudentAttendanceRepository;
import com.example.pdp_student_managment_system.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;
    private final StudentAttendanceRepository studentAttendanceRepository;
    private final StudentRepository studentRepository;

    public void finishLesson(FinishLessonDto lessonDto){

           Lesson lesson = lessonRepository.findById(lessonDto.getId())
                   .orElseThrow(() -> new RuntimeException("Lesson not found"));

           Group group = lesson.getGroup();

           if (lesson.getLessonStatus() != LessonStatus.STARTED) {
               throw new RuntimeException("Lesson not started or already finished");
           }
           if (lesson.getLessonNumber().equals(group.getGroupType().getModuleLessons()) && group.getModuleNum().equals(group.getCourse().getDuration())) {
               group.setGroupStatus(GroupStatus.FINISHED);
               groupRepository.save(group);
           } else if (lesson.getLessonNumber().equals(group.getGroupType().getModuleLessons())) {
               createNewModuleLessons(lesson.getModuleNumber() + 1, group);
           }
           lesson.setLessonStatus(LessonStatus.FINISHED);
           List<StudentAttendance> studentAttendance = mapAttendanceRequestToEntity(lessonDto.getStudentAttendance());
           lesson.setStudentAttendance(studentAttendance);
           lessonRepository.save(lesson);
    }
    public LessonResponseDto startLesson(UUID lessonId){
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        if(lesson.getLessonStatus() != LessonStatus.CREATED){
            throw new RuntimeException("Lesson already started or finished");
        }

        List<StudentAttendance> lessonAttendance = createLessonAttendance(lesson.getGroup().getStudents());

        lesson.setLessonStatus(LessonStatus.STARTED);
        lesson.setStudentAttendance(lessonAttendance);
        lessonRepository.save(lesson);
       return mapLessonToResponse( lesson);

    }

    public List<LessonResponseDto> getGroupLessons(UUID groupId){
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        List<Lesson> moduleLessons = lessonRepository.findByGroupIdAndModuleNumber(groupId,group.getModuleNum());
        return moduleLessons.stream()
                .map(this::mapLessonToResponse)
                .toList();
    }

    private List<StudentAttendance> createLessonAttendance(List<Student> students){
        List<StudentAttendance> studentAttendances = new ArrayList<>();
        for (Student student : students) {
            studentAttendances.add(new StudentAttendance(null,student,true,null));
        }
        studentAttendanceRepository.saveAll(studentAttendances);
        return studentAttendances;

    }

    private List<StudentAttendanceResponseDto> mapStudentsAttendanceToResponse(List<StudentAttendance> studentAttendances){
       return studentAttendances.stream()
                .map(attendance ->
                        new StudentAttendanceResponseDto(attendance.getId(),new StudentResponseDto(attendance.getStudent()),
                                attendance.getIsCome()) )
                .toList();
    }
    private LessonResponseDto mapLessonToResponse( Lesson lesson) {
        return LessonResponseDto.builder()
                .id(lesson.getId())
                .lessonNumber(lesson.getLessonNumber())
                .lessonStatus(lesson.getLessonStatus())
                .createdDate(lesson.getCreatedDate())
                .moduleNumber(lesson.getModuleNumber())
                .attendance(mapStudentsAttendanceToResponse(lesson.getStudentAttendance()))
                .build();
    }
    private List<StudentAttendance> mapAttendanceRequestToEntity(List<StudentAttendanceRequestDto> attendanceRequestDto){
     return    attendanceRequestDto.stream()
                .map(att-> {
                    StudentAttendance studentAttendance = new StudentAttendance(att.getId(), getStudentById(att.getStudentId()), att.getIsCome(), att.getReason());
                    studentAttendanceRepository.save(studentAttendance);
                    return studentAttendance;
                })
                .collect(Collectors.toList());
    }
    private Student getStudentById(UUID id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    private void createNewModuleLessons(Integer nextModuleNumber,Group group){
        for(int i = 1; i <= group.getGroupType().getModuleLessons(); i++ ){
            Lesson entity = new Lesson(group, i, nextModuleNumber, LessonStatus.CREATED,null);
            System.out.println("entity = " + entity);
            lessonRepository.save(entity);
        }

    }

}
