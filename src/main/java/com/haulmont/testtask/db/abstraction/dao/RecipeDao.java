package com.haulmont.testtask.db.abstraction.dao;

import com.haulmont.testtask.exception.db.DaoException;
import com.haulmont.testtask.model.Recipe;
import com.haulmont.testtask.model.RecipeExt;

import java.util.List;

public interface RecipeDao {
    void insert(Recipe recipe) throws DaoException;

    void delete(Long doctorId, Long patientId) throws DaoException;

    void update(Recipe recipe) throws DaoException;

    List<RecipeExt> getAll() throws DaoException;

    boolean existsRecordsByDoctorId(Long doctorId) throws DaoException;

    boolean existsRecordsByPatientId(Long patientId) throws DaoException;

    boolean existsRecord(Long doctorId, Long patientId) throws DaoException;
}
