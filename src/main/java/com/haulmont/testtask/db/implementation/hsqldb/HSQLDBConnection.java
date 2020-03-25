package com.haulmont.testtask.db.implementation.hsqldb;

import com.haulmont.testtask.db.constant.ErrorConstants;
import com.haulmont.testtask.exception.db.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

import static com.haulmont.testtask.db.constant.TableConstants.*;

public class HSQLDBConnection {

    private static Connection connection;

    private static void buildConnection() throws DriverNotFoundException, DbConnectionException {
        try {
            Class.forName(HSQLDBConstants.HSQLDB_DRIVER);
            connection = DriverManager.getConnection(HSQLDBConstants.HSQLDB_URL, HSQLDBConstants.HSQLDB_USERNAME, HSQLDBConstants.HSQLDB_PASSWORD);
            initDb();
        } catch (ClassNotFoundException e) {
            throw new DriverNotFoundException(ErrorConstants.DRIVER_ERROR);
        } catch (SQLException e) {
            throw new DbConnectionException(ErrorConstants.CONNECTION_ERROR);
        }
    }

    public static Connection getConnection() throws DriverNotFoundException, DbConnectionException {
        if (connection == null) buildConnection();
        return connection;
    }

    private static void initDb() throws SQLException, DbConnectionException {
        if (!tableExists(TABLE_DOCTOR) || !tableExists(TABLE_PATIENT) || !tableExists(TABLE_RECIPE)) {
            executeSqlStartScript(HSQLDBConstants.CREATEDB_SCRIPT_PATH);
            executeSqlStartScript(HSQLDBConstants.INSERT_SCRIPT_PATH);
        }
    }

    private static boolean tableExists(String tableName) throws SQLException {
        boolean isExists = false;
        try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String table_name = rs.getString("TABLE_NAME");
                if (table_name != null && table_name.equals(tableName)) {
                    isExists = true;
                    break;
                }
            }
        }
        return isExists;
    }

    private static void executeSqlStartScript(String path) throws DbConnectionException {
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
                    throw new DbConnectionException(ErrorConstants.STATEMENT_ERROR);
                } finally {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (SQLException e) {
                            throw new DbConnectionException(ErrorConstants.STATEMENT_ERROR);
                        }
                    }
                    statement = null;
                }
            }
            scanner.close();
        } catch (FileNotFoundException | DbConnectionException e) {
            throw new DbConnectionException(ErrorConstants.SCRIPT_ERROR);
        }
    }
}
