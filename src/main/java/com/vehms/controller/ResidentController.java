package com.vehms.controller;

import com.vehms.dto.ResidentCreateRequest;
import com.vehms.dto.ResidentResponse;
import com.vehms.service.ResidentService;
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
@RequestMapping("/api/residents")
@RequiredArgsConstructor
@Tag(name = "Resident", description = "APIs for managing society residents and their vehicles")
public class ResidentController {

    private final ResidentService residentService;

    // TICKET: VEHMS-M01-T007
    @Operation(summary = "Create a resident", description = "Creates a resident, optionally along with a list of vehicles owned by them.")
    @PostMapping
    public ResponseEntity<ResidentResponse> createResident(@Valid @RequestBody ResidentCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(residentService.createResident(request));
    }

    // TICKET: VEHMS-M01-T010
    @Operation(summary = "Get all residents", description = "Returns every resident along with their associated vehicles.")
    @GetMapping
    public ResponseEntity<List<ResidentResponse>> getAllResidents() {
        return ResponseEntity.ok(residentService.getAllResidents());
    }

    // TICKET: VEHMS-M01-T012
    @Operation(summary = "Search residents by name", description = "Search by first name, last name, or both. Numeric values are not allowed.")
    @GetMapping("/search")
    public ResponseEntity<List<ResidentResponse>> searchByName(
            @Parameter(description = "Resident's first name") @RequestParam(required = false) String fName,
            @Parameter(description = "Resident's last name") @RequestParam(required = false) String lName) {
        return ResponseEntity.ok(residentService.searchByName(fName, lName));
    }
}
