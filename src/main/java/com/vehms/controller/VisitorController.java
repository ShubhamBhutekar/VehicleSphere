package com.vehms.controller;

import com.vehms.dto.VisitorCreateRequest;
import com.vehms.dto.VisitorResponse;
import com.vehms.entity.enums.VisitorType;
import com.vehms.service.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitors")
@RequiredArgsConstructor
@Tag(name = "Visitor", description = "APIs for managing society visitors (guests and deliveries)")
public class VisitorController {

    private final VisitorService visitorService;

    // TICKET: VEHMS-M02-T020
    @Operation(summary = "Log a new visitor", description = "Creates a visitor entry mapped to an existing resident.")
    @PostMapping
    public ResponseEntity<VisitorResponse> createVisitor(@Valid @RequestBody VisitorCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(visitorService.createVisitor(request));
    }

    // TICKET: VEHMS-M02-T022
    @Operation(summary = "Get resident + visitor details by vehicle registration number",
               description = "Returns only resident and visitor details - no extra vehicle data.")
    @GetMapping("/search")
    public ResponseEntity<VisitorResponse> getByRegistrationNumber(
            @Parameter(description = "Visitor's vehicle registration number") @RequestParam String vehicleRegistrationNumber) {
        return ResponseEntity.ok(visitorService.getByVehicleRegistrationNumber(vehicleRegistrationNumber));
    }

    // TICKET: VEHMS-M02-T024
    @Operation(summary = "Mark a visitor's exit", description = "Sets exit time and computes visit duration (HH:MM) for the currently active visit matching this vehicle.")
    @PatchMapping("/exit")
    public ResponseEntity<VisitorResponse> markExit(
            @Parameter(description = "Visitor's vehicle registration number") @RequestParam String vehicleRegistrationNumber) {
        return ResponseEntity.ok(visitorService.markExit(vehicleRegistrationNumber));
    }

    // TICKET: VEHMS-M02-T026
    @Operation(summary = "Get currently active visitors",
               description = "Optionally filter by visitorType (GUEST, DELIVERY, or both). No filter returns all active visitors.")
    @GetMapping("/active")
    public ResponseEntity<List<VisitorResponse>> getActiveVisitors(
            @Parameter(description = "Filter by one or both types") @RequestParam(required = false) List<VisitorType> visitorType) {
        return ResponseEntity.ok(visitorService.getActiveVisitors(visitorType));
    }
}
