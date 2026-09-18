package com.vehms.repository;

import com.vehms.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    boolean existsByRegistrationNumber(String registrationNumber);

    // TICKET: VEHMS-M01-T017
    Optional<Vehicle> findByRegistrationNumberIgnoreCase(String registrationNumber);
}
