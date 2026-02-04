package com.mutzin.droneplatformmonitoringserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DronePlatformMonitoringServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DronePlatformMonitoringServerApplication.class, args);
    }

}
