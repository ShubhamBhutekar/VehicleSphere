package com.vehms.service;

import com.vehms.dto.VisitorCreateRequest;
import com.vehms.dto.VisitorResponse;
import com.vehms.entity.enums.VisitorType;

import java.util.List;

public interface VisitorService {
    VisitorResponse createVisitor(VisitorCreateRequest request);
    VisitorResponse getByVehicleRegistrationNumber(String vehicleRegistrationNumber);
    VisitorResponse markExit(String vehicleRegistrationNumber);
    List<VisitorResponse> getActiveVisitors(List<VisitorType> types);
}
