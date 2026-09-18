package com.vehms.service;

import com.vehms.dto.ResidentOnlyResponse;
import com.vehms.dto.VehicleResponse;
import com.vehms.dto.VehicleStandaloneCreateRequest;

public interface VehicleService {
    VehicleResponse createVehicle(VehicleStandaloneCreateRequest request);
    ResidentOnlyResponse getResidentByRegistrationNumber(String registrationNumber);
}
