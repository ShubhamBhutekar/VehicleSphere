package com.vehms.service.impl;

import com.vehms.dto.*;
import com.vehms.entity.Resident;
import com.vehms.entity.Vehicle;
import com.vehms.exception.InvalidInputException;
import com.vehms.exception.ResourceNotFoundException;
import com.vehms.repository.ResidentRepository;
import com.vehms.service.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResidentServiceImpl implements ResidentService {

    private final ResidentRepository residentRepository;

    // TICKET: VEHMS-M01-T007
    @Override
    @Transactional
    public ResidentResponse createResident(ResidentCreateRequest request) {

        Resident resident = Resident.builder()
                .fName(request.getFName())
                .lName(request.getLName())
                .flatNo(request.getFlatNo())
                .mobileNo(request.getMobileNo())
                .email(request.getEmail())
                .residentType(request.getResidentType())
                .build();

        List<Vehicle> vehicles = new ArrayList<>();
        if (request.getVehicles() != null) {
            for (VehicleCreateRequest v : request.getVehicles()) {
                Vehicle vehicle = Vehicle.builder()
                        .registrationNumber(v.getRegistrationNumber())
                        .vName(v.getVName())
                        .color(v.getColor())
                        .type(v.getType())
                        // time fields come from the server, never from user input
                        .associationActivatedAt(LocalDateTime.now())
                        .isVehicleActive(true)
                        .resident(resident)
                        .build();
                vehicles.add(vehicle);
            }
        }
        resident.setVehicleList(vehicles);

        Resident saved = residentRepository.save(resident);
        return toResponse(saved);
    }

    // TICKET: VEHMS-M01-T010
    @Override
    @Transactional(readOnly = true)
    public List<ResidentResponse> getAllResidents() {
        return residentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // TICKET: VEHMS-M01-T012
    @Override
    @Transactional(readOnly = true)
    public List<ResidentResponse> searchByName(String fName, String lName) {
        if (fName == null && lName == null) {
            throw new InvalidInputException("Provide at least firstname or lastname to search");
        }
        if (containsDigit(fName) || containsDigit(lName)) {
            throw new InvalidInputException("Name fields cannot contain numeric values");
        }

        List<Resident> results = residentRepository.searchByName(fName, lName);
        if (results.isEmpty()) {
            throw new ResourceNotFoundException("No resident found matching the given name");
        }
        return results.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private boolean containsDigit(String value) {
        return value != null && value.chars().anyMatch(Character::isDigit);
    }

    private ResidentResponse toResponse(Resident resident) {
        List<VehicleResponse> vehicleResponses = resident.getVehicleList() == null
                ? List.of()
                : resident.getVehicleList().stream()
                    .map(v -> VehicleResponse.builder()
                            .id(v.getId())
                            .registrationNumber(v.getRegistrationNumber())
                            .vName(v.getVName())
                            .color(v.getColor())
                            .type(v.getType())
                            .associationActivatedAt(v.getAssociationActivatedAt())
                            .isVehicleActive(v.isVehicleActive())
                            .build())
                    .collect(Collectors.toList());

        return ResidentResponse.builder()
                .id(resident.getId())
                .fName(resident.getFName())
                .lName(resident.getLName())
                .flatNo(resident.getFlatNo())
                .mobileNo(resident.getMobileNo())
                .email(resident.getEmail())
                .residentType(resident.getResidentType())
                .vehicles(vehicleResponses)
                .build();
    }
}
