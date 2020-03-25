package com.haulmont.testtask.view.subwindow.implementation;

import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.service.PatientService;
import com.haulmont.testtask.view.constant.ValidationConstants;
import com.haulmont.testtask.view.subwindow.abstraction.BasicSubWindow;
import com.haulmont.testtask.view.subwindow.abstraction.SimpleConvertation;
import com.haulmont.testtask.view.subwindow.abstraction.Validation;
import com.haulmont.testtask.view.validation.Utils;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

public class PatientSubWindow extends BasicSubWindow implements SimpleConvertation<Patient>, Validation {
    private TextField lastnameTextField;
    private TextField firstnameTextField;
    private TextField middlenameTextField;
    private TextField phoneTextField;

    private PatientService patientService;

    public PatientSubWindow() {
        super();

        patientService = PatientService.getInstance();
        setCaption("Add Patient");

        ok.addClickListener(event -> {
            if(validate()) {
                if (patientService.insertPatient(convertFormToObject())) {
                    close();
                } else {
                    Notification.show("Such a patient already exists in the system", Notification.Type.ERROR_MESSAGE);
                }
            }
            else {
                Notification.show(ValidationConstants.INVALID, Notification.Type.ERROR_MESSAGE);
            }
        });
    }

    public PatientSubWindow(Patient patient) {
        super();
        patientService = PatientService.getInstance();
        setCaption("Edit data about patient");

        lastnameTextField.setValue(patient.getLastname());
        firstnameTextField.setValue(patient.getFirstname());
        if(patient.getMiddlename() != null)
            middlenameTextField.setValue(patient.getMiddlename());
        phoneTextField.setValue(patient.getPhone());

        ok.addClickListener(event -> {
            if(validate()) {
                patientService.updatePatient(convertFormToObject(patient.getId()));
                close();
            }
            else {
                Notification.show(ValidationConstants.INVALID, Notification.Type.ERROR_MESSAGE);
            }
        });
    }

    @Override
    protected void constructForm() {
        setModal(true);
        setWidth("500px");
        setHeight("300px");
        setResizable(false);
        setDraggable(false);

        initLastnameTextField();
        initFirstnameTextField();
        initMiddlenameTextField();
        initPhoneTextField();

        formLayout.addComponents(lastnameTextField, firstnameTextField, middlenameTextField, phoneTextField);
        setContent(mainLayout);
    }

    private void initLastnameTextField() {
        lastnameTextField = new TextField("Lastname");
        lastnameTextField.setRequiredIndicatorVisible(true);
        lastnameTextField.setWidth("100%");
        lastnameTextField.setMaxLength(30);
        Utils.addValidator(lastnameTextField, new RegexpValidator(ValidationConstants.INVALID_LASTNAME,"^[A-Za-zА-Яа-яёЁ]+$", false ));
    }

    private void initFirstnameTextField() {
        firstnameTextField = new TextField("Firstname");
        firstnameTextField.setRequiredIndicatorVisible(true);
        firstnameTextField.setWidth("100%");
        firstnameTextField.setMaxLength(30);
        Utils.addValidator(firstnameTextField, new RegexpValidator(ValidationConstants.INVALID_FIRSTNAME,"^[A-Za-zА-Яа-яёЁ]+$", false ));
    }

    private void initMiddlenameTextField() {
        middlenameTextField = new TextField("Middlename");
        middlenameTextField.setWidth("100%");
        middlenameTextField.setMaxLength(30);
        Utils.addValidator(middlenameTextField, new RegexpValidator(ValidationConstants.INVALID_MIDDLENAME,"^([A-Za-zА-Яа-яёЁ]+|\\s*)$", false ));
    }

    private void initPhoneTextField() {
        phoneTextField = new TextField("Phone");
        phoneTextField.setRequiredIndicatorVisible(true);
        phoneTextField.setWidth("100%");
        phoneTextField.setMaxLength(30);
        phoneTextField.setPlaceholder("8(***)***-**-**");
        Utils.addValidator(phoneTextField, new RegexpValidator(ValidationConstants.INVALID_PHONE,"^8\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}$", false ));
    }

    @Override
    public Patient convertFormToObject(Long id) {
        return new Patient(id, lastnameTextField.getValue(), firstnameTextField.getValue(), middlenameTextField.getValue(), phoneTextField.getValue());
    }

    @Override
    public Patient convertFormToObject() {
        Patient patient = new Patient();
        patient.setLastname(lastnameTextField.getValue());
        patient.setFirstname(firstnameTextField.getValue());
        patient.setMiddlename(middlenameTextField.getValue());
        patient.setPhone(phoneTextField.getValue());

        return patient;
    }

    @Override
    public boolean validate(){
        return validateLastnameTextField() && validateFirstnameTextField()
                && validateMiddlenameTextField() && validatePhoneTextField();
    }

    private boolean validateLastnameTextField() {
        ValidationResult result = new RegexpValidator(ValidationConstants.INVALID_LASTNAME,"^[A-Za-zА-Яа-яёЁ]+$", false ).apply(lastnameTextField.getValue(), new ValueContext(lastnameTextField));
        return !result.isError();
    }

    private boolean validateFirstnameTextField() {
        ValidationResult result = new RegexpValidator(ValidationConstants.INVALID_FIRSTNAME,"^[A-Za-zА-Яа-яёЁ]+$", false ).apply(firstnameTextField.getValue(), new ValueContext(firstnameTextField));
        return !result.isError();
    }

    private boolean validateMiddlenameTextField() {
        ValidationResult result = new RegexpValidator(ValidationConstants.INVALID_MIDDLENAME,"^([A-Za-zА-Яа-яёЁ]|\\s*)+$", false ).apply(middlenameTextField.getValue(), new ValueContext(middlenameTextField));
        return !result.isError();
    }

    private boolean validatePhoneTextField() {
        ValidationResult result = new RegexpValidator(ValidationConstants.INVALID_PHONE,"^(\\+7|8)\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}$", false ).apply(phoneTextField.getValue(), new ValueContext(phoneTextField));
        return !result.isError();
    }

}
