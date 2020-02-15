package com.haulmont.testtask.view;

import com.haulmont.testtask.model.Recipe;
import com.haulmont.testtask.service.RecipeService;
import com.haulmont.testtask.view.design.RecipeFormDesign;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import java.util.List;

public class RecipeForm extends RecipeFormDesign implements View {
    private RecipeService service = RecipeService.getInstance();

    public RecipeForm() {
        //add.addClickListener(event -> );
    }

    public void updateGrid() {
        //grid.removeColumn("id");

        List<Recipe> recipes = service.getAll();
        grid.setItems(recipes);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        updateGrid();

    }
}
