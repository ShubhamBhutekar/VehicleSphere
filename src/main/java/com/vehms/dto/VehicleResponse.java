package com.vehms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vehms.entity.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponse {
    private Long id;
    private String registrationNumber;
    @JsonProperty("vName")
    private String vName;
    private String color;
    private VehicleType type;
    private LocalDateTime associationActivatedAt;
    private boolean isVehicleActive;
}