package com.haulmont.testtask.view.design.implementation;

import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.service.DoctorService;
import com.haulmont.testtask.view.design.abstraction.DoctorFormDesign;
import com.haulmont.testtask.view.constant.ViewConstants;
import com.haulmont.testtask.view.subwindow.implementation.DoctorStatSubWindow;
import com.haulmont.testtask.view.subwindow.implementation.DoctorSubWindow;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Notification;

public class DoctorForm extends DoctorFormDesign implements View {
    private DoctorService service = DoctorService.getInstance();
    private Navigator navigator;

    public DoctorForm() {
        grid.removeColumn("id");

        grid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                DoctorSubWindow doctorSubWindow = new DoctorSubWindow(event.getItem());
                doctorSubWindow.addCloseListener(e -> refresh());
                getUI().addWindow(doctorSubWindow);
            }
        });

        back.addClickListener(event -> {
            navigator.navigateTo("");
        });

        stat.addClickListener(event -> {
            DoctorStatSubWindow doctorSubWindow = new DoctorStatSubWindow();
            getUI().addWindow(doctorSubWindow);
        });

        add.addClickListener(event -> {
            DoctorSubWindow doctorSubWindow = new DoctorSubWindow();
            doctorSubWindow.addCloseListener(e -> refresh());
            getUI().addWindow(doctorSubWindow);
        });

         update.addClickListener(event -> {
             if(grid.getSelectionModel().getFirstSelectedItem().isPresent()) {
                 Doctor selectedDoctor = grid.getSelectionModel().getFirstSelectedItem().get();
                 DoctorSubWindow doctorSubWindow = new DoctorSubWindow(selectedDoctor);
                 doctorSubWindow.addCloseListener(e -> refresh());
                 getUI().addWindow(doctorSubWindow);
             }
             else Notification.show(ViewConstants.SELECT_ENTRY_TO_EDIT);
         });

        delete.addClickListener(event -> {
            if(grid.getSelectionModel().getFirstSelectedItem().isPresent()) {
                Doctor selectedDoctor = grid.getSelectionModel().getFirstSelectedItem().get();
                if (service.deleteDoctor(selectedDoctor.getId()))
                    refresh();
                else
                    Notification.show("This doctor was writing recipes");
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
