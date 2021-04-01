package server.sql;

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
            con = DriverManager.getConnection("jdbc:sqlite:./Chatbox/src/server/sql/users.db");
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

    /**
     * Checks if a name is avaliable
     * 
     * @param name
     * @return true if name is avaliable
     */
    public boolean nameAvaliable(String name) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username FROM registered_users WHERE username='" + name + "'");
            while (rs.next()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Selects the password from user 
     * 
     * @param user of user
     * @return string
     */
    public String getPassword(String user) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT password FROM registered_users WHERE username='"+ user +"'");
            while (rs.next()) {
                String pass = rs.getString("password");
                return pass;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserts row into sqlite db, must be synchronized since we are using threads
     * and this modifys the db.
     * 
     * Can also be used to check if the account name is avaliable, better than using nameAvaliable() 
     * since one name might concurrently be created
     * 
     * @param name
     * @param password
     */
    public synchronized boolean insertRow(String name, String password) {
        try {
            PreparedStatement stmt = con
                    .prepareStatement("INSERT INTO registered_users (username, password) VALUES (?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            if(e.getErrorCode() == 19)//if primary key constraint failed
                return false;//account must exist, should only happen if same name created concurrently
            else
                e.printStackTrace();
        }
        return true;
    }

    public Connection getCon() {
        return con;
    }
}

class Tester {
    /**
     * Example usage of Database to execute SQL.
     * 
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        handleRequests(1);
        handleRequests(2);
        handleRequests(3);
    }

    /**
     * Starts a new thread and executes SQL on it.
     * Uses ability to insert row as an indicator if account name is avaliable
     * @param thread - integer used to distinguish threads
     */
    public static void handleRequests(int thread) {
        Database db = Database.getInstance();
        new Thread(new Runnable() {
            public void run() {
                String name = null;
                try {
                    for (int i = 0; i < 100; i++) {
                        // System.out.println(i);
                        name = "billybobjoes"+i;
                        boolean checkName = db.insertRow(name, "Test");
                        System.out.println(""+thread+": Name: " + name +" Avaliabile: "+checkName);
                    }
                   // db.fetchAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
