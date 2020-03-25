package com.haulmont.testtask.model;

public class Doctor extends Entity {
    private String lastname;
    private String firstname;
    private String middlename;
    private String spec;

    public Doctor() {
    }

    public Doctor(long id, String lastname, String firstname, String middlename, String spec) {
        super(id);
        this.lastname = lastname;
        this.firstname = firstname;
        this.middlename = middlename;
        this.spec = spec;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middleName) {
        this.middlename = middleName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
}
