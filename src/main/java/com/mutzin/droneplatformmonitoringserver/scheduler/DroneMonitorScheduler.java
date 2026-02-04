package com.mutzin.droneplatformmonitoringserver.scheduler;

import com.mutzin.droneplatformmonitoringserver.logging.LogAppender;
import com.mutzin.droneplatformmonitoringserver.logging.LogPathCreator;
import com.mutzin.droneplatformmonitoringserver.service.DroneMonitorService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DroneMonitorScheduler {
    private final DroneMonitorService droneMonitorService;
    private final LogPathCreator logPathCreator;

    @Value("${drone.ttl}")
    private int droneTTL;

    private String logPath;
    @PostConstruct
    public void init() {
        this.logPath = logPathCreator.create().toString();
        LogAppender.prepend(logPath, "Drone monitor service initialized");
        log.info("Drone monitor service initialized");
    }
    @Scheduled(fixedDelayString = "${monitor.sut}")
    public void startDroneMonitor() {
        log.info("🟢 Drone monitor running");
        try {
            droneMonitorService.checkDisconnectedDrones(droneTTL, logPath);
        } catch (Exception e) {
            LogAppender.prepend(logPath, "X Drone monitor error" + e);
            log.error("X Drone monitor error", e);
        }
    }
}
