package com.haulmont.testtask.view;

import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.service.PatientService;
import com.haulmont.testtask.view.design.PatientFormDesign;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import java.util.List;

public class PatientForm extends PatientFormDesign implements View {

    private PatientService service = PatientService.getInstance();

    public PatientForm() {
        //add.addClickListener(event -> );
    }

    public void updateGrid() {
        grid.removeColumn("id");

        List<Patient> patients = service.getAll();
        grid.setItems(patients);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        updateGrid();

    }
}
