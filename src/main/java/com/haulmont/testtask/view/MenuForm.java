package com.haulmont.testtask.view;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.view.design.MenuFormDesign;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.UI;

public class MenuForm extends MenuFormDesign implements View {
    private UI ui;
    private Navigator navigator;

    public MenuForm(UI ui) {
        this.ui = ui;
        navigator = new Navigator(ui, this);
        navigator.addView("patient", PatientForm.class);
        navigator.addView("doctor", DoctorForm.class);
        navigator.addView("recipe", RecipeForm.class);
        patientBtn.addClickListener(event -> navigator.navigateTo("patient"));
        doctorBtn.addClickListener(event -> navigator.navigateTo("doctor"));
        recipeBtn.addClickListener(event -> navigator.navigateTo("recipe"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
