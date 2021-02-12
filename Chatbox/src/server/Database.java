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
