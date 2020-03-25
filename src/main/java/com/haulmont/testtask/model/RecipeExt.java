package com.haulmont.testtask.model;

import java.sql.Date;

public class RecipeExt extends Recipe {
    private String doctorFio;
    private String patientFio;

    public RecipeExt(String doctorFio, String patientFio, long doctorId, long patientId, String desc, Date creationDate, Date validityDate, Priority priority) {
        super(doctorId, patientId, desc, creationDate, validityDate, priority);
        this.doctorFio = doctorFio;
        this.patientFio = patientFio;
    }

    public String getDoctorFio() {
        return Helper.getShortFio(doctorFio);
    }

    public void setDoctorFio(String doctorFio) {
        this.doctorFio = doctorFio;
    }

    public String getPatientFio() {
        return Helper.getShortFio(patientFio);
    }

    public void setPatientFio(String patientFio) {
        this.patientFio = patientFio;
    }
}
