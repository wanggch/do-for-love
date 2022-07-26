package com.jasmine.doforlove;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class DoForLoveApplication {
    public static void main(String[] args) {
        SpringApplication.run(DoForLoveApplication.class, args);
        log.info("## do-for-love-application run successfully. ##");
    }
}
