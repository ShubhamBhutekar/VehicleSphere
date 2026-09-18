package com.vehms.service.impl;

import com.vehms.dto.VisitorCreateRequest;
import com.vehms.dto.VisitorResponse;
import com.vehms.entity.Resident;
import com.vehms.entity.Visitor;
import com.vehms.entity.enums.VisitorType;
import com.vehms.exception.ResourceNotFoundException;
import com.vehms.repository.ResidentRepository;
import com.vehms.repository.VisitorRepository;
import com.vehms.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;
    private final ResidentRepository residentRepository;

    // TICKET: VEHMS-M02-T020
    @Override

    public VisitorResponse createVisitor(VisitorCreateRequest request) {
        Resident resident = residentRepository.findById(request.getResidentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resident with id " + request.getResidentId() + " does not exist"));

        Visitor visitor = Visitor.builder()
                .visitorName(request.getVisitorName())
                .vehicleName(request.getVehicleName())
                .vehicleRegistrationNumber(request.getVehicleRegistrationNumber())
                .visitPurpose(request.getVisitPurpose())
                .phoneNumber(request.getPhoneNumber())
                .visitorType(request.getVisitorType())
                .timeIn(LocalDateTime.now())      // server-assigned, not user input
                .isActiveVisitor(true)
                .resident(resident)
                .build();

        Visitor saved = visitorRepository.save(visitor);
        return toResponse(saved);
    }

    // TICKET: VEHMS-M02-T022
    @Override
    @Transactional(readOnly = true)
    public VisitorResponse getByVehicleRegistrationNumber(String vehicleRegistrationNumber) {
        Visitor visitor = visitorRepository
                .findFirstByVehicleRegistrationNumberIgnoreCaseOrderByTimeInDesc(vehicleRegistrationNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No visitor record found for vehicle " + vehicleRegistrationNumber));
        return toResponse(visitor);
    }

    // TICKET: VEHMS-M02-T024
    @Override
    @Transactional
    public VisitorResponse markExit(String vehicleRegistrationNumber) {
        Visitor visitor = visitorRepository
                .findFirstByVehicleRegistrationNumberIgnoreCaseAndIsActiveVisitorTrue(vehicleRegistrationNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No active visitor found for vehicle " + vehicleRegistrationNumber));

        LocalDateTime now = LocalDateTime.now();
        visitor.setTimeOut(now);
        visitor.setActiveVisitor(false);
        visitor.setVisitDuration(formatDuration(visitor.getTimeIn(), now)); // TICKET: T028

        Visitor saved = visitorRepository.save(visitor);
        return toResponse(saved);
    }

    // TICKET: VEHMS-M02-T026
    @Override
    @Transactional(readOnly = true)
    public List<VisitorResponse> getActiveVisitors(List<VisitorType> types) {
        List<Visitor> visitors = (types == null || types.isEmpty())
                ? visitorRepository.findByIsActiveVisitorTrue()
                : visitorRepository.findByIsActiveVisitorTrueAndVisitorTypeIn(types);

        return visitors.stream().map(this::toResponse).collect(Collectors.toList());
    }

    // TICKET: VEHMS-M02-T028 - HH:MM format
    private String formatDuration(LocalDateTime timeIn, LocalDateTime timeOut) {
        if (timeIn == null || timeOut == null) return null;
        Duration duration = Duration.between(timeIn, timeOut);
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        return String.format("%02d:%02d", hours, minutes);
    }

    private VisitorResponse toResponse(Visitor visitor) {
        Resident resident = visitor.getResident();
        return VisitorResponse.builder()
                .id(visitor.getId())
                .visitorName(visitor.getVisitorName())
                .vehicleName(visitor.getVehicleName())
                .vehicleRegistrationNumber(visitor.getVehicleRegistrationNumber())
                .visitPurpose(visitor.getVisitPurpose())
                .timeIn(visitor.getTimeIn())
                .timeOut(visitor.getTimeOut())
                .phoneNumber(visitor.getPhoneNumber())
                .isActiveVisitor(visitor.isActiveVisitor())
                .visitorType(visitor.getVisitorType())
                .visitDuration(visitor.getVisitDuration())
                .residentName(resident != null ? (resident.getFName() + " " +
                        (resident.getLName() != null ? resident.getLName() : "")).trim() : null)
                .flatNo(resident != null ? resident.getFlatNo() : null)
                .build();
    }
}
