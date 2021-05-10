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
	private String ipAddress;
	private int port;
	private Scanner scan;
	private Thread thread;
	public String[] activeUserList;

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
	/*private void reqName() {
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
	}*/


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
				if(scan.hasNextLine()) {
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
	}

}
