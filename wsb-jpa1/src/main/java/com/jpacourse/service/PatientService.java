package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientService {
    PatientTO findById(Long id);
    List<PatientTO> findAll();
    void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitTime, String description);
}
