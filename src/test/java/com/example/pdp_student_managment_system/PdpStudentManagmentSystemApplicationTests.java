package com.example.pdp_student_managment_system;

import com.example.pdp_student_managment_system.config.HibernateConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        HibernateConfig.class})
@ActiveProfiles("elyor")
class PdpStudentManagmentSystemApplicationTests {

    @Test
    void contextLoads() {
    }

}
