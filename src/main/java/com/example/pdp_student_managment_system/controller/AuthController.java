package com.example.pdp_student_managment_system.controller;

import com.example.pdp_student_managment_system.dto.ExceptionDto;
import com.example.pdp_student_managment_system.dto.LoginDto;
import com.example.pdp_student_managment_system.dto.jwt.JwtResponse;
import com.example.pdp_student_managment_system.dto.user.UserRequestDto;
import com.example.pdp_student_managment_system.dto.user.UserResponseDto;
import com.example.pdp_student_managment_system.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/register")
    @Operation(
            description = "This is a register endpoint",
            method = "POST",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = UserRequestDto.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            description = "This response means that a new user has been created",
                            responseCode = "201",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "This is because it has not been authorized yet",
                            responseCode = "401",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "This is forbidden",
                            responseCode = "403",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "This is bad request. \n" +
                                    "this is for a data entry error",
                            responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionDto.class)
                            )
                    )
            },
            security = @SecurityRequirement(
                    name = "bearerAuth"
            )
    )
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto userRequestDto){
        return new ResponseEntity<>(authService.register(userRequestDto), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    @Operation(summary = "This endpoint for login user",
            method = "POST",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LoginDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            description = "For successful user login",
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = JwtResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "This is because it has not been authorized yet",
                            responseCode = "401",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "This is bad request. \n" +
                                    "this is for a data entry error",
                            responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionDto.class)
                            )
                    )

            }
    )
    public ResponseEntity<JwtResponse> login( @RequestBody LoginDto loginDto){
       return ResponseEntity.ok(authService.login(loginDto));
    }

}
