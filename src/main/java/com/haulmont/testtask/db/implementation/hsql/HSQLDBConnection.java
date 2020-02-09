package com.haulmont.testtask.db.implementation.hsql;

import com.haulmont.testtask.db.implementation.hsql.constant.HSQLDBErrorConstants;
import com.haulmont.testtask.exception.db.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

import static com.haulmont.testtask.db.implementation.hsql.constant.HSQLDBConstants.*;

public class HSQLDBConnection {

    private static Connection connection;

    private static void buildConnection() throws DriverNotFoundException, DbConnectionException {
        try {
            Class.forName(HSQLDB_DRIVER);
            connection = DriverManager.getConnection(HSQLDB_URL, HSQLDB_USERNAME, HSQLDB_PASSWORD);
            initDb();
        } catch (ClassNotFoundException e) {
            throw new DriverNotFoundException(HSQLDBErrorConstants.DRIVER_ERROR);
        } catch (SQLException e) {
            throw new DbConnectionException(HSQLDBErrorConstants.CONNECTION_ERROR);
        }
    }

    public static Connection getConnection() throws DriverNotFoundException, DbConnectionException {
        if (connection == null) buildConnection();
        return connection;
    }

    private static void initDb() throws DbConnectionException {
        executeSqlScript(CREATEDB_SCRIPT_PATH);
        executeSqlScript(INSERT_SCRIPT_PATH);
    }

    private static void executeSqlScript(String path) throws DbConnectionException {
        String delimiter = ";";
        Scanner scanner;
        try {
            scanner = new Scanner(new FileInputStream(path)).useDelimiter(delimiter);
            Statement statement = null;
            while (scanner.hasNext()) {
                String scriptStatement = scanner.next() + delimiter;
                try {
                    statement = connection.createStatement();
                    statement.execute(scriptStatement);
                } catch (SQLException e) {
                    throw new DbConnectionException(HSQLDBErrorConstants.STATEMENT_ERROR);
                } finally {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (SQLException e) {
                            throw new DbConnectionException(HSQLDBErrorConstants.STATEMENT_ERROR);
                        }
                    }
                    statement = null;
                }
            }
            scanner.close();
        } catch (FileNotFoundException | DbConnectionException e) {
            throw new DbConnectionException(HSQLDBErrorConstants.SCRIPT_ERROR);
        }
    }
}
