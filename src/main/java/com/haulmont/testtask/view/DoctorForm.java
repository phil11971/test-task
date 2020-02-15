package com.haulmont.testtask.view;

import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.service.DoctorService;
import com.haulmont.testtask.view.design.DoctorFormDesign;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import java.util.List;

public class DoctorForm extends DoctorFormDesign implements View {
    private DoctorService service = DoctorService.getInstance();

    public DoctorForm() {
        //add.addClickListener(event -> );
    }

    public void updateGrid() {
        grid.removeColumn("id");

        List<Doctor> doctors = service.getAll();
        grid.setItems(doctors);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        updateGrid();

    }
}
