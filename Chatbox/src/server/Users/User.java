package server.Users;

import server.Threading.ClientThread;
import server.sql.Database;

/**
 * User object defining people connected to the chatbox
 */
public class User {

    private ClientThread thread;
    private String username;
    private String passwordHash;
    private String ipAddress;
    private State state;
    


    /**
     * User constructor 
     * @param thread current thread for user
     * @param user username value
     * @param pass password hash for bcrypt
     * @param ip ipaddress of user
     */
    public User(ClientThread thread, String user, String pass) {
        this.thread = thread;
        this.username = user;
        this.passwordHash = pass;
        this.ipAddress = thread.toString();
        this.state = State.LOGIN;
    }

    public ClientThread getThread() {
        return thread;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public boolean userExists() {
        Database db = Database.getInstance();
        return !db.nameAvaliable(username);
    }

    /**
     * Input is the password we are checking
     * @param input
     * @return
     */
    public boolean checkPassword(String input) {
        Database db = Database.getInstance();
        String savedPass = db.getPassword(this.username);//stored database
        if(savedPass.equals(input)) 
            return true;
        return false;
    }

    public void saveUser() {
        Database db = Database.getInstance();
        new Thread(new Runnable() {
            public void run() {
                try {
                    db.insertRow(username, passwordHash);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
