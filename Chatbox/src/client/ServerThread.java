package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * ServerThread is a thread that handles all communications with the server
 */
public class ServerThread implements Runnable {

	/**
	 * State variables
	 */
	private Socket socket;
	private String userName;
	private final LinkedList<String> queuedMessages;

	/**
	 * Constructor
	 * 
	 * @param socket   - socket used to connect to server
	 * @param userName - display name picked by user
	 */
	public ServerThread(Socket socket, String userName) {
		this.socket = socket;
		this.userName = userName;
		queuedMessages = new LinkedList<String>();
	}
