package com.example.pdp_student_managment_system.controller;

import com.example.pdp_student_managment_system.dto.group.GroupRequestDto;
import com.example.pdp_student_managment_system.dto.group.GroupResponseDto;
import com.example.pdp_student_managment_system.enums.GroupStatus;
import com.example.pdp_student_managment_system.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('GROUP_CREATE') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<GroupResponseDto> createGroup(@Valid @RequestBody GroupRequestDto groupRequestDto){
        return ResponseEntity.status(201).body(groupService.create(groupRequestDto));
    }
    @PreAuthorize("hasAuthority('ALL_GROUPS') or hasRole('SUPER_ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<List<GroupResponseDto>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ){
        return ResponseEntity.ok(groupService.getAll(page,size));
    }
    @PreAuthorize("hasAuthority('ADD_STUDENT_GROUP')")
    @PostMapping("/add-student/{groupId}/{studentId}")
    public ResponseEntity<String> addStudentToGroup(
            @PathVariable UUID groupId,
            @PathVariable UUID studentId

            ){
        return new ResponseEntity<>(groupService.addStudentToGroup(groupId,studentId), HttpStatus.CREATED);
    }
    @PreAuthorize("hasAuthority('START_GROUP')")
    @PutMapping("/start-group/{id}")
    public ResponseEntity<String> startGroup(@PathVariable UUID id){
        groupService.startGroup(id);
        return ResponseEntity.ok("Successfully started group");
    }
    @PreAuthorize("hasAuthority('FINISH_GROUP')")
    @PutMapping("/finish-group/{id}")
    public ResponseEntity<String> finishGroup(@PathVariable UUID id){
        groupService.finishGroup(id);
        return ResponseEntity.ok("Successfully finished group");
    }
    @PreAuthorize("hasAnyRole('MENTOR','ADMIN') and hasAuthority('GROUP_STUDENTS') or hasRole('SUPER_ADMIN') ")
    @GetMapping("/mentor-groups/{mentorId}")
    public ResponseEntity<List<GroupResponseDto>> mentorsGroups(
            @PathVariable UUID mentorId,
            @RequestParam(required = false,defaultValue = "IN_PROGRESS")GroupStatus groupStatus
            ){
        return new ResponseEntity<>(groupService.getMentorsGroup(mentorId,groupStatus),HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('STUDENT_GROUP') or hasRole('SUPER_ADMIN')")
    @GetMapping("/student-groups/{studentId}")
    public ResponseEntity<List<GroupResponseDto>> studentGroups(
            @PathVariable UUID studentId,
            @RequestParam(required = false,defaultValue = "IN_PROGRESS")GroupStatus groupStatus
            ){
        return new ResponseEntity<>(groupService.getStudentGroups(studentId,groupStatus),HttpStatus.OK);
    }
}

