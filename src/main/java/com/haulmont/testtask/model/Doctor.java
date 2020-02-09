package com.haulmont.testtask.model;

public class Doctor {
    private long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String spec;

    public Doctor(long id) {
        this.id = id;
    }

    public Doctor(long id, String firstName, String lastName, String middleName, String spec) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.spec = spec;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
}
