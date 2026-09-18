package com.vehms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vehms.entity.enums.VisitorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// TICKET: VEHMS-M01-T005 (entity only - APIs are Module 2, T020+)
@Entity
@Table(name = "visitor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String visitorName;

    private String vehicleName;

    private String vehicleRegistrationNumber;

    private String visitPurpose;

    private LocalDateTime timeIn;

    private LocalDateTime timeOut;

    private Long phoneNumber;

    private boolean isActiveVisitor;

    @Enumerated(EnumType.STRING)
    private VisitorType visitorType;

    // TICKET: VEHMS-M02-T028 - formatted as HH:MM, computed when timeOut is set
    private String visitDuration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id")
    @JsonBackReference
    private Resident resident;
}
