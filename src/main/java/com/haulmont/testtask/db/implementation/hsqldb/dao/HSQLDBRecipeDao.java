package com.haulmont.testtask.db.implementation.hsqldb.dao;

import com.haulmont.testtask.db.abstraction.dao.RecipeDao;
import com.haulmont.testtask.model.Priority;
import com.haulmont.testtask.model.Recipe;
import com.haulmont.testtask.db.implementation.hsqldb.HSQLDBConnection;
import com.haulmont.testtask.db.constant.*;
import com.haulmont.testtask.exception.db.*;
import com.haulmont.testtask.model.RecipeExt;

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
            String sql = "INSERT INTO " + TableConstants.TABLE_RECIPE + " ("
                + TableConstants.TABLE_DOCTOR_ID + ", "
                + TableConstants.TABLE_PATIENT_ID + ", "
                + TableConstants.TABLE_RECIPE_DESC + ", "
                + TableConstants.TABLE_RECIPE_CREATIONDATE + ", "
                + TableConstants.TABLE_RECIPE_VALIDITYDATE + ", "
                + TableConstants.TABLE_RECIPE_PRIORITY + ") "
                + "VALUES (?,?,?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, recipe.getDoctorId());
            preparedStatement.setLong(2, recipe.getPatientId());
            preparedStatement.setString(3, recipe.getDesc());
            preparedStatement.setDate(4, recipe.getCreationDate());
            preparedStatement.setDate(5, recipe.getValidityDate());
            preparedStatement.setString(6, recipe.getPriority().name());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException(ErrorConstants.INSERT_ERROR + e.getMessage());
        }
    }

    @Override
    public void delete(Long doctorId, Long patientId) throws DaoException {
        try {
            String sql = "DELETE FROM " + TableConstants.TABLE_RECIPE + " "
                    + "WHERE " + TableConstants.TABLE_DOCTOR_ID + " = ? "
                    + "AND "  + TableConstants.TABLE_PATIENT_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, doctorId);
            preparedStatement.setLong(2, patientId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException(ErrorConstants.DELETE_ERROR + e.getMessage());
        }
    }

    @Override
    public void update(Recipe recipe) throws DaoException {
        try {
            String sql = "UPDATE " + TableConstants.TABLE_RECIPE + " SET "
                + TableConstants.TABLE_RECIPE_DESC + " = ?, "
                + TableConstants.TABLE_RECIPE_CREATIONDATE + " = ?, "
                + TableConstants.TABLE_RECIPE_VALIDITYDATE + " = ?, "
                + TableConstants.TABLE_RECIPE_PRIORITY + " = ? "
                + "WHERE " + TableConstants.TABLE_DOCTOR_ID + " = ? AND " + TableConstants.TABLE_PATIENT_ID + " = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, recipe.getDesc());
            preparedStatement.setDate(2, recipe.getCreationDate());
            preparedStatement.setDate(3, recipe.getValidityDate());
            preparedStatement.setString(4, recipe.getPriority().name());
            preparedStatement.setLong(5, recipe.getDoctorId());
            preparedStatement.setLong(6, recipe.getPatientId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException(ErrorConstants.UPDATE_ERROR + e.getMessage());
        }
    }

    @Override
    public List<RecipeExt> getAll() throws DaoException {
        try {
            List<RecipeExt> recipes = new ArrayList<>();
            String sql = "SELECT CONCAT(d.LASTNAME, ' ', d.FIRSTNAME, ' ', CONCAT_WS('', d.MIDDLENAME, '')) as doctor, " +
                    "CONCAT(p.LASTNAME, ' ', p.FIRSTNAME, ' ', CONCAT_WS('', p.MIDDLENAME, '')) as patient, " +
                    "r.* " +
                    "FROM " + TableConstants.TABLE_RECIPE + " r " +
                    "join " + TableConstants.TABLE_DOCTOR + " d on r.DOCTORID = d.DOCTORID " +
                    "join " + TableConstants.TABLE_PATIENT + " p on r.PATIENTID = p.PATIENTID;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                RecipeExt recipe = new RecipeExt(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getLong(TableConstants.TABLE_DOCTOR_ID),
                        resultSet.getLong(TableConstants.TABLE_PATIENT_ID),
                        resultSet.getString(TableConstants.TABLE_RECIPE_DESC),
                        resultSet.getDate(TableConstants.TABLE_RECIPE_CREATIONDATE),
                        resultSet.getDate(TableConstants.TABLE_RECIPE_VALIDITYDATE),
                        Priority.valueOf(resultSet.getString(TableConstants.TABLE_RECIPE_PRIORITY)));
                recipes.add(recipe);
            }
            return recipes;
        }
        catch (SQLException e) {
            throw new DaoException(ErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }

    @Override
    public boolean existsRecordsByDoctorId(Long doctorId) throws DaoException {
        String sql = "SELECT CAST(1 AS BIT)\n" +
                "FROM " + TableConstants.TABLE_DOCTOR + " " +
                "WHERE " + TableConstants.TABLE_DOCTOR_ID + " = ? and EXISTS (SELECT * " +
                "                                FROM " + TableConstants.TABLE_RECIPE + " " +
                "                                WHERE " + TableConstants.TABLE_DOCTOR_ID + " = ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, doctorId);
            preparedStatement.setLong(2, doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return resultSet.getBoolean(1);
            return false;
        } catch (SQLException e) {
            throw new DaoException(ErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }

    @Override
    public boolean existsRecordsByPatientId(Long patientId) throws DaoException {
        String sql = "SELECT CAST(1 AS BIT)\n" +
                "FROM " + TableConstants.TABLE_PATIENT + " " +
                "WHERE " + TableConstants.TABLE_PATIENT_ID + " = ? and EXISTS (SELECT * " +
                "                                FROM " + TableConstants.TABLE_RECIPE + " " +
                "                                WHERE " + TableConstants.TABLE_PATIENT_ID + " = ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, patientId);
            preparedStatement.setLong(2, patientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return resultSet.getBoolean(1);
            return false;
        } catch (SQLException e) {
            throw new DaoException(ErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }

    @Override
    public boolean existsRecord(Long doctorId, Long patientId) throws DaoException {
        String sql = "SELECT * FROM " + TableConstants.TABLE_RECIPE +
                " WHERE " + TableConstants.TABLE_DOCTOR_ID + " = ? AND " +
                TableConstants.TABLE_PATIENT_ID + " = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, doctorId);
            preparedStatement.setLong(2, patientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? true : false;
        } catch (SQLException e) {
            throw new DaoException(ErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }


}
