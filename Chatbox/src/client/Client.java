package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import client.Threading.ServerThread;

/**
 * Main client class
 */
public class Client {
	/**
	 * Host ip address
	 */
	private static final String HOST = "127.0.0.1";

	/**
	 * Open port to connect to
	 */
	private static final int PORT = 43594;

	/**
	 * State variables
	 */
	private String name;
	private String password;
	private String ipAddress;
	private int port;
	private Scanner scan;
	private Thread thread;

	/**
	 * Constructor
	 */
	private Client(String ipAddress, int port) {
		this.ipAddress = ipAddress;
		this.port = port;
	}

	/**
	 * Simple method of requesting a name to use
	 */
	private void reqName() {
		System.out.println("Please input username:");
		while (this.name == "" || this.name.isEmpty()) {
			// null, empty, whitespace(s) not allowed.
			this.name = this.scan.nextLine();
			if (name.isEmpty()) {
				System.out.println("Invalid. Please enter again:");
			}
		}
	}

	// Request password from the user
	// @TODO savinng in DB;
	private void reqPassword() {
		System.out.println("Please input password:");
		while (this.password == "" || this.password.isEmpty()) {
			// null, empty, whitespace(s) not allowed.
			this.password = this.scan.nextLine();
			if (this.password.isEmpty()) {
				System.out.println("Invalid. Please enter again:");
			}
		}
	}


// 	(?=.*[0-9]) a digit must occur at least once
// (?=.*[a-z]) a lower case letter must occur at least once
// (?=.*[A-Z]) an upper case letter must occur at least once
// (?=.*[@#$%^&+=]) a special character must occur at least once
// (?=\\S+$) no whitespace allowed in the entire string
// .{8,} at least 8 characters

	private boolean checkPasswordRequirement(String password) {
		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
		return password.matches(pattern);
	}

	/**
	 * Starts the thread that listens for messages from server Also keeps track of
	 * keyboard input on client's parent thread (Client) to send to child thread
	 * (ServerThread)
	 */
	private void startClient() {
		try {
			Socket socket = new Socket(ipAddress, port);
			ServerThread serverThread = new ServerThread(socket, this);
			this.scan = new Scanner(System.in);
			thread = new Thread(serverThread);
			thread.start();
			while (thread.isAlive()) {
				if (scan.hasNextLine()) {
					String msg = scan.nextLine();
					serverThread.appendMessage(msg);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Client client = new Client(HOST, PORT);
		client.startClient();
		client.reqName();
		client.reqPassword();
	}

}