package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientService {
    PatientTO findById(Long id);
    List<PatientTO> findAll();
    void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitTime, String description);
    List<PatientEntity> findPatientsByLastName(String lastName);
    List<VisitEntity> findVisitsByPatientId(Long patientId);
    List<PatientEntity> findPatientsWithMoreThanGivenVisits(int visitCount);
    List<PatientEntity> findPatientsWithVisitsAfter(LocalDateTime date);
}
