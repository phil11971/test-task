package com.haulmont.testtask.service;

import com.haulmont.testtask.db.abstraction.DaoManager;
import com.haulmont.testtask.db.abstraction.dao.DoctorDao;
import com.haulmont.testtask.db.abstraction.dao.RecipeDao;
import com.haulmont.testtask.db.implementation.hsqldb.HSQLDBDaoManager;
import com.haulmont.testtask.exception.db.DaoException;
import com.haulmont.testtask.exception.service.ServiceException;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.DoctorStat;

import java.util.List;
import java.util.logging.Logger;

public class DoctorService {
    private DaoManager daoManager;
    private DoctorDao doctorDao;
    private RecipeDao recipeDao;

    private static DoctorService instance;

    private Logger logger = Logger.getLogger(PatientService.class.getName());

    private DoctorService() {
        try {
            daoManager = HSQLDBDaoManager.getInstance();
            doctorDao = daoManager.getDoctorDao();
            recipeDao = daoManager.getRecipeDao();
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

    public boolean insertDoctor(Doctor doctor) {
        try {
            doctorDao.insert(doctor);
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return true;
    }

    public boolean deleteDoctor(Long id) {
        try {
            if (!recipeDao.existsRecordsByDoctorId(id)) {
                doctorDao.delete(id);
                return true;
            }
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return false;
    }

    public boolean updateDoctor(Doctor doctor) {
        try {
            doctorDao.update(doctor);
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return true;
    }

    public List<DoctorStat> getDoctorStats(){
        try {
            return doctorDao.getDoctorStats();
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

}
