package server;

import java.sql.*;

/**
 * Singleton class to handle SQLite database
 */
public class Database {
    private Connection con = null;
    private static Database db = null;

    /**
     * Gets the instance of singleton Database or creates it
     * 
     * @return Database instance
     */
    public static Database getInstance() {
        if (db == null)
            db = new Database();
        return db;
    }

    /**
     * Singleton constructor initilizes the connection once for every thread to use
     */
    private Database() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:users.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches all the registed users
     */
    public void fetchAll() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM registered_users");
            boolean flag = false;
            while (rs.next()) {
                flag = true;
                String name = rs.getString("username");
                // String password = rs.getString("password");//needs password encryption
                System.out.println("Name: " + name);
            }
            if (!flag)
                System.out.println("Empty set");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
