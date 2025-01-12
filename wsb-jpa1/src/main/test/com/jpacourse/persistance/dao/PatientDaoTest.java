package com.jpacourse.persistance.dao;

import com.jpacourse.persistence.dao.AddressRepository;
import com.jpacourse.persistence.dao.DoctorRepository;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.Specialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private DoctorRepository doctorRepository;


    @Test
    @DirtiesContext
    public void testFindPatientsByLastName() {
        // given
        String lastName = "Makowiak";

        // Tworzenie i zapisanie pierwszego pacjenta
        AddressEntity addressEntity1 = new AddressEntity();
        addressEntity1.setAddressLine1("ul. Szkolna 12");
        addressEntity1.setAddressLine2("Mieszkanie 5");
        addressEntity1.setCity("Gdańsk");
        addressEntity1.setPostalCode("80-001");
        addressEntity1 = addressRepository.save(addressEntity1);

        PatientEntity patientEntity1 = new PatientEntity();
        patientEntity1.setFirstName("Marek");
        patientEntity1.setLastName(lastName);
        patientEntity1.setTelephoneNumber("123456789");
        patientEntity1.setEmail("marek.Makowiak@example.com");
        patientEntity1.setPatientNumber("P101");
        patientEntity1.setDateOfBirth(LocalDate.of(1980, 5, 15));
        patientEntity1.setIsInsured(true);
        patientEntity1.setAddress(addressEntity1);
        patientDao.save(patientEntity1);

        // Tworzenie i zapisanie drugiego pacjenta
        AddressEntity addressEntity2 = new AddressEntity();
        addressEntity2.setAddressLine1("ul. Klonowa 7");
        addressEntity2.setAddressLine2("Mieszkanie 2");
        addressEntity2.setCity("Warszawa");
        addressEntity2.setPostalCode("00-123");
        addressEntity2 = addressRepository.save(addressEntity2);

        PatientEntity patientEntity2 = new PatientEntity();
        patientEntity2.setFirstName("Anna");
        patientEntity2.setLastName(lastName);
        patientEntity2.setTelephoneNumber("987654321");
        patientEntity2.setEmail("anna.Makowiak@example.com");
        patientEntity2.setPatientNumber("P102");
        patientEntity2.setDateOfBirth(LocalDate.of(1990, 10, 25));
        patientEntity2.setIsInsured(false);
        patientEntity2.setAddress(addressEntity2);
        patientDao.save(patientEntity2);

        // when
        List<PatientEntity> patients = patientDao.findPatientsByLastName(lastName);

        // then
        assertThat(patients).hasSize(2);
        assertThat(patients).allMatch(patient -> patient.getLastName().equals(lastName));
    }

    @Test
    @Transactional
    public void testFindPatientsWithMoreThanGivenVisits() {
        // given
        int visitCountThreshold = 2;

        // Tworzenie i zapisanie lekarza
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setFirstName("Ewa");
        doctorEntity.setLastName("Zielińska");
        doctorEntity.setSpecialization(Specialization.DERMATOLOGIST);
        doctorEntity.setDoctorNumber("D456");
        doctorEntity.setTelephoneNumber("789-123-456");
        doctorEntity = doctorRepository.save(doctorEntity);

        // Tworzenie i zapisanie adresu
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("ul. Lipowa 10");
        addressEntity.setAddressLine2("Mieszkanie 4");
        addressEntity.setCity("Kraków");
        addressEntity.setPostalCode("30-001");
        addressEntity = addressRepository.save(addressEntity);

        // Tworzenie pacjenta z 3 wizytami
        PatientEntity patientWith3Visits = new PatientEntity();
        patientWith3Visits.setFirstName("Marian");
        patientWith3Visits.setLastName("Wiśniewski");
        patientWith3Visits.setTelephoneNumber("123456789");
        patientWith3Visits.setEmail("mariano.wisniewski@domena.com");
        patientWith3Visits.setPatientNumber("P123");
        patientWith3Visits.setDateOfBirth(LocalDate.of(1985, 5, 15));
        patientWith3Visits.setIsInsured(true);
        patientWith3Visits.setAddress(addressEntity);

        VisitEntity visit1 = new VisitEntity();
        visit1.setTime(LocalDateTime.now().minusDays(10));
        visit1.setDescription("Konsultacja dermatologiczna");
        visit1.setPatient(patientWith3Visits);
        visit1.setDoctor(doctorEntity);

        VisitEntity visit2 = new VisitEntity();
        visit2.setTime(LocalDateTime.now().minusDays(5));
        visit2.setDescription("Kontrola alergii");
        visit2.setPatient(patientWith3Visits);
        visit2.setDoctor(doctorEntity);

        VisitEntity visit3 = new VisitEntity();
        visit3.setTime(LocalDateTime.now());
        visit3.setDescription("Badanie okresowe");
        visit3.setPatient(patientWith3Visits);
        visit3.setDoctor(doctorEntity);

        patientWith3Visits.setVisits(Set.of(visit1, visit2, visit3));
        patientDao.save(patientWith3Visits);

        // Tworzenie pacjenta z 1 wizytą
        PatientEntity patientWith1Visit = new PatientEntity();
        patientWith1Visit.setFirstName("Anna");
        patientWith1Visit.setLastName("Kowalska");
        patientWith1Visit.setTelephoneNumber("987654321");
        patientWith1Visit.setEmail("anna.kowalska@example.com");
        patientWith1Visit.setPatientNumber("P456");
        patientWith1Visit.setDateOfBirth(LocalDate.of(1992, 8, 25));
        patientWith1Visit.setIsInsured(false);
        patientWith1Visit.setAddress(addressEntity);

        VisitEntity visit4 = new VisitEntity();
        visit4.setTime(LocalDateTime.now().minusDays(3));
        visit4.setDescription("Badanie kontrolne");
        visit4.setPatient(patientWith1Visit);
        visit4.setDoctor(doctorEntity);

        patientWith1Visit.setVisits(Set.of(visit4));
        patientDao.save(patientWith1Visit);

        // when
        List<PatientEntity> patients = patientDao.findPatientsWithMoreThanGivenVisits(visitCountThreshold);

        // then
        assertThat(patients).isNotEmpty();
        assertThat(patients).hasSize(1);
        assertThat(patients.get(0).getFirstName()).isEqualTo("Marian");
        assertThat(patients.get(0).getVisits().size()).isGreaterThan(visitCountThreshold);
    }

    @Transactional
    @Test
    public void testFindPatientsWithVisitsAfter() {
        // given
        LocalDateTime thresholdDate = LocalDateTime.now().minusDays(5);

        // Tworzenie i zapisanie lekarza
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setFirstName("Ewa");
        doctorEntity.setLastName("Zielińska");
        doctorEntity.setSpecialization(Specialization.DERMATOLOGIST);
        doctorEntity.setDoctorNumber("D456");
        doctorEntity.setTelephoneNumber("789-123-456");
        doctorEntity = doctorRepository.save(doctorEntity);

        // Tworzenie i zapisanie adresu
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("ul. Lipowa 10");
        addressEntity.setAddressLine2("Mieszkanie 4");
        addressEntity.setCity("Kraków");
        addressEntity.setPostalCode("30-001");
        addressEntity = addressRepository.save(addressEntity);

        // Tworzenie pacjenta z wizytą późniejszą niż thresholdDate
        PatientEntity patientWithFutureVisit = new PatientEntity();
        patientWithFutureVisit.setFirstName("Marek");
        patientWithFutureVisit.setLastName("Wiśniewski");
        patientWithFutureVisit.setTelephoneNumber("123456789");
        patientWithFutureVisit.setEmail("marek.wisniewski@example.com");
        patientWithFutureVisit.setPatientNumber("P123");
        patientWithFutureVisit.setDateOfBirth(LocalDateTime.now().minusYears(30).toLocalDate());
        patientWithFutureVisit.setIsInsured(true);
        patientWithFutureVisit.setAddress(addressEntity);

        VisitEntity visit1 = new VisitEntity();
        visit1.setTime(LocalDateTime.now());
        visit1.setDescription("Konsultacja dermatologiczna");
        visit1.setPatient(patientWithFutureVisit);
        visit1.setDoctor(doctorEntity);

        patientWithFutureVisit.setVisits(Set.of(visit1));
        patientDao.save(patientWithFutureVisit);

        // Tworzenie pacjenta z wizytą wcześniejszą niż thresholdDate
        PatientEntity patientWithPastVisit = new PatientEntity();
        patientWithPastVisit.setFirstName("Anna");
        patientWithPastVisit.setLastName("Kowalska");
        patientWithPastVisit.setTelephoneNumber("987654321");
        patientWithPastVisit.setEmail("anna.kowalska@example.com");
        patientWithPastVisit.setPatientNumber("P456");
        patientWithPastVisit.setDateOfBirth(LocalDateTime.now().minusYears(25).toLocalDate());
        patientWithPastVisit.setIsInsured(false);
        patientWithPastVisit.setAddress(addressEntity);

        VisitEntity visit2 = new VisitEntity();
        visit2.setTime(LocalDateTime.now().minusDays(10));
        visit2.setDescription("Badanie kontrolne");
        visit2.setPatient(patientWithPastVisit);
        visit2.setDoctor(doctorEntity);

        patientWithPastVisit.setVisits(Set.of(visit2));
        patientDao.save(patientWithPastVisit);

        // when
        List<PatientEntity> patients = patientDao.findPatientsWithVisitsAfter(thresholdDate);

        // then
        assertThat(patients).isNotEmpty();
        assertThat(patients).hasSize(1);
        assertThat(patients.get(0).getFirstName()).isEqualTo("Marek");
        assertThat(patients.get(0).getVisits())
                .allMatch(visit -> visit.getTime().isAfter(thresholdDate));
    }
}



