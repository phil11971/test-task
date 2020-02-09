package com.haulmont.testtask.db.implementation.hsql.dao;

import com.haulmont.testtask.db.abstraction.dao.RecipeDao;
import com.haulmont.testtask.model.Recipe;
import com.haulmont.testtask.db.implementation.hsql.HSQLDBConnection;
import com.haulmont.testtask.exception.db.*;

import java.sql.*;
import java.util.List;

public class HSQLDBRecipeDao implements RecipeDao {
    private Connection connection;

    public HSQLDBRecipeDao() throws DaoException {
        try {
            connection = HSQLDBConnection.getConnection();
        } catch (DriverNotFoundException | DbConnectionException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Long insert(Recipe recipe) throws DaoException {
        return null;
    }

    @Override
    public void delete(Long id) throws DaoException {

    }

    @Override
    public void update(Recipe recipe) throws DaoException {

    }

    @Override
    public List<Recipe> getAll() throws DaoException {
        return null;
    }
}
