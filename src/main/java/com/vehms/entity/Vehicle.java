package com.vehms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vehms.entity.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// TICKET: VEHMS-M01-T005 / T006
// NOTE: spelling corrected from the spec's "Vehical"/"registerationNumber" to
// proper English (Vehicle / registrationNumber). Ticket IDs are unaffected.
@Entity
@Table(name = "vehicle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String registrationNumber; // e.g. MH12BA1234

    @Column(nullable = false)
    private String vName;

    private String color;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;

    @Column(nullable = false, updatable = false)
    private LocalDateTime associationActivatedAt;

    private LocalDateTime associationDeactivatedAt;

    @Column(nullable = false)
    private boolean isVehicleActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id", nullable = false)
    @JsonBackReference
    private Resident resident;
}
