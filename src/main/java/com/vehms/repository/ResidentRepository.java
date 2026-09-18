package com.vehms.repository;

import com.vehms.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResidentRepository extends JpaRepository<Resident, Long> {

    boolean existsByEmail(String email);

    // TICKET: VEHMS-M01-T012 - search by first, last, or both (case-insensitive)
    @Query("SELECT r FROM Resident r WHERE " +
           "(:fName IS NULL OR LOWER(r.fName) = LOWER(:fName)) AND " +
           "(:lName IS NULL OR LOWER(r.lName) = LOWER(:lName))")
    List<Resident> searchByName(@Param("fName") String fName, @Param("lName") String lName);
}
