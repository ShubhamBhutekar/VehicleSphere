package com.vehms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vehms.entity.enums.ResidentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ResidentCreateRequest {

    @NotBlank(message = "fName is mandatory")
    @JsonProperty("fName")
    private String fName;

    @JsonProperty("lName")
    private String lName;

    @NotBlank(message = "flatNo is mandatory")
    private String flatNo;

    @NotNull(message = "mobileNo is mandatory")
    private Long mobileNo;

    @NotBlank(message = "email is mandatory")
    @Email(message = "email must be a valid email address")
    private String email;

    @NotNull(message = "residentType is mandatory (TENANT/OWNER)")
    private ResidentType residentType;

    @Valid
    private List<VehicleCreateRequest> vehicles;
}