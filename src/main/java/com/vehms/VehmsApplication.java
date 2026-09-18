package com.vehms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// TICKET: VEHMS-M01-T001
// @EnableScheduling added for VEHMS-M02-T031 (nightly visitor backup)
@SpringBootApplication
@EnableScheduling
public class VehmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(VehmsApplication.class, args);
    }
}
