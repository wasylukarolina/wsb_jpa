package com.jpacourse.rest;

import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/{patientId}/visits")
    public ResponseEntity<String> addVisit(
            @PathVariable Long patientId,
            @RequestParam Long doctorId,
            @RequestParam String description,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime visitTime) {

        patientService.addVisitToPatient(patientId, doctorId, visitTime, description);
        return ResponseEntity.ok("Visit added successfully");
    }
}
