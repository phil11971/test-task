package com.haulmont.testtask.db.implementation.hsql.dao;

import com.haulmont.testtask.db.abstraction.dao.PatientDao;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.db.implementation.hsql.HSQLDBConnection;
import com.haulmont.testtask.exception.db.*;

import java.sql.*;
import java.util.List;

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
        return null;
    }

    @Override
    public void delete(Long id) throws DaoException {

    }

    @Override
    public void update(Patient patient) throws DaoException {

    }

    @Override
    public List<Patient> getAll() throws DaoException {
        return null;
    }
}
