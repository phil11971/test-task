package com.haulmont.testtask.service;

import com.haulmont.testtask.db.abstraction.DaoManager;
import com.haulmont.testtask.db.abstraction.dao.DoctorDao;
import com.haulmont.testtask.db.implementation.hsql.HSQLDBDaoManager;
import com.haulmont.testtask.exception.db.DaoException;
import com.haulmont.testtask.exception.service.ServiceException;
import com.haulmont.testtask.model.Doctor;

import java.util.List;
import java.util.logging.Logger;

public class DoctorService {
    private DaoManager daoManager;
    private DoctorDao doctorDao;

    private static DoctorService instance;

    private Logger logger = Logger.getLogger(PatientService.class.getName());

    private DoctorService() {
        try {
            daoManager = HSQLDBDaoManager.getInstance();
            doctorDao = daoManager.getDoctorDao();
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public static synchronized DoctorService getInstance() {
        if (instance == null) instance = new DoctorService();
        return instance;
    }

    public List<Doctor> getAll() {
        try {
            return doctorDao.getAll();
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }
}
