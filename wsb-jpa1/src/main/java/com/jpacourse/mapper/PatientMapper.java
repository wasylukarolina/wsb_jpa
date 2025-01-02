package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;

import java.util.stream.Collectors;

public class PatientMapper {

    public static PatientTO mapToTO(PatientEntity entity) {
        if (entity == null) {
            return null;
        }

        return new PatientTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getTelephoneNumber(),
                entity.getEmail(),
                entity.getPatientNumber(),
                entity.getDateOfBirth(),
                entity.getIsInsured(), // Nowe pole
                entity.getVisits().stream()
                        .map(PatientMapper::mapVisitToTO)
                        .collect(Collectors.toList())
        );
    }

    private static VisitTO mapVisitToTO(VisitEntity visit) {
        return new VisitTO(
                visit.getTime(),
                visit.getDoctor().getFirstName(),
                visit.getDoctor().getLastName(),
                visit.getTreatments().stream()
                        .map(t -> t.getType().toString())
                        .collect(Collectors.toList())
        );
    }
}
