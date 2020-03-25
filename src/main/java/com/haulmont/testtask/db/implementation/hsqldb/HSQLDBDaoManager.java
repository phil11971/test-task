package com.haulmont.testtask.db.implementation.hsqldb;

import com.haulmont.testtask.db.abstraction.*;
import com.haulmont.testtask.db.abstraction.dao.DoctorDao;
import com.haulmont.testtask.db.abstraction.dao.PatientDao;
import com.haulmont.testtask.db.abstraction.dao.RecipeDao;
import com.haulmont.testtask.db.implementation.hsqldb.dao.HSQLDBDoctorDao;
import com.haulmont.testtask.db.implementation.hsqldb.dao.HSQLDBPatientDao;
import com.haulmont.testtask.db.implementation.hsqldb.dao.HSQLDBRecipeDao;
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
        if (doctorDao == null) {
            doctorDao = new HSQLDBDoctorDao();
        }
        return doctorDao;
    }

    @Override
    public PatientDao getPatientDao() throws DaoException {
        if (patientDao == null) {
            patientDao = new HSQLDBPatientDao();
        }
        return patientDao;
    }

    @Override
    public RecipeDao getRecipeDao() throws DaoException {
        if (recipeDao == null) {
            recipeDao = new HSQLDBRecipeDao();
        }
        return recipeDao;
    }
}
