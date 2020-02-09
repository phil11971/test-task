package com.haulmont.testtask.db.abstraction.dao;

import com.haulmont.testtask.exception.db.DaoException;
import com.haulmont.testtask.model.Doctor;

import java.util.List;

public interface DoctorDao {
    Long insert(Doctor doctor) throws DaoException;

    void delete(Long id) throws DaoException;

    void update(Doctor doctor) throws DaoException;

    List<Doctor> getAll() throws DaoException;
}
