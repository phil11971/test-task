package com.haulmont.testtask.model;

public class Patient extends Entity {
    private String lastname;
    private String firstname;
    private String middlename;
    private String phone;

    public Patient(){}

    public Patient(long id, String lastname, String firstname, String middlename, String phone) {
        super(id);
        this.lastname = lastname;
        this.firstname = firstname;
        this.middlename = middlename;
        this.phone = phone;
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

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
