package com.haulmont.testtask;

import com.haulmont.testtask.view.design.implementation.MenuForm;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

public class MainUI extends UI {
    private Navigator navigator;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        navigator = new Navigator(this, this);
        navigator.addView("", MenuForm.class);

        setContent(new MenuForm());
    }
}
