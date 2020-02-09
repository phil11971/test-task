package com.haulmont.testtask.model;

import java.sql.Date;

public class Recipe {
    private long doctorId;
    private long patientId;
    private String desc;
    private Date creationDate;
    private int validity;
    private Priority priority;

    public Recipe(long doctorId, long patientId) {
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    public Recipe(long doctorId, long patientId, String desc, Date creationDate, int validity, Priority priority) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.desc = desc;
        this.creationDate = creationDate;
        this.validity = validity;
        this.priority = priority;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}

enum Priority{
    NORMAL,
    CITO,
    STATIM
}
