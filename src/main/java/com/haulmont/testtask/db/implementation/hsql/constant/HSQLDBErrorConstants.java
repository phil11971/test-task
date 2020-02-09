package com.haulmont.testtask.db.implementation.hsql.constant;

public class HSQLDBErrorConstants {
    public static final String
            SCRIPT_ERROR = "Script for db hasn't been found!",
            STATEMENT_ERROR = "Statement hasn't created!",
            DRIVER_ERROR = "Driver not found!",
            CONNECTION_ERROR = "Connection with db hasn't created!",

            INSERT_ERROR = "Couldn't insert data to db!",
            SELECT_ERROR = "Couldn't select data in db!",
            DELETE_ERROR = "Couldn't delete data from db!",
            UPDATE_ERROR = "Couldn't update data in db!";
}
