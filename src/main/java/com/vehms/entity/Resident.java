package com.vehms.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vehms.entity.enums.ResidentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// TICKET: VEHMS-M01-T005 / T006
@Entity
@Table(name = "resident")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fName;

    private String lName;

    @Column(nullable = false)
    private String flatNo; // format: A-123, B-23

    @Column(nullable = false)
    private Long mobileNo;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResidentType residentType;

    // one-to-many mapping with vehicles
    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<Vehicle> vehicleList = new ArrayList<>();

    // one-to-many mapping with visitors (Module 2)
    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<Visitor> visitorList = new ArrayList<>();
}
