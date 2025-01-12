package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PatientEntity> findPatientsByLastName(String lastName) {
        return entityManager.createQuery(
                        "SELECT p FROM PatientEntity p WHERE p.lastName LIKE :lastName", PatientEntity.class)
                .setParameter("lastName", "%" + lastName + "%")
                .getResultList();
    }

    @Override
    public List<VisitEntity> findVisitsByPatientId(Long patientId) {
        return entityManager.createQuery(
                        "SELECT v FROM VisitEntity v WHERE v.patient.id = :patientId", VisitEntity.class)
                .setParameter("patientId", patientId)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsWithMoreThanGivenVisits(int visitCount) {
        return entityManager.createQuery(
                        "SELECT p FROM PatientEntity p WHERE SIZE(p.visits) > :visitCount", PatientEntity.class)
                .setParameter("visitCount", visitCount)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsWithVisitsAfter(LocalDateTime date) {
        return entityManager.createQuery(
                        "SELECT DISTINCT p FROM PatientEntity p JOIN p.visits v WHERE v.time > :date", PatientEntity.class)
                .setParameter("date", date)
                .getResultList();
    }

    @Override
    public PatientEntity findPatientWithVisits(Long id) {
        return entityManager.createQuery(
                        "SELECT p FROM PatientEntity p LEFT JOIN FETCH p.visits WHERE p.id = :id", PatientEntity.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
