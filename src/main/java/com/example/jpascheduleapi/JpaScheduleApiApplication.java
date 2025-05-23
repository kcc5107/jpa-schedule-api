package com.example.jpascheduleapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JpaScheduleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaScheduleApiApplication.class, args);
    }

}
