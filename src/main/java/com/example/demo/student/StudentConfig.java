package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student pszemo = new Student(
                    "Pszemo",
                    "pszemo@gmail.com",
                    LocalDate.of(2000, 1, 1)
            );

            Student mateo = new Student(
                    "Mateo",
                    "mateo@gmail.com",
                    LocalDate.of(1998, 2, 2)
            );

            repository.saveAll(
                    List.of(pszemo, mateo)
            );
        };
    }
}
