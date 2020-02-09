package com.haulmont.testtask.db.abstraction.dao;

import com.haulmont.testtask.exception.db.DaoException;
import com.haulmont.testtask.model.Recipe;

import java.util.List;

public interface RecipeDao {
    Long insert(Recipe recipe) throws DaoException;

    void delete(Long id) throws DaoException;

    void update(Recipe recipe) throws DaoException;

    List<Recipe> getAll() throws DaoException;
}
