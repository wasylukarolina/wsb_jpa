package com.jpacourse.persistance.dao;

import com.jpacourse.persistence.dao.PatientRepository;
import com.jpacourse.persistence.dao.DoctorRepository;
import com.jpacourse.persistence.dao.AddressRepository;  // Dodanie importu AddressRepository
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.Specialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    @Test
    public void testAddVisitToPatient() {
        // given
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("line1");
        addressEntity.setAddressLine2("line2");
        addressEntity.setCity("City1");
        addressEntity.setPostalCode("66-666");
        addressEntity = addressRepository.save(addressEntity);

        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setFirstName("John");
        doctorEntity.setLastName("Doe");
        doctorEntity.setTelephoneNumber("123-456-789");
        doctorEntity.setEmail("john.doe@example.com");
        doctorEntity.setDoctorNumber("D12345");
        doctorEntity.setSpecialization(Specialization.DERMATOLOGIST);
        doctorEntity = doctorRepository.save(doctorEntity);

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFirstName("Alice");
        patientEntity.setLastName("Smith");
        patientEntity.setTelephoneNumber("987654321");
        patientEntity.setEmail("alice.smith@example.com");
        patientEntity.setPatientNumber("P12345");
        patientEntity.setDateOfBirth(LocalDate.of(1990, 5, 15));
        patientEntity.setIsInsured(true);
        patientEntity.setAddress(addressEntity);

        patientEntity.setVisits(new HashSet<>());
        patientEntity = patientRepository.save(patientEntity);

        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setTime(LocalDateTime.now());
        visitEntity.setDescription("Routine checkup");
        visitEntity.setPatient(patientEntity);
        visitEntity.setDoctor(doctorEntity);

        // Dodanie wizyty do pacjenta
        patientEntity.getVisits().add(visitEntity);

        // Zapisywanie pacjenta z wizytą
        patientRepository.save(patientEntity);

        // when
        PatientEntity fetchedPatient = patientRepository.findById(patientEntity.getId()).orElse(null);

        // then
        assertThat(fetchedPatient).isNotNull();
        assertThat(fetchedPatient.getVisits()).hasSize(1); // Sprawdzenie, czy wizyta została dodana

        VisitEntity addedVisit = fetchedPatient.getVisits().iterator().next();
        assertThat(addedVisit.getDoctor().getId()).isEqualTo(doctorEntity.getId());
        assertThat(addedVisit.getTime()).isEqualTo(visitEntity.getTime());
        assertThat(addedVisit.getDescription()).isEqualTo(visitEntity.getDescription());
    }



    @Transactional
    @Test
    public void testFindPatientWithVisits() {
        // given
        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setTelephoneNumber("123456789");
        doctor.setEmail("johndoe@example.com");
        doctor.setDoctorNumber("D12345");
        doctor.setSpecialization(Specialization.DERMATOLOGIST);
        doctor = doctorRepository.save(doctor);

        // Tworzenie adresu
        AddressEntity address = new AddressEntity();
        address.setAddressLine1("Ul. Przykładowa 1");
        address.setAddressLine2("Mieszkanie 2");
        address.setCity("Miasto");
        address.setPostalCode("12-345");
        address = addressRepository.save(address);  // Zapisanie adresu przed pacjentem

        // Tworzenie pacjenta i przypisanie adresu
        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Alice");
        patient.setLastName("Smith");
        patient.setTelephoneNumber("987654321");
        patient.setEmail("alicesmith@example.com");
        patient.setPatientNumber("P12345");
        patient.setDateOfBirth(LocalDate.of(1990, 5, 15));
        patient.setIsInsured(true);
        patient.setAddress(address);  // Przypisanie adresu do pacjenta

        // Inicjalizacja kolekcji wizyt
        patient.setVisits(new HashSet<>());

        patient = patientRepository.save(patient);

        LocalDateTime visitTime = LocalDateTime.now();
        String description = "Routine checkup";

        VisitEntity visit = new VisitEntity();
        visit.setDoctor(doctor);
        visit.setPatient(patient);
        visit.setTime(visitTime);
        visit.setDescription(description);

        // Dodanie wizyty do pacjenta
        patient.getVisits().add(visit);
        patientRepository.save(patient);

        // when
        PatientEntity fetchedPatient = patientRepository.findPatientWithVisits(patient.getId());

        // then
        assertThat(fetchedPatient).isNotNull();
        assertThat(fetchedPatient.getVisits()).hasSize(1);
    }

}
