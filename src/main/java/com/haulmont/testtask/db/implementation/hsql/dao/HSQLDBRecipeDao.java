package com.haulmont.testtask.db.implementation.hsql.dao;

import com.haulmont.testtask.db.abstraction.dao.RecipeDao;
import com.haulmont.testtask.model.Recipe;
import com.haulmont.testtask.db.implementation.hsql.HSQLDBConnection;
import com.haulmont.testtask.db.implementation.hsql.constant.*;
import com.haulmont.testtask.exception.db.*;

import java.sql.*;
import java.util.*;

public class HSQLDBRecipeDao implements RecipeDao {
    private Connection connection;

    public HSQLDBRecipeDao() throws DaoException {try {
        connection = HSQLDBConnection.getConnection();
    } catch (DriverNotFoundException | DbConnectionException e) {
        throw new DaoException(e.getMessage());
    }}

    @Override
    public void insert(Recipe recipe) throws DaoException {
        try {
            String sql = "INSERT INTO " + HSQLDBConstants.TABLE_RECIPE + " ("
                + HSQLDBConstants.TABLE_DOCTOR_ID + ", "
                + HSQLDBConstants.TABLE_PATIENT_ID + ", "
                + HSQLDBConstants.TABLE_RECIPE_DESC + ", "
                + HSQLDBConstants.TABLE_RECIPE_CREATIONDATE + ", "
                + HSQLDBConstants.TABLE_RECIPE_VALIDITY + ", "
                + HSQLDBConstants.TABLE_RECIPE_PRIORITY + ", "
                + "VALUES (?,?,?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, recipe.getDoctorId());
            preparedStatement.setLong(2, recipe.getPatientId());
            preparedStatement.setString(3, recipe.getDesc());
            preparedStatement.setDate(4, recipe.getCreationDate());
            preparedStatement.setInt(5, recipe.getValidity());
            preparedStatement.setString(6, recipe.getPriority());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException(HSQLDBErrorConstants.INSERT_ERROR + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) throws DaoException {

    }

    @Override
    public void update(Recipe recipe) throws DaoException {
        try {
            String sql = "UPDATE " + HSQLDBConstants.TABLE_RECIPE + " SET "
                + HSQLDBConstants.TABLE_RECIPE_DESC + " = ?, "
                + HSQLDBConstants.TABLE_RECIPE_CREATIONDATE + " = ?, "
                + HSQLDBConstants.TABLE_RECIPE_VALIDITY + " = ?, "
                + HSQLDBConstants.TABLE_RECIPE_PRIORITY + " = ?, "
                + "WHERE " + HSQLDBConstants.TABLE_DOCTOR_ID + " = ? AND" + HSQLDBConstants.TABLE_PATIENT_ID + " = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, recipe.getDesc());
            preparedStatement.setDate(2, recipe.getCreationDate());
            preparedStatement.setInt(3, recipe.getValidity());
            preparedStatement.setString(4, recipe.getPriority());
            preparedStatement.setLong(5, recipe.getDoctorId());
            preparedStatement.setLong(6, recipe.getPatientId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException(HSQLDBErrorConstants.UPDATE_ERROR + e.getMessage());
        }
    }

    @Override
    public List<Recipe> getAll() throws DaoException {
        try {
            List<Recipe> recipes = new ArrayList<>();
            String sql = "SELECT * FROM " + HSQLDBConstants.TABLE_RECIPE + ";";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Recipe recipe = new Recipe(resultSet.getLong(HSQLDBConstants.TABLE_DOCTOR_ID),
                        resultSet.getLong(HSQLDBConstants.TABLE_PATIENT_ID),
                        resultSet.getString(HSQLDBConstants.TABLE_RECIPE_DESC),
                        resultSet.getDate(HSQLDBConstants.TABLE_RECIPE_CREATIONDATE),
                        resultSet.getInt(HSQLDBConstants.TABLE_RECIPE_VALIDITY),
                        resultSet.getString(HSQLDBConstants.TABLE_RECIPE_PRIORITY));
                recipes.add(recipe);
            }
            return recipes;
        }
        catch (SQLException e) {
            throw new DaoException(HSQLDBErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }
}
