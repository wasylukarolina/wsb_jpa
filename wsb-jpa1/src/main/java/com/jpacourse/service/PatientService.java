package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;

import java.util.List;

public interface PatientService {
    public PatientTO findById(Long id);
    List<PatientTO> findAll();
}
