package com.haulmont.testtask.db.implementation.hsql;

import com.haulmont.testtask.db.abstraction.*;
import com.haulmont.testtask.db.abstraction.dao.DoctorDao;
import com.haulmont.testtask.db.abstraction.dao.PatientDao;
import com.haulmont.testtask.db.abstraction.dao.RecipeDao;
import com.haulmont.testtask.exception.db.DaoException;

public class HSQLDBDaoManager implements DaoManager {
    private static HSQLDBDaoManager instance;
    private DoctorDao doctorDao;
    private PatientDao patientDao;
    private RecipeDao recipeDao;

    public HSQLDBDaoManager() {

    }

    public static synchronized HSQLDBDaoManager getInstance() {
        if (instance == null) {
            instance = new HSQLDBDaoManager();
        }
        return instance;
    }

    @Override
    public DoctorDao getDoctorDao() throws DaoException {
        return null;
    }

    @Override
    public PatientDao getPatientDao() throws DaoException {
        return null;
    }

    @Override
    public RecipeDao getRecipeDao() throws DaoException {
        return null;
    }
}
