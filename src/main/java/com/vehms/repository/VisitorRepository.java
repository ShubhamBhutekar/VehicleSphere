package com.vehms.repository;

import com.vehms.entity.Visitor;
import com.vehms.entity.enums.VisitorType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    // TICKET: VEHMS-M02-T022 - most recent visit record for a given vehicle
    Optional<Visitor> findFirstByVehicleRegistrationNumberIgnoreCaseOrderByTimeInDesc(String vehicleRegistrationNumber);

    // TICKET: VEHMS-M02-T024 - the visitor currently inside (not yet exited) with this vehicle
    Optional<Visitor> findFirstByVehicleRegistrationNumberIgnoreCaseAndIsActiveVisitorTrue(String vehicleRegistrationNumber);

    // TICKET: VEHMS-M02-T026 - no filter -> all active visitors
    List<Visitor> findByIsActiveVisitorTrue();

    // TICKET: VEHMS-M02-T026 - filtered by one or both types
    List<Visitor> findByIsActiveVisitorTrueAndVisitorTypeIn(List<VisitorType> types);

    // TICKET: VEHMS-M02-T031 - all visits that started within the given day
    List<Visitor> findByTimeInBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
