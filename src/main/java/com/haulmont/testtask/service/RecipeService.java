package com.haulmont.testtask.service;

import com.haulmont.testtask.db.abstraction.DaoManager;
import com.haulmont.testtask.db.abstraction.dao.RecipeDao;
import com.haulmont.testtask.db.implementation.hsql.HSQLDBDaoManager;
import com.haulmont.testtask.exception.db.DaoException;
import com.haulmont.testtask.exception.service.ServiceException;
import com.haulmont.testtask.model.Recipe;

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

    public List<Recipe> getAll() {
        try {
            return recipeDao.getAll();
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }
}
