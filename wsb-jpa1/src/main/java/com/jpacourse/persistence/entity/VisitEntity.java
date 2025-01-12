package com.jpacourse.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "VISIT")
public class VisitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	@Column(nullable = false)
	private LocalDateTime time;

	@ManyToOne(optional = false) // Jednostronna relacja od strony dziecka
	@JoinColumn(name = "patient_id", nullable = false)
	@JsonBackReference
	private PatientEntity patient;

	@ManyToOne(optional = false) // Jednostronna relacja od strony dziecka
	@JoinColumn(name = "doctor_id", nullable = false)
	private DoctorEntity doctor;

	@OneToMany(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<MedicalTreatmentEntity> treatments = new HashSet<>(); // Dwustronna relacja z MedicalTreatmentEntity

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}

	public DoctorEntity getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorEntity doctor) {
		this.doctor = doctor;
	}

	public Set<MedicalTreatmentEntity> getTreatments() {
		return treatments;
	}

	public void setTreatments(Set<MedicalTreatmentEntity> treatments) {
		this.treatments = treatments;
	}
}
