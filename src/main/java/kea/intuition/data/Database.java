package kea.intuition.data;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Map;

public class Database {
    private static String dbUrl = "";
    private static String dbUsername = "";
    private static String dbPassword = "";
    private static Statement statement = null;
    private static Connection connection = null;

    public Connection getConnection() {
        return connection;
    }

    public Database() {
        getConnInfo();

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

    private void getConnInfo() {
        YamlReader reader = null;
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        //System.out.println(getClass().getResource("/config.yml").toExternalForm());

        try {
            //reader = new YamlReader(new FileReader(contextClassLoader.getResource("/config.yml").toExternalForm()));
            reader = new YamlReader(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/config.yml"))));
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        Map map = null;

        try {
            map = (Map) reader.read();
        } catch (YamlException ex) {
            System.out.println("Invalid YAML config.");
            System.exit(1);
        }

        dbUsername = (String) map.get("username");
        dbPassword = (String) map.get("password");
        dbUrl = (String) map.get("url");
    }
}
