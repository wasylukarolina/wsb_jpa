package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistence.dao.PatientRepository;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
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
}