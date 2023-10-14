package com.example.pdp_student_managment_system.controller;

import com.example.pdp_student_managment_system.dto.lesson.FinishLessonDto;
import com.example.pdp_student_managment_system.dto.lesson.LessonResponseDto;
import com.example.pdp_student_managment_system.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lesson")
public class LessonController {
    @Autowired
    private LessonService lessonService;
    @PreAuthorize("hasRole('MENTOR') or hasAuthority('GROUP_LESSONS')")
    @GetMapping("/group-lesson/{groupId}")
    public ResponseEntity<List<LessonResponseDto>> getGroupLessons(@PathVariable UUID groupId){
    return new ResponseEntity<>(lessonService.getGroupLessons(groupId), HttpStatus.OK);
}
    @GetMapping("/start/{id}")
    public ResponseEntity<LessonResponseDto> startLessons(@PathVariable UUID id){
        return ResponseEntity.ok(lessonService.startLesson(id));
    }
    @PutMapping("/finish")
    public ResponseEntity<String> finishLesson(@RequestBody FinishLessonDto lesson){
        lessonService.finishLesson(lesson);
        return ResponseEntity.status(200).body("Successfully finished lesson");
    }
}
