package com.vehms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vehms.entity.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleCreateRequest {

    @NotBlank(message = "registrationNumber is mandatory")
    private String registrationNumber;

    @NotBlank(message = "vName is mandatory")
    @JsonProperty("vName")
    private String vName;

    private String color;

    @NotNull(message = "type is mandatory (CAR/MOPED/BIKE)")
    private VehicleType type;
}