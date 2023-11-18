package com.example.pdp_student_managment_system.service;

import com.example.pdp_student_managment_system.dto.LoginDto;
import com.example.pdp_student_managment_system.dto.jwt.JwtResponse;
import com.example.pdp_student_managment_system.dto.user.UserRequestDto;
import com.example.pdp_student_managment_system.dto.user.UserResponseDto;
import com.example.pdp_student_managment_system.entity.UserEntity;
import com.example.pdp_student_managment_system.enums.Permissions;
import com.example.pdp_student_managment_system.enums.UserRole;
import com.example.pdp_student_managment_system.exception.InCorrectPermissionsException;
import com.example.pdp_student_managment_system.exception.NoVerificationException;
import com.example.pdp_student_managment_system.jwt.JwtService;
import com.example.pdp_student_managment_system.repository.UserRepository;
import com.example.pdp_student_managment_system.util.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);


    public UserResponseDto register(UserRequestDto userRequestDto) {
        Boolean existEmail = userRepository.existsByEmail(userRequestDto.getEmail());
        if(existEmail){
            throw new RuntimeException(MessageConstants.EMAIL_EXISTS.formatted(userRequestDto.getEmail()));
        }
        if(!isPermissionsValid(userRequestDto.getRole(),userRequestDto.getPermissions())){
            throw new InCorrectPermissionsException(MessageConstants.INCORRECT_PERMISSIONS);
        }
        if(userRequestDto.getRole()==UserRole.MENTOR && (userRequestDto.getLanguages() == null
                || userRequestDto.getLanguages().isEmpty() ))
            throw new RuntimeException(MessageConstants.MENTOR_LANGUAGE);

        executorService.execute(() -> emailService.send(userRequestDto.getEmail()));

        UserEntity user = modelMapper.map(userRequestDto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        UserResponseDto response = modelMapper.map(user, UserResponseDto.class);
        response.setFullName(user.getName() + " " + user.getSurname());
        return response;
    }
    private boolean isPermissionsValid(UserRole userRole, Set<Permissions> permissions){
        for (Permissions permission : permissions) {
            if(!permission.getRoles().contains(userRole)) return false;
        }
        return true;
    }

    public JwtResponse login(LoginDto loginDto) {
        UserEntity user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(MessageConstants.USER_NOT_FOUND));
        if(!passwordEncoder.matches(loginDto.getPassword(),user.getPassword())){
            throw new UsernameNotFoundException(MessageConstants.USER_NOT_FOUND);
        }
        if(!user.getIsActive()){
            throw new RuntimeException(MessageConstants.USER_BLOCK);
        }
        if(!user.getIsVerification()){
            throw new NoVerificationException(MessageConstants.NO_VERIFY);
        }
        return new JwtResponse(jwtService.generateToken(user));

    }
}
