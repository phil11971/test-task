package com.haulmont.testtask.view.design.implementation;

import com.haulmont.testtask.model.Recipe;
import com.haulmont.testtask.model.RecipeExt;
import com.haulmont.testtask.service.RecipeService;
import com.haulmont.testtask.view.design.abstraction.RecipeFormDesign;
import com.haulmont.testtask.view.subwindow.implementation.RecipeSubWindow;
import com.haulmont.testtask.view.constant.ViewConstants;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;

import java.util.EnumSet;
import java.util.List;

public class RecipeForm extends RecipeFormDesign implements View {
    private RecipeService service = RecipeService.getInstance();
    private Navigator navigator;

    public RecipeForm() {
        combobox.setTextInputAllowed(false);
        combobox.setEmptySelectionAllowed(false);
        combobox.setItems(EnumSet.allOf(Filter.class));
        combobox.setSelectedItem(Filter.Desc);

        apply.addClickListener(event -> {
            ListDataProvider<RecipeExt> dataProvider = (ListDataProvider<RecipeExt>) grid.getDataProvider();
            Filter filter = combobox.getValue();
            switch(filter){
                case Desc:
                    dataProvider.setFilter(Recipe::getDesc, s -> caseInsensitiveContains(s, search.getValue()));
                    break;
                case Priority:
                    dataProvider.setFilter(Recipe::getPriority, s -> caseInsensitiveContains(s.name(), search.getValue()));
                    break;
                case Patient:
                    dataProvider.setFilter(RecipeExt::getPatientFio, s -> caseInsensitiveContains(s, search.getValue()));
                    break;
                default:
                    dataProvider.clearFilters();
                    break;
            }
        });

        initGrid();

        grid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                RecipeSubWindow recipeSubWindow = new RecipeSubWindow(event.getItem());
                recipeSubWindow.addCloseListener(e -> refresh());
                getUI().addWindow(recipeSubWindow);
            }
        });

        back.addClickListener(event -> {
            navigator.navigateTo("");
        });

        add.addClickListener(event -> {
            RecipeSubWindow recipeSubWindow = new RecipeSubWindow();
            recipeSubWindow.addCloseListener(e -> refresh());
            getUI().addWindow(recipeSubWindow);
        });

        update.addClickListener(event -> {
            if(grid.getSelectionModel().getFirstSelectedItem().isPresent()) {
                RecipeExt selectedRecipe = grid.getSelectionModel().getFirstSelectedItem().get();
                RecipeSubWindow recipeSubWindow = new RecipeSubWindow(selectedRecipe);
                recipeSubWindow.addCloseListener(e -> refresh());
                getUI().addWindow(recipeSubWindow);
            }
            else Notification.show(ViewConstants.SELECT_ENTRY_TO_EDIT);
        });

        delete.addClickListener(event -> {
            if(grid.getSelectionModel().getFirstSelectedItem().isPresent()) {
                Recipe selectedRecipe = grid.getSelectionModel().getFirstSelectedItem().get();
                if(service.deleteRecipe(selectedRecipe.getDoctorId(), selectedRecipe.getPatientId()))
                    refresh();
            }
            else Notification.show(ViewConstants.SELECT_ENTRY_TO_DELETE);
        });
    }

    protected void refresh() {
        List<RecipeExt> recipes = service.getAll();
        grid.setItems(recipes);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        navigator = viewChangeEvent.getNavigator();
        refresh();
    }

    private boolean caseInsensitiveContains(String where, String what) {
        return where.toLowerCase().contains(what.toLowerCase());
    }

    private void initGrid() {
        List<Grid.Column<RecipeExt, ?>> list = grid.getColumns();
        list.get(0).setCaption("Doctor");
        list.get(1).setCaption("Patient");
        grid.addColumn(Recipe::getDesc).setCaption("Desc");
        grid.addColumn(Recipe::getCreationDate).setCaption("Creation");
        grid.addColumn(Recipe::getValidityDate).setCaption("Validity");
        grid.addColumn(Recipe::getPriority).setCaption("Priority");;
    }
}

