package com.haulmont.testtask.service;

import com.haulmont.testtask.db.abstraction.DaoManager;
import com.haulmont.testtask.db.abstraction.dao.PatientDao;
import com.haulmont.testtask.db.implementation.hsql.HSQLDBDaoManager;
import com.haulmont.testtask.exception.db.DaoException;
import com.haulmont.testtask.exception.service.ServiceException;
import com.haulmont.testtask.model.Patient;

import java.util.List;
import java.util.logging.Logger;

public class PatientService {
    private DaoManager daoManager;
    private PatientDao patientDao;

    private static PatientService instance;

    private Logger logger = Logger.getLogger(PatientService.class.getName());

    private PatientService() {
        try {
            daoManager = HSQLDBDaoManager.getInstance();
            patientDao = daoManager.getPatientDao();
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public static synchronized PatientService getInstance() {
        if (instance == null) instance = new PatientService();
        return instance;
    }

    public List<Patient> getAll() {
        try {
            return patientDao.getAll();
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }
}
