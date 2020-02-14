package com.haulmont.testtask.view.design;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

/**
 * !! DO NOT EDIT THIS FILE !!
 * <p>
 * This class is generated by Vaadin Designer and will be overwritten.
 * <p>
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class MenuFormDesign extends HorizontalLayout {
    protected Button menuButton;
    protected CssLayout menu;
    protected Button patientBtn;
    protected Button doctorBtn;
    protected Button recipeBtn;
    protected Label viewTitle;
    protected CssLayout content;

    public MenuFormDesign() {
        Design.read(this);
    }
}
