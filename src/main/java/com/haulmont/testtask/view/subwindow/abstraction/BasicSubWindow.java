package com.haulmont.testtask.view.subwindow.abstraction;

import com.vaadin.ui.*;

public abstract class BasicSubWindow extends Window {

    protected Button ok;
    protected Button cancel;
    protected VerticalLayout mainLayout;
    protected FormLayout formLayout;
    protected HorizontalLayout buttonsLayout;

    public BasicSubWindow() {
        setStyleName("modal-window");
        mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        formLayout = new FormLayout();
        formLayout.setSizeFull();
        formLayout.setMargin(false);
        formLayout.setSpacing(true);

        ok = new Button("Оk");
        cancel = new Button("Cancel");
        cancel.addClickListener(event -> close());

        buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(ok, cancel);
        buttonsLayout.setSpacing(false);
        mainLayout.addComponents(formLayout, buttonsLayout);
        mainLayout.setExpandRatio(formLayout, 7.0f);
        mainLayout.setExpandRatio(buttonsLayout, 1.0f);
        constructForm();
    }

    abstract protected void constructForm();
}
