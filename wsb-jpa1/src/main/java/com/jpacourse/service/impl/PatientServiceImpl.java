package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistence.dao.DoctorRepository;
import com.jpacourse.persistence.dao.PatientRepository;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.rest.exception.EntityNotFoundException;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PatientDao patientDao;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, DoctorRepository doctorRepository, PatientDao patientDao) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.patientDao = patientDao;
    }

    @Override
    public PatientTO findById(Long id) {
        PatientEntity entity = patientRepository.findById(id).orElse(null);
        return PatientMapper.mapToTO(entity);
    }

    @Override
    public List<PatientTO> findAll() {
        return patientRepository.findAll().stream()
                .map(PatientMapper::mapToTO)
                .collect(Collectors.toList());
    }
    public void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitTime, String description) {
        // Pobierz pacjenta
        PatientEntity patient = patientRepository.findPatientWithVisits(patientId);
        if (patient == null) {
            throw new EntityNotFoundException(patientId);
        }

        // Pobierz doktora
        DoctorEntity doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException(doctorId));

        // Utwórz nową wizytę
        VisitEntity visit = new VisitEntity();
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setTime(visitTime);
        visit.setDescription(description);

        // Dodaj wizytę do pacjenta
        patient.getVisits().add(visit);

        patientRepository.save(patient);
    }

    @Override
    public List<PatientEntity> findPatientsByLastName(String lastName) {
        return patientDao.findPatientsByLastName(lastName);
    }

    @Override
    public List<VisitEntity> findVisitsByPatientId(Long patientId) {
        return patientDao.findVisitsByPatientId(patientId);
    }

    @Override
    public List<PatientEntity> findPatientsWithMoreThanGivenVisits(int visitCount) {
        return patientDao.findPatientsWithMoreThanGivenVisits(visitCount);
    }

    @Override
    public List<PatientEntity> findPatientsWithVisitsAfter(LocalDateTime date) {
        return patientDao.findPatientsWithVisitsAfter(date);
    }

}