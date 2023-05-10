package com.exam1;

/**
 * Hello world!
 *
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class FileToRedshift {

    private static final String JDBC_URL = "jdbc:redshift://my-workgroup.405313240333.us-east-1.redshift-serverless.amazonaws.com:5439/dev?user=admin&password=Zecdata123";
    private static final String TABLE_NAME = "my_table";
    private static final String[] UNIQUE_KEY_COLUMNS = {"id", "name"};

    public static void main(String[] args) throws SQLException {
        // Read data from file into a List of Maps
        List<Map<String, Object>> fileRecords = readDataFromFile();

        // Connect to Redshift database
        Connection conn = DriverManager.getConnection(JDBC_URL);

        // Iterate over file records and insert or update in database
        for (Map<String, Object> fileRecord : fileRecords) {
            // Check if record already exists in database
            String selectQuery = buildSelectQuery(fileRecord);
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            ResultSet resultSet = selectStmt.executeQuery();
            boolean recordExists = resultSet.next();
            resultSet.close();
            selectStmt.close();

            // If record exists, update in database
            if (recordExists) {
                String updateQuery = buildUpdateQuery(fileRecord);
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.executeUpdate();
                updateStmt.close();
            }
            // Otherwise, insert into database
            else {
                String insertQuery = buildSelectQuery(fileRecord);
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.executeUpdate();
                insertStmt.close();
            }
        }

        // Close JDBC connection
        conn.close();
    }

    private static List<Map<String, Object>> readDataFromFile() {
        List<Map<String, Object>> fileRecords = new ArrayList();

        try {
            // Open file for reading
            FileReader fileReader = new FileReader("s3://bucket-keshav/sempletest2 (1).csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Read file line by line
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Split line into columns using a delimiter (e.g. comma)
                String[] columns = line.split(",");

                // Create a new Map for this record
                Map<String, Object> record = new HashMap();

                // Add columns to Map
                record.put("id", columns[0]);
                record.put("name", columns[1]);
                record.put("value", columns[2]);

                // Add Map to List
                fileRecords.add(record);
            }

            // Close file
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileRecords;
    }


    private static String buildSelectQuery(Map<String, Object> record) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM ").append(TABLE_NAME).append(" WHERE ");
        for (String column : UNIQUE_KEY_COLUMNS) {
            queryBuilder.append(column).append(" = ? AND ");
        }
        queryBuilder.setLength(queryBuilder.length() - 5); // Remove trailing " AND "
        return queryBuilder.toString();
    }

    private static String buildUpdateQuery(Map<String, Object> record) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("UPDATE ").append(TABLE_NAME).append(" SET ");
        for (Map.Entry<String, Object> entry : record.entrySet()) {
            if (!Arrays.asList(UNIQUE_KEY_COLUMNS).contains(entry.getKey())) {
                queryBuilder.append(entry.getKey()).append(" = ?, ");
            }
        }
        queryBuilder.setLength(queryBuilder.length() - 2); // Remove trailing ", "
        queryBuilder.append(" WHERE ");
        for (String column : UNIQUE_KEY_COLUMNS) {
            queryBuilder.append(column).append(" = ? AND ");
        }
        queryBuilder.setLength(queryBuilder.length() - 5); // Remove trailing " AND "
        return queryBuilder.toString();
    }

}