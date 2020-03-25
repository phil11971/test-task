package com.haulmont.testtask.view.design.implementation;

import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.service.PatientService;
import com.haulmont.testtask.view.design.abstraction.PatientFormDesign;
import com.haulmont.testtask.view.constant.ViewConstants;
import com.haulmont.testtask.view.subwindow.implementation.PatientSubWindow;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Notification;

public class PatientForm extends PatientFormDesign implements View {
    private PatientService service = PatientService.getInstance();
    private Navigator navigator;

    public PatientForm() {
        grid.removeColumn("id");

        grid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                PatientSubWindow patientSubWindow = new PatientSubWindow(event.getItem());
                patientSubWindow.addCloseListener(e -> refresh());
                getUI().addWindow(patientSubWindow);
            }
        });

        back.addClickListener(event -> {
            navigator.navigateTo("");
        });

        add.addClickListener(event -> {
            PatientSubWindow patientSubWindow = new PatientSubWindow();
            patientSubWindow.addCloseListener(e -> refresh());
            getUI().addWindow(patientSubWindow);
        });

        update.addClickListener(event -> {
            if(grid.getSelectionModel().getFirstSelectedItem().isPresent()) {
                Patient selectedPatient = grid.getSelectionModel().getFirstSelectedItem().get();
                PatientSubWindow patientSubWindow = new PatientSubWindow(selectedPatient);
                patientSubWindow.addCloseListener(e -> refresh());
                getUI().addWindow(patientSubWindow);
            }
            else Notification.show(ViewConstants.SELECT_ENTRY_TO_EDIT);
        });

        delete.addClickListener(event -> {
            if(grid.getSelectionModel().getFirstSelectedItem().isPresent()) {
                Patient selectedPatient = grid.getSelectionModel().getFirstSelectedItem().get();
                if(service.deletePatient(selectedPatient.getId()))
                    refresh();
                else
                    Notification.show("Recipes have been written for this patient");
            }
            else Notification.show(ViewConstants.SELECT_ENTRY_TO_DELETE);

        });

    }

    protected void refresh() {
        grid.setItems(service.getAll());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        navigator = viewChangeEvent.getNavigator();
        refresh();
    }
}
