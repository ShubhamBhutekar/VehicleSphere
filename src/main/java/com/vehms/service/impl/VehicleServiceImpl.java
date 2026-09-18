package com.vehms.service.impl;

import com.vehms.dto.ResidentOnlyResponse;
import com.vehms.dto.VehicleResponse;
import com.vehms.dto.VehicleStandaloneCreateRequest;
import com.vehms.entity.Resident;
import com.vehms.entity.Vehicle;
import com.vehms.exception.InvalidInputException;
import com.vehms.exception.ResourceNotFoundException;
import com.vehms.repository.ResidentRepository;
import com.vehms.repository.VehicleRepository;
import com.vehms.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private static final int REQUIRED_REG_NUMBER_LENGTH = 10;

    private final VehicleRepository vehicleRepository;
    private final ResidentRepository residentRepository;

    // TICKET: VEHMS-M01-T015
    @Override
    @Transactional
    public VehicleResponse createVehicle(VehicleStandaloneCreateRequest request) {

        // "a resident should be present with it else it should not be saved"
        Resident resident = residentRepository.findById(request.getResidentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resident with id " + request.getResidentId() + " does not exist. Vehicle was not saved."));

        if (vehicleRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
            throw new InvalidInputException("A vehicle with this registration number already exists");
        }

        Vehicle vehicle = Vehicle.builder()
                .registrationNumber(request.getRegistrationNumber())
                .vName(request.getVName())
                .color(request.getColor())
                .type(request.getType())
                .associationActivatedAt(LocalDateTime.now())
                .isVehicleActive(true)
                .resident(resident)
                .build();

        Vehicle saved = vehicleRepository.save(vehicle);

        return VehicleResponse.builder()
                .id(saved.getId())
                .registrationNumber(saved.getRegistrationNumber())
                .vName(saved.getVName())
                .color(saved.getColor())
                .type(saved.getType())
                .associationActivatedAt(saved.getAssociationActivatedAt())
                .isVehicleActive(saved.isVehicleActive())
                .build();
    }

    // TICKET: VEHMS-M01-T017
    @Override
    public ResidentOnlyResponse getResidentByRegistrationNumber(String registrationNumber) {
        if (registrationNumber == null || registrationNumber.length() != REQUIRED_REG_NUMBER_LENGTH) {
            throw new InvalidInputException(
                    "Invalid registration number: it must be exactly " + REQUIRED_REG_NUMBER_LENGTH + " characters long");
        }

        Vehicle vehicle = vehicleRepository.findByRegistrationNumberIgnoreCase(registrationNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No vehicle found with registration number " + registrationNumber));

        Resident resident = vehicle.getResident();
        return ResidentOnlyResponse.builder()
                .id(resident.getId())
                .fName(resident.getFName())
                .lName(resident.getLName())
                .flatNo(resident.getFlatNo())
                .mobileNo(resident.getMobileNo())
                .email(resident.getEmail())
                .residentType(resident.getResidentType())
                .build();
    }
}
