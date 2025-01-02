package com.jpacourse.dto;

import java.time.LocalDateTime;
import java.util.List;

public class VisitTO {
    private LocalDateTime time;
    private String doctorFirstName;
    private String doctorLastName;
    private List<String> treatmentTypes;

    public VisitTO(LocalDateTime time, String doctorFirstName, String doctorLastName, List<String> treatmentTypes) {
        this.time = time;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.treatmentTypes = treatmentTypes;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public List<String> getTreatmentTypes() {
        return treatmentTypes;
    }

    public void setTreatmentTypes(List<String> treatmentTypes) {
        this.treatmentTypes = treatmentTypes;
    }
}