package com.example.pdp_student_managment_system.exceptionHandling;

import com.example.pdp_student_managment_system.exception.DataNotFoundException;
import com.example.pdp_student_managment_system.exception.InCorrectPermissionsException;
import com.example.pdp_student_managment_system.exception.NoVerificationException;
import com.example.pdp_student_managment_system.util.MessageConstants;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.pdp_student_managment_system.dto.ExceptionDto;
import org.springframework.web.servlet.NoHandlerFoundException;


import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
@ControllerAdvice
public class ExceptionHandling {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ExceptionDto> runTimeException(RuntimeException exception){
        ExceptionDto exceptionDto = new ExceptionDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionDto,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionDto> validatorException(ConstraintViolationException exception){
        ExceptionDto exceptionDto = new ExceptionDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionDto,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ExceptionDto>> handleBindException(BindException ex) {
        ArrayList<ExceptionDto> exceptionDtos = new ArrayList<>();
        StringBuilder errorMessage = new     StringBuilder();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMessage.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage());
            exceptionDtos.add(new ExceptionDto(errorMessage.toString(),HttpStatus.BAD_REQUEST.value()));
            errorMessage = new StringBuilder();
        }
        return new ResponseEntity<>(exceptionDtos,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionDto> handlerUserNotFoundException(UsernameNotFoundException exception){
        ExceptionDto exceptionDto = new ExceptionDto(exception.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(exceptionDto,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ExceptionDto> handlerDataNotFound(DataNotFoundException exception){
        ExceptionDto exceptionDto = new ExceptionDto(exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionDto,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NoVerificationException.class)
    public ResponseEntity<ExceptionDto> verificationExceptionHandling(NoVerificationException exception){
        ExceptionDto exceptionDto = new ExceptionDto(exception.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(exceptionDto,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(InCorrectPermissionsException.class)
    public ResponseEntity<ExceptionDto> permissionExceptionHandling(InCorrectPermissionsException exception){
        ExceptionDto exceptionDto = new ExceptionDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionDto,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDto> accessDeniedException(AccessDeniedException e){
        ExceptionDto exceptionDto = new ExceptionDto(MessageConstants.DO_NOT_HAVE_PERMISSION_TO_USE_THIS_WAY, HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(exceptionDto,HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseEntity<?> handleNoEndpoint(NoHandlerFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionDto,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> handleMethodException(HttpRequestMethodNotSupportedException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(MessageConstants.METHOD_ERROR, HttpStatus.METHOD_NOT_ALLOWED.value());
        return new ResponseEntity<>(exceptionDto,HttpStatus.METHOD_NOT_ALLOWED);
    }
}
