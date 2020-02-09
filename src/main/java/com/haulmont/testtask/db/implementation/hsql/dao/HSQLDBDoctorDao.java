package com.haulmont.testtask.db.implementation.hsql.dao;

import com.haulmont.testtask.db.abstraction.dao.DoctorDao;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.db.implementation.hsql.HSQLDBConnection;
import com.haulmont.testtask.exception.db.*;

import java.sql.Connection;
import java.util.List;

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
        return null;
    }

    @Override
    public void delete(Long id) throws DaoException {

    }

    @Override
    public void update(Doctor doctor) throws DaoException {

    }

    @Override
    public List<Doctor> getAll() throws DaoException {
        return null;
    }
}
