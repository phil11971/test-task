package com.haulmont.testtask.db.implementation.hsql.dao;

import com.haulmont.testtask.db.abstraction.dao.PatientDao;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.db.implementation.hsql.HSQLDBConnection;
import com.haulmont.testtask.db.implementation.hsql.constant.*;
import com.haulmont.testtask.exception.db.*;

import java.sql.*;
import java.util.*;

public class HSQLDBPatientDao implements PatientDao {
    private Connection connection;

    public HSQLDBPatientDao() throws DaoException {
        try {
            connection = HSQLDBConnection.getConnection();
        } catch (DriverNotFoundException | DbConnectionException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Long insert(Patient patient) throws DaoException {
        try {
            String sql = "INSERT INTO " + HSQLDBConstants.TABLE_PATIENT + " ("
                + HSQLDBConstants.TABLE_PATIENT_LAST_NAME + ", "
                + HSQLDBConstants.TABLE_PATIENT_FIRST_NAME + ", "
                + HSQLDBConstants.TABLE_PATIENT_MIDDLE_NAME + ", "
                + HSQLDBConstants.TABLE_PATIENT_PHONE + ", "
                + "VALUES (?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, patient.getLastName());
            preparedStatement.setString(2, patient.getFirstName());
            preparedStatement.setString(3, patient.getMiddleName());
            preparedStatement.setString(4, patient.getPhone());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            return resultSet.next() ? resultSet.getLong(1) : 0;
        }
        catch (SQLException e) {
            throw new DaoException(HSQLDBErrorConstants.INSERT_ERROR + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        try {
            String sql = "DELETE FROM " + HSQLDBConstants.TABLE_PATIENT
                + " WHERE " + HSQLDBConstants.TABLE_PATIENT_ID + " = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException(HSQLDBErrorConstants.DELETE_ERROR + e.getMessage());
        }
    }

    @Override
    public void update(Patient patient) throws DaoException {
        try {
            String sql = "UPDATE " + HSQLDBConstants.TABLE_PATIENT + " SET "
                + HSQLDBConstants.TABLE_PATIENT_LAST_NAME + " = ?, "
                + HSQLDBConstants.TABLE_PATIENT_FIRST_NAME + " = ?, "
                + HSQLDBConstants.TABLE_PATIENT_MIDDLE_NAME + " = ?, "
                + HSQLDBConstants.TABLE_PATIENT_PHONE + " = ?, "
                + "WHERE " + HSQLDBConstants.TABLE_PATIENT_ID + " = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patient.getLastName());
            preparedStatement.setString(2, patient.getFirstName());
            preparedStatement.setString(3, patient.getMiddleName());
            preparedStatement.setString(4, patient.getPhone());
            preparedStatement.setLong(5, patient.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException(HSQLDBErrorConstants.UPDATE_ERROR + e.getMessage());
        }
    }

    @Override
    public List<Patient> getAll() throws DaoException {
        try {
            List<Patient> patients = new ArrayList<>();
            String sql = "SELECT * FROM " + HSQLDBConstants.TABLE_PATIENT + ";";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Patient patient = new Patient(resultSet.getLong(HSQLDBConstants.TABLE_PATIENT_ID),
                        resultSet.getString(HSQLDBConstants.TABLE_PATIENT_LAST_NAME),
                        resultSet.getString(HSQLDBConstants.TABLE_PATIENT_FIRST_NAME),
                        resultSet.getString(HSQLDBConstants.TABLE_PATIENT_MIDDLE_NAME),
                        resultSet.getString(HSQLDBConstants.TABLE_PATIENT_PHONE));
                patients.add(patient);
            }
            return patients;
        }
        catch (SQLException e) {
            throw new DaoException(HSQLDBErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }
}
