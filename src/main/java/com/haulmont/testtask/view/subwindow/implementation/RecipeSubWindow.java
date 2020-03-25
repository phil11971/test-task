package com.haulmont.testtask.view.subwindow.implementation;

import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.Priority;
import com.haulmont.testtask.model.Recipe;
import com.haulmont.testtask.service.DoctorService;
import com.haulmont.testtask.service.PatientService;
import com.haulmont.testtask.service.RecipeService;
import com.haulmont.testtask.view.constant.ValidationConstants;
import com.haulmont.testtask.view.subwindow.abstraction.BasicSubWindow;
import com.haulmont.testtask.view.subwindow.abstraction.ComplexValidation;
import com.haulmont.testtask.view.subwindow.abstraction.CompositeConvertation;
import com.haulmont.testtask.view.validation.Utils;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import java.sql.Date;
import java.util.EnumSet;

public class RecipeSubWindow extends BasicSubWindow implements CompositeConvertation<Recipe>, ComplexValidation {
    private ComboBox<Doctor> doctorComboBox;
    private ComboBox<Patient> patientComboBox;
    private TextField descTextField;
    private DateField creationDateField;
    private DateField validityDateField;
    private ComboBox<Priority> priorityComboBox;

    private RecipeService recipeService;
    private DoctorService doctorService;
    private PatientService patientService;

    public RecipeSubWindow() {
        super();

        initForInsert();

        recipeService = RecipeService.getInstance();

        setCaption("Add recipe");

        ok.addClickListener(event -> {
            if(validateInsert()) {
                if (recipeService.insertRecipe(convertFormToObject())) {
                    close();
                }
                else {
                    Notification.show("This doctor has already written a recipe for this patient", Notification.Type.ERROR_MESSAGE);
                }
            }
            else {
                Notification.show(ValidationConstants.INVALID, Notification.Type.ERROR_MESSAGE);
            }


        });
    }

    public RecipeSubWindow(Recipe recipe) {
        super();

        initForUpdate();

        recipeService = RecipeService.getInstance();

        setCaption("Edit data about recipe");

        descTextField.setValue(recipe.getDesc());
        creationDateField.setValue(recipe.getCreationDate().toLocalDate());
        validityDateField.setValue(recipe.getValidityDate().toLocalDate());
        priorityComboBox.setValue(recipe.getPriority());

        ok.addClickListener(event -> {
            if(validateUpdate()) {
                recipeService.updateRecipe(convertFormToObject(recipe.getDoctorId(), recipe.getPatientId()));
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
    }

    private void initForUpdate() {
        initDescTextField();
        initCreationDateField();
        initValidityDateField();
        initPriorityComboBox();

        formLayout.addComponents(descTextField, creationDateField, validityDateField, priorityComboBox);
        setContent(mainLayout);
    }

    private void initForInsert() {
        doctorService = DoctorService.getInstance();
        patientService = PatientService.getInstance();

        setHeight("400px");
        initDoctorComboBox();
        initPatientComboBox();
        formLayout.addComponents(doctorComboBox, patientComboBox);

        initForUpdate();
    }

    private void initDoctorComboBox() {
        doctorComboBox = new ComboBox<>("Doctor");
        doctorComboBox.setTextInputAllowed(false);
        doctorComboBox.setEmptySelectionAllowed(false);
        doctorComboBox.setRequiredIndicatorVisible(true);
        doctorComboBox.setWidth("100%");
        doctorComboBox.setItemCaptionGenerator(Doctor::getLastname);
        doctorComboBox.setItems(doctorService.getAll());
    }

    private void initPatientComboBox() {
        patientComboBox = new ComboBox<>("Patient");
        patientComboBox.setTextInputAllowed(false);
        patientComboBox.setEmptySelectionAllowed(false);
        patientComboBox.setRequiredIndicatorVisible(true);
        patientComboBox.setWidth("100%");
        patientComboBox.setItemCaptionGenerator(Patient::getLastname);
        patientComboBox.setItems(patientService.getAll());
    }

    private void initDescTextField() {
        descTextField = new TextField("Description");
        descTextField.setRequiredIndicatorVisible(true);
        descTextField.setWidth("100%");
        descTextField.setMaxLength(30);
        Utils.addValidator(descTextField, new StringLengthValidator(ValidationConstants.INVALID_SPEC_OR_DESC,1, 100));
    }

    private void initCreationDateField() {
        creationDateField = new DateField("Creation date");
        creationDateField.setTextFieldEnabled(false);
        creationDateField.setRequiredIndicatorVisible(true);
        creationDateField.setWidth("100%");
    }

    private void initValidityDateField() {
        validityDateField = new DateField("Validity date");
        validityDateField.setTextFieldEnabled(false);
        validityDateField.setRequiredIndicatorVisible(true);
        validityDateField.setWidth("100%");
    }

    private void initPriorityComboBox() {
        priorityComboBox = new ComboBox<>("Priority");
        priorityComboBox.setTextInputAllowed(false);
        priorityComboBox.setEmptySelectionAllowed(false);
        priorityComboBox.setItems(EnumSet.allOf(Priority.class));
        priorityComboBox.setRequiredIndicatorVisible(true);
        priorityComboBox.setWidth("100%");
        priorityComboBox.setSelectedItem(Priority.Normal);
    }

    @Override
    public Recipe convertFormToObject(Long doctorId, Long patientId) {
        Date creationDate = Date.valueOf(creationDateField.getValue());
        Date validityDate = Date.valueOf(validityDateField.getValue());
        return new Recipe(doctorId, patientId, descTextField.getValue(), creationDate, validityDate, priorityComboBox.getValue());
    }

    @Override
    public Recipe convertFormToObject() {
        Recipe recipe = new Recipe();
        recipe.setDoctorId(doctorComboBox.getSelectedItem().get().getId());
        recipe.setPatientId(patientComboBox.getSelectedItem().get().getId());
        recipe.setDesc(descTextField.getValue());
        recipe.setCreationDate(Date.valueOf(creationDateField.getValue()));
        recipe.setValidityDate(Date.valueOf(validityDateField.getValue()));
        recipe.setPriority(priorityComboBox.getValue());

        return recipe;
    }

    @Override
    public boolean validateInsert() {
        return validateDoctorComboBox() && validatePatientComboBox()
                && validateDescTextField()
                && validateCreationDateField() && validateValidityDateField();
    }

    @Override
    public boolean validateUpdate() {
        return validateDescTextField();

    }

    private boolean validateDoctorComboBox() {
        return doctorComboBox.getValue() != null;
    }

    private boolean validatePatientComboBox() {
        return patientComboBox.getValue() != null;
    }

    private boolean validateDescTextField() {
        ValidationResult result = new StringLengthValidator(ValidationConstants.INVALID_SPEC_OR_DESC,1, 100).apply(descTextField.getValue(), new ValueContext(descTextField));
        return !result.isError();
    }

    private boolean validateCreationDateField() {
        return creationDateField.getValue() != null;
    }

    private boolean validateValidityDateField() {
        return validityDateField.getValue() != null;
    }
}
