package com.haulmont.testtask.view.subwindow.implementation;

import com.haulmont.testtask.model.DoctorStat;
import com.haulmont.testtask.service.DoctorService;
import com.haulmont.testtask.view.subwindow.abstraction.BasicSubWindow;
import com.vaadin.ui.Grid;

public class DoctorStatSubWindow extends BasicSubWindow {
    private Grid<DoctorStat> doctorStatGrid;

    private DoctorService doctorService;

    public DoctorStatSubWindow() {
        super();

        doctorService = DoctorService.getInstance();

        doctorStatGrid.setItems(doctorService.getDoctorStats());

        setCaption("Recipe doctors statistics");
    }

    @Override
    protected void constructForm() {
        setModal(true);
        setWidth("500px");
        setHeight("400px");
        setResizable(false);
        setDraggable(false);

        ok.setVisible(false);
        initDoctorStatGrid();

        formLayout.addComponents(doctorStatGrid);
        setContent(mainLayout);
    }

    private void initDoctorStatGrid() {
        doctorStatGrid = new Grid<>("Doctor statistics");
        doctorStatGrid.setHeight("250px");
        doctorStatGrid.setWidth("300px");
        doctorStatGrid.addColumn(DoctorStat::getDoctorFio).setCaption("Doctor");
        doctorStatGrid.addColumn(DoctorStat::getNumRecipes).setCaption("Number Recipes");
    }

}
