package kea.intuition.data;

import java.sql.*;

public class Database {
    private static String dbUrl = "";
    private static String dbUsername = "";
    private static String dbPassword = "";
    private static Statement statement = null;
    private static Connection connection = null;

    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            statement = connection.createStatement();

        } catch(SQLException e) {
            System.err.print(e.getMessage());
        } catch(Exception e) {
            System.out.print(e.getMessage());
            System.exit(1);
        }
    }

    public void close() {
        try{
            connection.close();
        } catch(SQLException e) {
            System.err.print(e.getMessage());
        } catch(Exception e) {
            System.err.print(e.getMessage());
        }
    }

    public ResultSet select( String select, String table) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT " + select + " FROM " + table + "");

            return resultSet;
        } catch(SQLException e) {
            System.err.print(e.getMessage());
        } catch(Exception e) {
            System.err.print(e.getMessage());
        }

        return null;
    }

    public ResultSet select( String select, String table, String where ) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT " + select + " FROM " + table + " WHERE " + where  + "");

            return resultSet;
        } catch(SQLException e) {
            System.err.print(e.getMessage());
        } catch(Exception e) {
            System.err.print(e.getMessage());
        }

        return null;
    }
}
