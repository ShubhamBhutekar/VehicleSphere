package com.vehms.controller;

import com.vehms.dto.ResidentOnlyResponse;
import com.vehms.dto.VehicleResponse;
import com.vehms.dto.VehicleStandaloneCreateRequest;
import com.vehms.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@Tag(name = "Vehicle", description = "APIs for managing vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    // TICKET: VEHMS-M01-T015
    @Operation(summary = "Register a vehicle to an existing resident")
    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(@Valid @RequestBody VehicleStandaloneCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.createVehicle(request));
    }

    // TICKET: VEHMS-M01-T017
    @Operation(summary = "Get resident details by vehicle registration number",
               description = "Registration number must be exactly 10 characters. Returns resident details only.")
    @GetMapping("/search")
    public ResponseEntity<ResidentOnlyResponse> getResidentByRegistrationNumber(
            @Parameter(description = "10-character vehicle registration number, e.g. MH12BA1234")
            @RequestParam String registrationNumber) {
        return ResponseEntity.ok(vehicleService.getResidentByRegistrationNumber(registrationNumber));
    }
}
