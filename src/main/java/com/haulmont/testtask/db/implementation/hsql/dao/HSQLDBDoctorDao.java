package com.haulmont.testtask.db.implementation.hsql.dao;

import com.haulmont.testtask.db.abstraction.dao.DoctorDao;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.db.implementation.hsql.HSQLDBConnection;
import com.haulmont.testtask.db.implementation.hsql.constant.*;
import com.haulmont.testtask.exception.db.*;

import java.sql.*;
import java.util.*;

public class HSQLDBDoctorDao implements DoctorDao {
    private Connection connection;

    public HSQLDBDoctorDao() throws DaoException {
        try {
            connection = HSQLDBConnection.getConnection();
        } catch (DriverNotFoundException | DbConnectionException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Long insert(Doctor doctor) throws DaoException {
        try {
            String sql = "INSERT INTO " + HSQLDBConstants.TABLE_DOCTOR + " ("
                + HSQLDBConstants.TABLE_DOCTOR_LAST_NAME + ", "
                + HSQLDBConstants.TABLE_DOCTOR_FIRST_NAME + ", "
                + HSQLDBConstants.TABLE_DOCTOR_MIDDLE_NAME + ", "
                + HSQLDBConstants.TABLE_DOCTOR_SPEC + ", "
                + "VALUES (?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, doctor.getLastName());
            preparedStatement.setString(2, doctor.getFirstName());
            preparedStatement.setString(3, doctor.getMiddleName());
            preparedStatement.setString(4, doctor.getSpec());
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
            String sql = "DELETE FROM " + HSQLDBConstants.TABLE_DOCTOR
                + " WHERE " + HSQLDBConstants.TABLE_DOCTOR_ID + " = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException(HSQLDBErrorConstants.DELETE_ERROR + e.getMessage());
        }
    }

    @Override
    public void update(Doctor doctor) throws DaoException {
        try {
            String sql = "UPDATE " + HSQLDBConstants.TABLE_DOCTOR + " SET "
                + HSQLDBConstants.TABLE_DOCTOR_LAST_NAME + " = ?, "
                + HSQLDBConstants.TABLE_DOCTOR_FIRST_NAME + " = ?, "
                + HSQLDBConstants.TABLE_DOCTOR_MIDDLE_NAME + " = ?, "
                + HSQLDBConstants.TABLE_DOCTOR_SPEC + " = ?, "
                + "WHERE " + HSQLDBConstants.TABLE_DOCTOR_ID + " = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, doctor.getLastName());
            preparedStatement.setString(2, doctor.getFirstName());
            preparedStatement.setString(3, doctor.getMiddleName());
            preparedStatement.setString(4, doctor.getSpec());
            preparedStatement.setLong(5, doctor.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException(HSQLDBErrorConstants.UPDATE_ERROR + e.getMessage());
        }
    }

    @Override
    public List<Doctor> getAll() throws DaoException {
        try {
            List<Doctor> doctors = new ArrayList<>();
            String sql = "SELECT * FROM " + HSQLDBConstants.TABLE_DOCTOR + ";";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Doctor doctor = new Doctor(resultSet.getLong(HSQLDBConstants.TABLE_DOCTOR_ID),
                        resultSet.getString(HSQLDBConstants.TABLE_DOCTOR_LAST_NAME),
                        resultSet.getString(HSQLDBConstants.TABLE_DOCTOR_FIRST_NAME),
                        resultSet.getString(HSQLDBConstants.TABLE_DOCTOR_MIDDLE_NAME),
                        resultSet.getString(HSQLDBConstants.TABLE_DOCTOR_SPEC));
                doctors.add(doctor);
            }
            return doctors;
        }
        catch (SQLException e) {
            throw new DaoException(HSQLDBErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }
}
