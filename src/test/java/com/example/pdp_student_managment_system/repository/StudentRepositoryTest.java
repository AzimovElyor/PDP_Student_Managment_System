package com.example.pdp_student_managment_system.repository;

import com.example.pdp_student_managment_system.entity.Student;
import com.example.pdp_student_managment_system.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@DataJdbcTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;
    @Test
    void save(){
        Student student =
                new Student("Elyor", "Azimov", "994724134", UserRole.STUDENT, LocalDate.of(2005, 2, 12), true);
        Student save = studentRepository.save(student);
        System.out.println(student);
        assertNotNull(save);
    }

}