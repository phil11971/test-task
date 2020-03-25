package com.haulmont.testtask.view.subwindow.implementation;

import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.service.DoctorService;
import com.haulmont.testtask.view.subwindow.abstraction.BasicSubWindow;
import com.haulmont.testtask.view.subwindow.abstraction.SimpleConvertation;
import com.haulmont.testtask.view.subwindow.abstraction.Validation;
import com.haulmont.testtask.view.validation.Utils;
import com.haulmont.testtask.view.constant.ValidationConstants;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

public class DoctorSubWindow extends BasicSubWindow implements SimpleConvertation<Doctor>, Validation {
    private TextField lastnameTextField;
    private TextField firstnameTextField;
    private TextField middlenameTextField;
    private TextField specTextField;

    private DoctorService doctorService;

    public DoctorSubWindow() {
        super();

        doctorService = DoctorService.getInstance();
        setCaption("Add doctor");

        ok.addClickListener(event -> {
            if(validate()) {
                if (doctorService.insertDoctor(convertFormToObject())) {
                    close();
                } else {
                    Notification.show("Such a doctor is already in the system", Notification.Type.ERROR_MESSAGE);
                }
            }
            else {
                Notification.show(ValidationConstants.INVALID, Notification.Type.ERROR_MESSAGE);
            }
        });
    }

    public DoctorSubWindow(Doctor doctor) {
        super();
        doctorService = DoctorService.getInstance();
        setCaption("Edit data about doctor");

        lastnameTextField.setValue(doctor.getLastname());
        firstnameTextField.setValue(doctor.getFirstname());
        if(doctor.getMiddlename() != null)
            middlenameTextField.setValue(doctor.getMiddlename());
        specTextField.setValue(doctor.getSpec());

        ok.addClickListener(event -> {
            if(validate()) {
                doctorService.updateDoctor(convertFormToObject(doctor.getId()));
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
        initSpecTextField();

        formLayout.addComponents(lastnameTextField, firstnameTextField, middlenameTextField, specTextField);
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

    private void initSpecTextField() {
        specTextField = new TextField("Specialization");
        specTextField.setRequiredIndicatorVisible(true);
        specTextField.setWidth("100%");
        specTextField.setMaxLength(30);
        Utils.addValidator(specTextField, new StringLengthValidator(ValidationConstants.INVALID_SPEC_OR_DESC,1, 100));
    }

    @Override
    public Doctor convertFormToObject(Long id) {
        return new Doctor(id, lastnameTextField.getValue(), firstnameTextField.getValue(), middlenameTextField.getValue(), specTextField.getValue());
    }

    @Override
    public Doctor convertFormToObject() {
        Doctor doctor = new Doctor();
        doctor.setLastname(lastnameTextField.getValue());
        doctor.setFirstname(firstnameTextField.getValue());
        doctor.setMiddlename(middlenameTextField.getValue());
        doctor.setSpec(specTextField.getValue());
        return doctor;
    }

    @Override
    public boolean validate() {
        return validateLastnameTextField() && validateFirstnameTextField()
                && validateMiddlenameTextField() && validateSpecTextField();
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

    private boolean validateSpecTextField() {
        ValidationResult result = new StringLengthValidator(ValidationConstants.INVALID_SPEC_OR_DESC,1, 100).apply(specTextField.getValue(), new ValueContext(specTextField));
        return !result.isError();
    }
}
