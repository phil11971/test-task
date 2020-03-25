package com.haulmont.testtask.view.design.implementation;

import com.haulmont.testtask.view.design.abstraction.MenuFormDesign;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.UI;

public class MenuForm extends MenuFormDesign implements View {
    private Navigator navigator;

    public MenuForm() {
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        navigator = viewChangeEvent.getNavigator();

        navigator.addView("patient", PatientForm.class);
        navigator.addView("doctor", DoctorForm.class);
        navigator.addView("recipe", RecipeForm.class);
        navigator.addView("", MenuForm.class);

        patientBtn.addClickListener(event -> navigator.navigateTo("patient"));
        doctorBtn.addClickListener(event -> navigator.navigateTo("doctor"));
        recipeBtn.addClickListener(event -> navigator.navigateTo("recipe"));
    }
}
