package com.haulmont.testtask;

import com.haulmont.testtask.view.DoctorForm;
import com.haulmont.testtask.view.MenuForm;
import com.haulmont.testtask.view.PatientForm;
import com.haulmont.testtask.view.RecipeForm;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MainUI extends UI {
    private MenuForm menuForm;
    private Navigator navigator;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        menuForm = new MenuForm();
        navigator = new Navigator(this, menuForm);

        navigator.addView("patient", new PatientForm());
        navigator.addView("doctor", new DoctorForm());
        navigator.addView("recipe", new RecipeForm());
        setContent(menuForm);
    }
}
