package com.mutzin.droneplatformmonitoringserver.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;

@Component
@Slf4j
public class RedisHeartbeatRepository {

    private static final String HEARTBEAT_ZSET = "drone:heartbeat";

    private final StringRedisTemplate redisTemplate;

    public RedisHeartbeatRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Remove heartbeat (called on disconnect or timeout cleanup).
     */
    public void remove(String serial) {
        redisTemplate.opsForZSet()
                .remove(HEARTBEAT_ZSET, serial);
    }

    /**
     * Find drones that exceeded heartbeat timeout.
     * Used by monitoring server.
     */
    public Set<String> findTimeoutDrones(long timeoutSeconds) {
        long threshold = Instant.now().getEpochSecond() - timeoutSeconds;

        return redisTemplate.opsForZSet()
                .rangeByScore(HEARTBEAT_ZSET, 0, threshold);
    }
}
