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
import java.util.List;
import java.util.Set;

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

    @Test
    public void testFindVisitsByPatientId() {
        // given
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("ul. Lipowa 10");
        addressEntity.setAddressLine2("Mieszkanie 4");
        addressEntity.setCity("Kraków");
        addressEntity.setPostalCode("30-001");
        addressEntity = addressRepository.save(addressEntity);

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFirstName("Marcin");
        patientEntity.setLastName("Wiśniewski");
        patientEntity.setTelephoneNumber("987654321");
        patientEntity.setEmail("marcin.wisniewski@example.com");
        patientEntity.setPatientNumber("P456");
        patientEntity.setDateOfBirth(LocalDate.of(1985, 5, 15));
        patientEntity.setIsInsured(true);
        patientEntity.setAddress(addressEntity);

        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setFirstName("Ewa");
        doctorEntity.setLastName("Zielińska");
        doctorEntity.setSpecialization(Specialization.DERMATOLOGIST);
        doctorEntity.setDoctorNumber("D456");
        doctorEntity.setTelephoneNumber("789-123-456");
        doctorEntity = doctorRepository.save(doctorEntity);

        VisitEntity visit1 = new VisitEntity();
        visit1.setTime(LocalDateTime.now().minusDays(3));
        visit1.setDescription("Konsultacja dermatologiczna");
        visit1.setPatient(patientEntity);
        visit1.setDoctor(doctorEntity);

        VisitEntity visit2 = new VisitEntity();
        visit2.setTime(LocalDateTime.now());
        visit2.setDescription("Badanie kontrolne serca");
        visit2.setPatient(patientEntity);
        visit2.setDoctor(doctorEntity);

        patientEntity.setVisits(Set.of(visit1, visit2));

        patientEntity = patientRepository.save(patientEntity);

        // when
        List<VisitEntity> visits = patientService.findVisitsByPatientId(patientEntity.getId());

        // then
        assertThat(visits).isNotEmpty();
        assertThat(visits).hasSize(2);
        assertThat(visits.get(0).getPatient().getId()).isEqualTo(patientEntity.getId());
        assertThat(visits.get(0).getDescription()).isEqualTo("Konsultacja dermatologiczna");
        assertThat(visits.get(1).getDescription()).isEqualTo("Badanie kontrolne serca");
    }

    @Test
    public void testFindVisitsByPatientId_NoVisits() {
        // given
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("ul. Klonowa 5");
        addressEntity.setAddressLine2("Mieszkanie 2");
        addressEntity.setCity("Warszawa");
        addressEntity.setPostalCode("00-123");
        addressEntity = addressRepository.save(addressEntity);

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFirstName("Anna");
        patientEntity.setLastName("Kowalska");
        patientEntity.setTelephoneNumber("555444333");
        patientEntity.setEmail("anna.kowalska@example.com");
        patientEntity.setPatientNumber("P789");
        patientEntity.setDateOfBirth(LocalDate.of(1992, 8, 25));
        patientEntity.setIsInsured(false);
        patientEntity.setAddress(addressEntity);

        patientEntity = patientRepository.save(patientEntity);

        // when
        List<VisitEntity> visits = patientService.findVisitsByPatientId(patientEntity.getId());

        // then
        assertThat(visits).isEmpty();
    }


}
