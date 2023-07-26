package org.programmers.jpaweeklymission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JpaWeeklyMissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaWeeklyMissionApplication.class, args);
    }

}
