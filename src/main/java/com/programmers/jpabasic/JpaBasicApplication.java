package com.programmers.jpabasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JpaBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaBasicApplication.class, args);
    }
}
