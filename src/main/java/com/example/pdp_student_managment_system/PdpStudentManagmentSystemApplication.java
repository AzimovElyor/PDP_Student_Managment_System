package com.example.pdp_student_managment_system;

import com.example.pdp_student_managment_system.dto.sending.MessageDto;
import com.example.pdp_student_managment_system.dto.user.UserRequestDto;
import com.example.pdp_student_managment_system.dto.user.UserResponseDto;
import com.example.pdp_student_managment_system.enums.Permissions;
import com.example.pdp_student_managment_system.enums.UserRole;
import com.example.pdp_student_managment_system.service.AuthService;
import com.example.pdp_student_managment_system.service.SMSService;
import com.example.pdp_student_managment_system.service.SendingService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@SpringBootApplication
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER,
        bearerFormat = "JWT"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Title of project",
                description = "This is for erp project",
                contact = @Contact(
                        name = "ElyorAzimov",
                        url ="https://t.me/elyorazimov",
                        email = "elyorazimov098@gmail.com"
                )
        ),
         servers = @Server(
                 url = "http://localhost:9090",
                 description = "This is local server"
         )
)
public class PdpStudentManagmentSystemApplication {


    public static void main(String[] args) {
        SpringApplication.run(PdpStudentManagmentSystemApplication.class, args);
        System.out.println(LocalTime.now());
        System.out.println(LocalDateTime.now());

    }
    /*@Bean*/
    public CommandLineRunner commandLineRunner(AuthService authService){

        return args -> {
            UserRequestDto user = UserRequestDto.builder()
                    .name("Elyor")
                    .surname("Azimov")
                    .password("123456")
                    .email("elyorazimov098@gmail.com")
                    .phoneNumber("994724134")
                    .role(UserRole.SUPER_ADMIN)
                    .permissions(Set.of())
                    .build();
            authService.register(user);
        };
    }




}
