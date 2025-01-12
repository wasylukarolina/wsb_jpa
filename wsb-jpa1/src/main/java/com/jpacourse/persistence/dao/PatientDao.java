package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long>{
    List<PatientEntity> findPatientsByLastName(String lastName);
    List<VisitEntity> findVisitsByPatientId(Long patientId);
    List<PatientEntity> findPatientsWithMoreThanGivenVisits(int visitCount);
    List<PatientEntity> findPatientsWithVisitsAfter(LocalDateTime date);
    PatientEntity findPatientWithVisits(Long id);
}