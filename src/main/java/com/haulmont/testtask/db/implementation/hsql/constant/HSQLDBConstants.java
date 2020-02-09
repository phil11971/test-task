package com.haulmont.testtask.db.implementation.hsql.constant;

public class HSQLDBConstants {
    public static final String
            HSQLDB_DRIVER = "org.hsqldb.jdbc.JDBCDriver",
            HSQLDB_URL = "jdbc:hsqldb:file:~/data/db/",
            HSQLDB_USERNAME = "SA",
            HSQLDB_PASSWORD = "",

            CREATEDB_SCRIPT_PATH = "data/script/createDB",
            INSERT_SCRIPT_PATH = "data/script/insert",

            TABLE_DOCTOR = "DOCTOR",
            TABLE_DOCTOR_ID = "DOCTORID",
            TABLE_DOCTOR_LAST_NAME = "LASTNAME",
            TABLE_DOCTOR_FIRST_NAME = "FIRSTNAME",
            TABLE_DOCTOR_MIDDLE_NAME = "MIDDLENAME",
            TABLE_DOCTOR_SPEC = "SPEC",

            TABLE_PATIENT = "PATIENT",
            TABLE_PATIENT_ID = "PATIENTID",
            TABLE_PATIENT_LAST_NAME = "LASTNAME",
            TABLE_PATIENT_FIRST_NAME = "FIRSTNAME",
            TABLE_PATIENT_MIDDLE_NAME = "MIDDLENAME",
            TABLE_PATIENT_PHONE = "PHONE",

            TABLE_RECIPE = "RECIPE",
            TABLE_RECIPE_DESC = "DESC",
            TABLE_RECIPE_CREATIONDATE = "CREATIONDATE",
            TABLE_RECIPE_VALIDITY = "VALIDITY",
            TABLE_RECIPE_PRIORITY = "PRIORITY";
}
