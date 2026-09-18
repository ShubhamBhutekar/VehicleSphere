package com.vehms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vehms.entity.enums.ResidentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResidentOnlyResponse {
    private Long id;
    @JsonProperty("fName")
    private String fName;
    @JsonProperty("lName")
    private String lName;
    private String flatNo;
    private Long mobileNo;
    private String email;
    private ResidentType residentType;
}