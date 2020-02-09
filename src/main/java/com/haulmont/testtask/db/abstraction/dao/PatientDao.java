package com.haulmont.testtask.db.abstraction.dao;

import com.haulmont.testtask.exception.db.DaoException;
import com.haulmont.testtask.model.Patient;

import java.util.List;

public interface PatientDao {
    Long insert(Patient patient) throws DaoException;

    void delete(Long id) throws DaoException;

    void update(Patient patient) throws DaoException;

    List<Patient> getAll() throws DaoException;
}
