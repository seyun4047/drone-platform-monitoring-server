package com.mutzin.droneplatformmonitoringserver.service;

import com.mutzin.droneplatformmonitoringserver.domain.Drone;
import com.mutzin.droneplatformmonitoringserver.logging.LogAppender;
import com.mutzin.droneplatformmonitoringserver.repository.DroneRepository;
import com.mutzin.droneplatformmonitoringserver.repository.RedisHeartbeatRepository;
import com.mutzin.droneplatformmonitoringserver.repository.RedisTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class DroneMonitorService {

    private final DroneRepository droneRepository;
    private final RedisTokenRepository redisTokenRepository;
    private final RedisHeartbeatRepository redisHeartbeatRepository;

    @Transactional
    public void checkDisconnectedDrones(int timeoutSeconds, String logPath) {

        // 1. FIND TIMEOUT DRONES IN REDIS ZSET(HEARTBEAT)
        Set<String> timeoutSerials =
                redisHeartbeatRepository.findTimeoutDrones(timeoutSeconds);

        if (timeoutSerials == null || timeoutSerials.isEmpty()) {
            return;
        }

        // 2. FILTER DRONES THAT ACTUALLY EXIST IN THE DB
        List<Drone> disconnected =
                droneRepository.findBySerialIn(timeoutSerials);

        if (disconnected.isEmpty()) {
            return;
        }

        // 3. REMOVE AUTH TOKEN FROM REDIS
        disconnected.forEach(d ->
                redisTokenRepository.deleteBySerial(d.getSerial())
        );

        // 4. UPDATE DB STATUS TO DISCONNECTED (SET OFFLINE)
        droneRepository.updateStatusToDisconnected(timeoutSerials);

        // REMOVE DRONES FROM REDIS HEARTBEAT ZSET
        timeoutSerials.forEach(redisHeartbeatRepository::remove);

        // APPEND LOG
        disconnected.forEach(d -> {
            String message = "X DRONE DISCONNECTED: " + d.getSerial();
            log.info(message);
            LogAppender.prepend(logPath, message);
        });
    }

    @Transactional
    public void resetAllDronesToDisconnected() {
        int updated = droneRepository.resetAllConnections();
        log.info("Reset all drones to disconnected. count={}", updated);
    }

}
