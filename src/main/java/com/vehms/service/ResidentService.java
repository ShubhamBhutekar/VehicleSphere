package com.vehms.service;

import com.vehms.dto.ResidentCreateRequest;
import com.vehms.dto.ResidentResponse;

import java.util.List;

public interface ResidentService {
    ResidentResponse createResident(ResidentCreateRequest request);
    List<ResidentResponse> getAllResidents();
    List<ResidentResponse> searchByName(String fName, String lName);
}
