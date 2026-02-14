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

        Set<String> timeoutSerials =
                redisHeartbeatRepository.findTimeoutDrones();

        if (timeoutSerials.isEmpty()) return;

//        REMOVE TOKEN AND SET DISCONNECT WITH TIMEOUT SERIALS
        timeoutSerials.forEach(serial -> {
            redisTokenRepository.deleteBySerial(serial);
            droneRepository.updateDisconnectedDrone(serial);

            String message = "X DRONE DISCONNECTED: " + serial;
            log.info(message);
            LogAppender.prepend(logPath, message);

            redisHeartbeatRepository.remove(serial);
        });
    }

}
