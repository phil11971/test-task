package com.haulmont.testtask.db.implementation.hsqldb.dao;

import com.haulmont.testtask.db.abstraction.dao.DoctorDao;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.db.implementation.hsqldb.HSQLDBConnection;
import com.haulmont.testtask.db.constant.*;
import com.haulmont.testtask.exception.db.*;
import com.haulmont.testtask.model.DoctorStat;

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
            String sql = "INSERT INTO " + TableConstants.TABLE_DOCTOR + " ("
                + TableConstants.TABLE_DOCTOR_LAST_NAME + ", "
                + TableConstants.TABLE_DOCTOR_FIRST_NAME + ", "
                + TableConstants.TABLE_DOCTOR_MIDDLE_NAME + ", "
                + TableConstants.TABLE_DOCTOR_SPEC + ") "
                + "VALUES (?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, doctor.getLastname());
            preparedStatement.setString(2, doctor.getFirstname());
            preparedStatement.setString(3, doctor.getMiddlename());
            preparedStatement.setString(4, doctor.getSpec());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            return resultSet.next() ? resultSet.getLong(1) : 0;
        }
        catch (SQLException e) {
            throw new DaoException(ErrorConstants.INSERT_ERROR + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        try {
            String sql = "DELETE FROM " + TableConstants.TABLE_DOCTOR
                + " WHERE " + TableConstants.TABLE_DOCTOR_ID + " = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException(ErrorConstants.DELETE_ERROR + e.getMessage());
        }
    }

    @Override
    public void update(Doctor doctor) throws DaoException {
        try {
            String sql = "UPDATE " + TableConstants.TABLE_DOCTOR + " SET "
                + TableConstants.TABLE_DOCTOR_LAST_NAME + " = ?, "
                + TableConstants.TABLE_DOCTOR_FIRST_NAME + " = ?, "
                + TableConstants.TABLE_DOCTOR_MIDDLE_NAME + " = ?, "
                + TableConstants.TABLE_DOCTOR_SPEC + " = ? "
                + "WHERE " + TableConstants.TABLE_DOCTOR_ID + " = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, doctor.getLastname());
            preparedStatement.setString(2, doctor.getFirstname());
            preparedStatement.setString(3, doctor.getMiddlename());
            preparedStatement.setString(4, doctor.getSpec());
            preparedStatement.setLong(5, doctor.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException(ErrorConstants.UPDATE_ERROR + e.getMessage());
        }
    }

    @Override
    public List<Doctor> getAll() throws DaoException {
        try {
            List<Doctor> doctors = new ArrayList<>();
            String sql = "SELECT * FROM " + TableConstants.TABLE_DOCTOR + ";";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Doctor doctor = new Doctor(resultSet.getLong(TableConstants.TABLE_DOCTOR_ID),
                        resultSet.getString(TableConstants.TABLE_DOCTOR_LAST_NAME),
                        resultSet.getString(TableConstants.TABLE_DOCTOR_FIRST_NAME),
                        resultSet.getString(TableConstants.TABLE_DOCTOR_MIDDLE_NAME),
                        resultSet.getString(TableConstants.TABLE_DOCTOR_SPEC));
                doctors.add(doctor);
            }
            return doctors;
        }
        catch (SQLException e) {
            throw new DaoException(ErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }

    @Override
    public List<DoctorStat> getDoctorStats() throws DaoException {
        try {
            List<DoctorStat> doctorStats = new ArrayList<>();
            String sql = "SELECT d.DOCTORID, " +
                    "       CONCAT(d.LASTNAME, ' ', d.FIRSTNAME, ' ', CONCAT_WS(' ', d.MIDDLENAME, '')) as doctor, " +
                    "       COUNT(r.DOCTORID) " +
                    "FROM " + TableConstants.TABLE_DOCTOR + " d " +
                    "JOIN " + TableConstants.TABLE_RECIPE + " r on d.DOCTORID = r.DOCTORID " +
                    "GROUP BY d.DOCTORID";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DoctorStat doctorStat = new DoctorStat(
                        resultSet.getLong(TableConstants.TABLE_DOCTOR_ID),
                        resultSet.getString(2),
                        resultSet.getInt(3));
                doctorStats.add(doctorStat);
            }
            return doctorStats;
        }
        catch (SQLException e) {
            throw new DaoException(ErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }
}
