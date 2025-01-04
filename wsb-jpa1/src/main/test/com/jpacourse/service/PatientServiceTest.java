package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistence.dao.*;
import com.jpacourse.persistence.dao.PatientRepository;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.Specialization;
import com.jpacourse.service.impl.PatientServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientServiceImpl patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Transactional
    @Test
    public void testShouldRemovePatientAndCascadeDeleteVisits() {
        // given
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("line1");
        addressEntity.setAddressLine2("line2");
        addressEntity.setCity("City1");
        addressEntity.setPostalCode("66-666");
        addressEntity = addressRepository.save(addressEntity);

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFirstName("John");
        patientEntity.setLastName("Doe");
        patientEntity.setTelephoneNumber("123456789");
        patientEntity.setEmail("john.doe@example.com");
        patientEntity.setPatientNumber("P123");
        patientEntity.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patientEntity.setIsInsured(true);
        patientEntity.setAddress(addressEntity);

        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setFirstName("Alice");
        doctorEntity.setLastName("Smith");
        doctorEntity.setSpecialization(Specialization.DERMATOLOGIST);
        doctorEntity.setDoctorNumber("D123");
        doctorEntity.setTelephoneNumber("123-456-789");
        doctorEntity = doctorRepository.save(doctorEntity);


        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setTime(LocalDateTime.now());
        visitEntity.setDescription("Checkup");
        visitEntity.setPatient(patientEntity);
        visitEntity.setDoctor(doctorEntity);

        patientEntity.setVisits(Collections.singleton(visitEntity));

        patientEntity = patientRepository.save(patientEntity);

        // when
        patientRepository.deleteById(patientEntity.getId());

        // then
        assertThat(patientRepository.findById(patientEntity.getId())).isEmpty();
        assertThat(visitRepository.findAllByPatientId(patientEntity.getId())).isEmpty();
    }


    @Transactional
    @Test
    public void testShouldFindPatientById() {
        // given
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("line1");
        addressEntity.setAddressLine2("line2");
        addressEntity.setCity("City1");
        addressEntity.setPostalCode("66-666");
        addressEntity = addressRepository.save(addressEntity);

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFirstName("John");
        patientEntity.setLastName("Doe");
        patientEntity.setTelephoneNumber("123456789");
        patientEntity.setEmail("john.doe@example.com");
        patientEntity.setPatientNumber("P123");
        patientEntity.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patientEntity.setIsInsured(true);
        patientEntity.setAddress(addressEntity);

        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setFirstName("Alice");
        doctorEntity.setLastName("Smith");
        doctorEntity.setSpecialization(Specialization.DERMATOLOGIST);
        doctorEntity.setDoctorNumber("D123");
        doctorEntity.setTelephoneNumber("123-456-789");
        doctorEntity = doctorRepository.save(doctorEntity);

        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setTime(LocalDateTime.now());
        visitEntity.setDescription("Checkup");
        visitEntity.setPatient(patientEntity);
        visitEntity.setDoctor(doctorEntity);
        patientEntity.setVisits(Collections.singleton(visitEntity));

        patientEntity = patientRepository.save(patientEntity);

        // when
        PatientTO result = patientService.findById(patientEntity.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(patientEntity.getId());
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
        assertThat(result.getTelephoneNumber()).isEqualTo("123456789");
        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.getPatientNumber()).isEqualTo("P123");
        assertThat(result.getDateOfBirth()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(result.getIsInsured()).isTrue();
        assertThat(result.getVisits()).isNotEmpty(); // Sprawdzenie wizyt
    }

}
