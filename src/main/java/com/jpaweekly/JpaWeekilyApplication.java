package com.jpaweekly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JpaWeekilyApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaWeekilyApplication.class, args);
    }

}
