package com.haulmont.testtask.model;

public class DoctorStat extends Entity {
    private String doctorFio;
    private int numRecipes;

    public DoctorStat(long id, String doctorFio, int numRecipes) {
        super(id);
        this.doctorFio = doctorFio;
        this.numRecipes = numRecipes;
    }

    public String getDoctorFio() {
        return Helper.getShortFio(doctorFio);
    }

    public void setDoctorFio(String doctorFio) {
        this.doctorFio = doctorFio;
    }

    public int getNumRecipes() {
        return numRecipes;
    }

    public void setNumRecipes(int numRecipes) {
        this.numRecipes = numRecipes;
    }
}
