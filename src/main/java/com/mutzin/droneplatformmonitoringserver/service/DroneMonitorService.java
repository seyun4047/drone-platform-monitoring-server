package com.mutzin.droneplatformmonitoringserver.service;

import com.mutzin.droneplatformmonitoringserver.domain.Drone;
import com.mutzin.droneplatformmonitoringserver.logging.LogAppender;
import com.mutzin.droneplatformmonitoringserver.repository.DroneRepository;
import com.mutzin.droneplatformmonitoringserver.repository.RedisTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DroneMonitorService {

    private final DroneRepository droneRepository;
    private final RedisTokenRepository redisTokenRepository;

    @Transactional
    public void checkDisconnectedDrones(int timeoutSeconds, String logPath) {

        LocalDateTime threshold =
                LocalDateTime.now().minusSeconds(timeoutSeconds);

        List<Drone> disconnected =
                droneRepository.findDisconnectedDrones(threshold);

        if (disconnected.isEmpty()) return;

        // Token expire
        disconnected.forEach(d ->
                redisTokenRepository.deleteBySerial(d.getSerial())
        );

        // state update
        droneRepository.updateDisconnectedDrones(threshold);
        // get log
        disconnected.forEach(d -> {
            String message = "X DRONE DISCONNECTED: " + d.getSerial();
            log.info(message);
            LogAppender.prepend(logPath, message);
        });

    }
}
