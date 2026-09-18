package com.vehms.dto;

import com.vehms.entity.enums.VisitorType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// TICKET: VEHMS-M02-T020
// timeIn and isActiveVisitor are set by the server - not accepted from the client.
@Data
public class VisitorCreateRequest {

    @NotNull(message = "residentId is mandatory - visitor must be mapped to a resident")
    private Long residentId;

    @NotBlank(message = "visitorName is mandatory")
    private String visitorName;

    private String vehicleName;

    private String vehicleRegistrationNumber;

    @NotBlank(message = "visitPurpose is mandatory")
    private String visitPurpose;

    @NotNull(message = "phoneNumber is mandatory")
    private Long phoneNumber;

    @NotNull(message = "visitorType is mandatory (GUEST/DELIVERY)")
    private VisitorType visitorType;
}
