package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    @Query("SELECT p FROM PatientEntity p WHERE p.lastName LIKE %:lastName%")
    List<PatientEntity> findPatientsByLastName(@Param("lastName") String lastName);

    @Query("SELECT p.visits FROM PatientEntity p WHERE p.id = :patientId")
    List<VisitEntity> findVisitsByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT p FROM PatientEntity p WHERE SIZE(p.visits) > :visitCount")
    List<PatientEntity> findPatientsWithMoreThanGivenVisits(@Param("visitCount") int visitCount);

    @Query("SELECT DISTINCT p FROM PatientEntity p " +
            "JOIN p.visits v " +
            "WHERE v.time > :date")
    List<PatientEntity> findPatientsWithVisitsAfter(@Param("date") LocalDateTime date);


    @Query("SELECT p FROM PatientEntity p LEFT JOIN FETCH p.visits WHERE p.id = :id")
    PatientEntity findPatientWithVisits(@Param("id") Long id);



}
