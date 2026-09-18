package com.vehms.dto;

import com.vehms.entity.enums.VisitorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// TICKET: VEHMS-M02-T020 / T022
// T022 requires "only resident and visitor details" - so this carries the resident's
// name and flat number directly, rather than nesting a full ResidentResponse
// (which would drag in the resident's whole vehicle list).
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitorResponse {
    private Long id;
    private String visitorName;
    private String vehicleName;
    private String vehicleRegistrationNumber;
    private String visitPurpose;
    private LocalDateTime timeIn;
    private LocalDateTime timeOut;
    private Long phoneNumber;
    private boolean isActiveVisitor;
    private VisitorType visitorType;
    private String visitDuration;

    // resident context, flattened per T022
    private String residentName;
    private String flatNo;
}
