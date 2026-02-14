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

    @Modifying
    @Query("""
            UPDATE Drone d
            SET d.connecting = false
            WHERE d.serial = :serial
        """)
    int updateDisconnectedDrone(@Param("serial") String serial);
}
