package com.haulmont.testtask.service;

import com.haulmont.testtask.db.abstraction.DaoManager;
import com.haulmont.testtask.db.abstraction.dao.RecipeDao;
import com.haulmont.testtask.db.implementation.hsqldb.HSQLDBDaoManager;
import com.haulmont.testtask.exception.db.DaoException;
import com.haulmont.testtask.exception.service.ServiceException;
import com.haulmont.testtask.model.Recipe;
import com.haulmont.testtask.model.RecipeExt;

import java.util.List;
import java.util.logging.Logger;

public class RecipeService {
    private DaoManager daoManager;
    private RecipeDao recipeDao;

    private static RecipeService instance;

    private Logger logger = Logger.getLogger(PatientService.class.getName());

    private RecipeService() {
        try {
            daoManager = HSQLDBDaoManager.getInstance();
            recipeDao = daoManager.getRecipeDao();
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public static synchronized RecipeService getInstance() {
        if (instance == null) instance = new RecipeService();
        return instance;
    }

    public List<RecipeExt> getAll() {
        try {
            return recipeDao.getAll();
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean insertRecipe(Recipe recipe) {
        try {
            if (!recipeDao.existsRecord(recipe.getDoctorId(), recipe.getPatientId())){
                recipeDao.insert(recipe);
                return true;
            }
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return false;
    }

    public boolean deleteRecipe(Long doctorId, Long patientId) {
        try {
             recipeDao.delete(doctorId, patientId);
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return true;
    }

    public boolean updateRecipe(Recipe recipe) {
        try {
            recipeDao.update(recipe);
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return true;
    }
}
