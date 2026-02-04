package com.mutzin.droneplatformmonitoringserver.repository;

import com.mutzin.droneplatformmonitoringserver.domain.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    Optional<Drone> findBySerial(String serial);

    @Query("""
        SELECT d
        FROM Drone d
        WHERE d.connecting = true
          AND d.updatedAt < :threshold
    """)
    List<Drone> findDisconnectedDrones(@Param("threshold") LocalDateTime threshold);

    @Modifying
    @Query("""
        UPDATE Drone d
        SET d.connecting = false
        WHERE d.connecting = true
          AND d.updatedAt < :threshold
    """)
    int updateDisconnectedDrones(@Param("threshold") LocalDateTime threshold);
}