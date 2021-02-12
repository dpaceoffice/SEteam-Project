package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
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
	private String ipAddress;
	private int port;
	private Scanner scan;

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
		String name = null;
		scan = new Scanner(System.in);
		System.out.println("Please input username:");
		while (name == null || name.trim().equals("")) {
			// null, empty, whitespace(s) not allowed.
			name = scan.nextLine();
			if (name.trim().equals("")) {
				System.out.println("Invalid. Please enter again:");
			}
		}
		this.name = name;
	}
