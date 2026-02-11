package com.mutzin.droneplatformmonitoringserver.repository;

import com.mutzin.droneplatformmonitoringserver.domain.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DroneRepository extends JpaRepository<Drone, Long> {

    Optional<Drone> findBySerial(String serial);

    // FIND DRONE WITH ENTIRE SERIALS
    List<Drone> findBySerialIn(Set<String> serials);

    // SET OFFLINE
    @Modifying
    @Query("""
        UPDATE Drone d
        SET d.connecting = false
        WHERE d.serial IN :serials
          AND d.connecting = true
    """)
    int updateStatusToDisconnected(@Param("serials") Set<String> serials);

    @Modifying
    @Query("""
    UPDATE Drone d
    SET d.connecting = false
    WHERE d.connecting = true
    """)
    int resetAllConnections();
}
