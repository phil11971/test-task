package com.haulmont.testtask.db.abstraction;

import com.haulmont.testtask.db.abstraction.dao.DoctorDao;
import com.haulmont.testtask.db.abstraction.dao.PatientDao;
import com.haulmont.testtask.db.abstraction.dao.RecipeDao;
import com.haulmont.testtask.exception.db.DaoException;

public interface DaoManager {
    DoctorDao getDoctorDao() throws DaoException;

    PatientDao getPatientDao() throws DaoException;

    RecipeDao getRecipeDao() throws DaoException;
}
