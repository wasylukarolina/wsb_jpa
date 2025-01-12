package com.jpacourse.rest;


import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping("/searchByLastName")
    public List<PatientEntity> searchPatientsByLastName(@RequestParam String lastName) {
        return patientService.findPatientsByLastName(lastName);
    }

    @GetMapping("/{patientId}/visits")
    public List<VisitEntity> getVisitsByPatientId(@PathVariable Long patientId) {
        return patientService.findVisitsByPatientId(patientId);
    }

    @GetMapping("/more-than-visits")
    public List<PatientEntity> getPatientsWithMoreThanXVisits(@RequestParam int visitCount) {
        return patientService.findPatientsWithMoreThanGivenVisits(visitCount);
    }

    @GetMapping("/visits-after")
    public List<PatientEntity> getPatientsWithVisitsAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        return patientService.findPatientsWithVisitsAfter(date);
    }
}
