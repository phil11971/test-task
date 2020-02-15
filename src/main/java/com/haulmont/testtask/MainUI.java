package com.haulmont.testtask;

import com.haulmont.testtask.view.MenuForm;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

public class MainUI extends UI {
    private Navigator navigator;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        MenuForm menuForm = new MenuForm(this);
        navigator = new Navigator(this, this);
        navigator.addView("", menuForm);

        setContent(menuForm);
    }
}
